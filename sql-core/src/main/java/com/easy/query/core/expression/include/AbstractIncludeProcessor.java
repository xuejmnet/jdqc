package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/16 18:29
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractIncludeProcessor implements IncludeProcessor {
    protected final Collection<?> entities;
    protected final EntityMetadata selfEntityMetadata;
    protected final IncludeParserResult includeParserResult;
    protected final QueryRuntimeContext runtimeContext;
    protected final EntityMetadata targetEntityMetadata;
    protected final ColumnMetadata targetColumnMetadata;
    protected  ColumnMetadata selfColumn;

    protected Class<?> collectionType;

    public AbstractIncludeProcessor(Collection<?> entities, IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {

        this.entities = entities;
        this.includeParserResult = includeParserResult;
        this.selfEntityMetadata = includeParserResult.getEntityMetadata();
        this.runtimeContext = runtimeContext;
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getNavigatePropertyType());
        this.targetColumnMetadata = getTargetRelationColumn();
    }



    private ColumnMetadata getTargetRelationColumn(){
        String selfPropertyName = EasyStringUtil.isBlank(includeParserResult.getTargetProperty()) ? getTargetSingleKeyProperty() : includeParserResult.getTargetProperty();
        return targetEntityMetadata.getColumnNotNull(selfPropertyName);
    }
    private String getTargetSingleKeyProperty(){
        Collection<String> keyProperties = targetEntityMetadata.getKeyProperties();

        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "multi key not support include");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    /**
     * 获取对象关系id为key的对象集合为value的map
     * @param includes
     * @return
     * @param <TNavigateEntity>
     */
    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetToManyMap(List<TNavigateEntity> includes) {
        Class<?> collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        for (TNavigateEntity target : includes) {
            Object targetRelationId = targetColumnMetadata.getGetterCaller().apply(target);
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> (Collection<TNavigateEntity>) EasyClassUtil.newInstance(collectionType));
            objects.add(target);
        }
        return resultMap;
    }

    protected Class<?> getCollectionType(){
        if(collectionType==null){
            collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        }
        return collectionType;
    }
    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection(){
        Class<?> collectionType = getCollectionType();
        return EasyObjectUtil.typeCastNullable(EasyClassUtil.newInstance(collectionType));
    }
    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetToManyMap(List<TNavigateEntity> includes, List<Map<String, Object>> mappingRows) {

        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getMappingClass());
        ColumnMetadata selfRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getSelfMappingProperty());
        String selfColumnName = selfRelationColumn.getName();
        ColumnMetadata targetRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getTargetMappingProperty());
        String targetColumnName = targetRelationColumn.getName();

        Map<Object, Collection<TNavigateEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (Map<String, Object> mappingRow : mappingRows) {
            Object selfRelationId = mappingRow.get(selfColumnName);
            Object targetRelationId = mappingRow.get(targetColumnName);
            Collection<TNavigateEntity> targetEntities = resultMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            Collection<TNavigateEntity> targets = targetToManyMap.get(targetRelationId);
            targetEntities.addAll(targets);
        }
        return resultMap;
    }
    protected Map<Object, Collection<Object>> getSelfToManyMap() {
        Map<Object, Collection<Object>> resultMap = new HashMap<>();
        ColumnMetadata selfRelationColumn = includeParserResult.getEntityMetadata().getColumnNotNull(includeParserResult.getSelfProperty());
        for (Object entity : entities) {
            Object relationId = selfRelationColumn.getGetterCaller().apply(entity);
            Collection<Object> objects = resultMap.computeIfAbsent(relationId, k -> new ArrayList<>());
            objects.add(entity);
        }
        return resultMap;
    }

    @Override
    public void process() {
        switch (includeParserResult.getRelationType()) {
            case OneToOne:
                OneToOneProcess(includeParserResult.getIncludeResult());
                return;
            case OneToMany:
                OneToManyProcess(includeParserResult.getIncludeResult());
                return;
            case ManyToOne:
                ManyToOneProcess(includeParserResult.getIncludeResult());
                return;
            case ManyToMany:
                ManyToManyProcess(includeParserResult.getIncludeResult(),includeParserResult.getMappingRows());
                return;
        }
        throw new UnsupportedOperationException("not support include relation type:" + includeParserResult.getRelationType());
    }

    protected abstract <TEntityInclude> void OneToOneProcess(List<TEntityInclude> includes);

    protected abstract <TEntityInclude> void ManyToOneProcess(List<TEntityInclude> includes);

    protected abstract <TEntityInclude> void OneToManyProcess(List<TEntityInclude> includes);

    protected abstract<TEntityInclude> void ManyToManyProcess(List<TEntityInclude> includes, List<Map<String, Object>> mappingRows);


    public ColumnMetadata getSelfRelationColumn() {
        if (this.selfColumn == null) {
            this.selfColumn = includeParserResult.getEntityMetadata().getColumnNotNull(includeParserResult.getSelfProperty());
        }
        return this.selfColumn;
    }
}
