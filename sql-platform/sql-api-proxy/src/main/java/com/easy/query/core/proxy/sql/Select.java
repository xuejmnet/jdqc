package com.easy.query.core.proxy.sql;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.TypeResultColumnMetadata;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.FlatEntitySQLContext;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.proxy.partition.proxy.PartitionProxy;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.Objects;

/**
 * create time 2023/12/2 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class Select {

    public static SQLSelectExpression of(boolean condition, SQLSelectExpression... selects) {
        if (condition) {
            return of(selects);
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectExpression of(SQLSelectExpression... selects) {
        if (EasyArrayUtil.isNotEmpty(selects)) {
            SQLSelectExpression firstSQLSelectAs = selects[0];
            for (int i = 1; i < selects.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs._concat(selects[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectAsExpression of(SQLSelectAsExpression... selectAss) {

        if (EasyArrayUtil.isNotEmpty(selectAss)) {
            SQLSelectAsExpression firstSQLSelectAs = selectAss[0];
            for (int i = 1; i < selectAss.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs._concat(selectAss[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectAsExpression.empty;
    }

    public static Draft DRAFT = new Draft();
    public static SQLPartition PARTITION = new SQLPartition();


    public static <TR, TRProxy> void setDraftPropTypes(ClientQueryable<TR> select, TRProxy trProxy) {
        ExpressionContext expressionContext = select.getSQLEntityExpressionBuilder().getExpressionContext();
        if (trProxy instanceof DraftProxy) {
            DraftProxy draftProxy = (DraftProxy) trProxy;
            expressionContext.setResultPropTypes(draftProxy.getDraftPropTypes());
        } else if (trProxy instanceof MapTypeProxy) {
            MapTypeProxy mapTypeProxy = (MapTypeProxy) trProxy;
            expressionContext.setResultPropTypes(mapTypeProxy._getResultPropTypes().stream().map(x -> new TypeResultColumnMetadata(x)).toArray(ResultColumnMetadata[]::new));
        } else {
            if (expressionContext.getResultPropTypes() != null) {
                expressionContext.setResultPropTypes(null);
            }
        }
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectProxy(TRProxy resultProxy, ClientQueryable<?> queryable) {

        Objects.requireNonNull(resultProxy, "select null result class");
        if (resultProxy.getEntitySQLContext() instanceof FlatEntitySQLContext) {
            //[flatElement]方法不允许在select函数里面使用,如果你希望是从[flatElement]函数获取的请在[toList]函数内部使用
            throw new EasyQueryInvalidOperationException("The [flatElement] method is not allowed to be used inside the select function. If you wish to retrieve from the [flatElement] function, please use it within the [toList] function. ");
        }
        QueryRuntimeContext runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression == null) {//全属性映射
            TableAvailable tableOrNull = resultProxy.getTableOrNull();
            if (tableOrNull == null) {
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass());
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            } else {
                EntityMetadata resultEntityMetadata = getResultProxyEntityMetadata(resultProxy, runtimeContext);
                if (resultProxy instanceof SQLSelectExpression) {
                    SQLSelectExpression resultProxySelectExpression = (SQLSelectExpression) resultProxy;
                    ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                        resultProxySelectExpression.accept(columnAsSelector.getAsSelector());
                    });
                    Select.setDraftPropTypes(select, resultProxy);
                    return new EasyEntityQueryable<>(resultProxy, select);
                }
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                    columnAsSelector.getAsSelector().columnAll(tableOrNull);
                });
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            }
        } else {
//            if(resultProxy instanceof PartitionByProxy){
//                PartitionByProxy resultProxy1 = (PartitionByProxy) resultProxy;
//                ClientQueryable<?> select = queryable.select(resultProxy1.getPartitionByEntityClass(), columnAsSelector -> {
//                    selectAsExpression.accept(columnAsSelector.getAsSelector());
//                });
//                Select.setDraftPropTypes(select, resultProxy);
//                return new EasyEntityQueryable<>(resultProxy, (ClientQueryable<TR>)select);
//            }else{
//
//            }
            EntityMetadata resultEntityMetadata = getResultProxyEntityMetadata(resultProxy, runtimeContext);
            ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                if (resultProxy instanceof PartitionProxy) {
                    PartitionProxy resultProxy1 = (PartitionProxy) resultProxy;
                    resultProxy1.accept(columnAsSelector.getAsSelector());
                }
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            Select.setDraftPropTypes(select, resultProxy);
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }

    private static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityMetadata getResultProxyEntityMetadata(TRProxy resultProxy, QueryRuntimeContext runtimeContext) {
        EntityMetadata entityMetadata = resultProxy.getEntityMetadata();
        if (entityMetadata != null && entityMetadata.getEntityClass().equals(resultProxy.getEntityClass())) {
            return entityMetadata;
        } else {
            return runtimeContext.getEntityMetadataManager().getEntityMetadata(resultProxy.getEntityClass());
        }
    }
}
