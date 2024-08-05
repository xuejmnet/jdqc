package com.easy.query.core.proxy.partition.metadata;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.partition.Partition1;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2024/8/5 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionBy1EntityMetadata extends EntityMetadata {

    private final EntityMetadata entityMetadata;
    private final JdbcTypeHandler jdbcTypeHandler;

    public PartitionBy1EntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, JdbcTypeHandler jdbcTypeHandler) {
        super(entityClass);
        this.entityMetadata = entityMetadata;
        this.jdbcTypeHandler = jdbcTypeHandler;
        entityMetadataType = EntityMetadataTypeEnum.PARTITION_BY;
        beanConstructorCreator=() -> {
            Partition1<Object, Object> r = new Partition1<>();
            Object entity = entityMetadata.getBeanConstructorCreator().get();
            r.setEntity(entity);
            return r;
        };
    }

    @Override
    public ColumnMetadata getColumnNotNull(String propertyName) {
        ColumnMetadata columnMetadata = getColumnOrNull(propertyName);
        if(columnMetadata==null){
            throw new EasyQueryException(String.format("%s not found property:[%s] mapping column name", EasyClassUtil.getSimpleName(getEntityClass()), propertyName));
        }
        return columnMetadata;
    }

    @Override
    public ColumnMetadata getColumnOrNull(String propertyName) {
        ColumnMetadata partitionByColumn = getPartitionByColumn(propertyName);
        if(partitionByColumn!=null){
            return partitionByColumn;
        }
        return entityMetadata.getColumnOrNull(propertyName);
    }
    private ColumnMetadata getPartitionByColumn(String propertyName){

        if(Partition1.PARTITION_COLUMN1.equals(propertyName)){
            ColumnOption columnOption = new ColumnOption(false, this, propertyName);
            columnOption.setGetterCaller(obj->{
                return ((Partition1)obj).getPartitionColumn1();
            });
            columnOption.setSetterCaller((obj, value)->{
                ((Partition1)obj).setPartitionColumn1(value);
            });
            columnOption.setJdbcTypeHandler(jdbcTypeHandler);
            return new PartitionBy1ColumnMetadata(columnOption);
        }
        return null;
    }

    public String getPropertyNameOrNull(String columnName, String def) {
        ColumnMetadata columnMetadata = getColumnMetadataOrNull(columnName);
        if (columnMetadata == null) {
            return def;
        }
        return columnMetadata.getPropertyName();
    }

    @Override
    public ColumnMetadata getColumnMetadataOrNull(String columnName) {
        ColumnMetadata partitionByColumn = getPartitionByColumn(columnName);
        if(partitionByColumn!=null){
            return partitionByColumn;
        }
        return entityMetadata.getColumnMetadataOrNull(columnName);

    }
}
