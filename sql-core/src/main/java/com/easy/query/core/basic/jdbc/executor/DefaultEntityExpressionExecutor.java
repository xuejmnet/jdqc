package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultExecuteBatchEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultExecuteUpdateEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultInsertEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultQueryEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.abstraction.EasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.executor.parser.DefaultEntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.DefaultInsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.DefaultQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.merge.DefaultStreamMergeContext;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StreamResultUtil;

import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEntityExpressionExecutor implements EntityExpressionExecutor {
    private final EasyPrepareParser easyPrepareParser;
    private final ExecutionContextFactory executionContextFactory;

    public DefaultEntityExpressionExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {

        this.easyPrepareParser = easyPrepareParser;
        this.executionContextFactory = executionContextFactory;
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createQueryExecutionContext(sql, sqlParameters);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getQueryJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof QueryExecuteResult) {
                QueryExecuteResult queryExecuteResult = (QueryExecuteResult) executeResult;
                return StreamResultUtil.mapTo(executorContext, queryExecuteResult.getStreamResult(), clazz);
            }
            throw new UnsupportedOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
//        try (StreamIterable queryIterable = getQueryStreamIterable(executorContext, executionContext)) {
//            StreamResult streamResult = queryIterable.getStreamResult();
//            return StreamResultUtil.mapTo(executorContext, streamResult, clazz);
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        Set<Class<?>> shardingEntities = easyPrepareParser.parse(entityQueryExpressionBuilder);
        DefaultQueryPrepareParseResult queryPrepareParseResult = new DefaultQueryPrepareParseResult(shardingEntities, entityQueryExpressionBuilder);
        ExecutionContext executionContext = executionContextFactory.createExecutionContext(queryPrepareParseResult);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getQueryJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof QueryExecuteResult) {
                QueryExecuteResult queryExecuteResult = (QueryExecuteResult) executeResult;
                return StreamResultUtil.mapTo(executorContext, queryExecuteResult.getStreamResult(), clazz);
            }
            throw new UnsupportedOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));

        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
//        try (StreamIterable queryIterable = getQueryStreamIterable(executorContext,executionContext)) {
//            StreamResult streamResult = queryIterable.getStreamResult();
//            return StreamResultUtil.mapTo(executorContext, streamResult, clazz);
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
//        //需要分片
//        if (queryCompilerContext.isShardingQuery()) {//queryCompilerContext.isSingleShardingQuery()
//            MergeQueryCompilerContext mergeQueryCompilerContext = (MergeQueryCompilerContext) queryCompilerContext;
//            StreamResult streamResult = ShardingExecutor.<StreamResult>execute(null, null, mergeQueryCompilerContext.GetShardingRouteResult().getRouteUnits().stream());
//            throw new NotImplementedException();
//        } else {
//            //非分片处理
//            boolean tracking = entityQueryExpression.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
//            String sql = entityQueryExpression.toSql();
//            return easyExecutor.query(ExecutorContext.create(entityQueryExpression.getRuntimeContext(), tracking), resultClass, sql, entityQueryExpression.getParameters());
//
//        }
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        Set<Class<?>> shardingEntities = easyPrepareParser.parse(entityInsertExpressionBuilder);
        DefaultInsertPrepareParseResult defaultInsertPrepareParseResult = new DefaultInsertPrepareParseResult(shardingEntities, entityInsertExpressionBuilder, (List<Object>) entities,fillAutoIncrement);
        ExecutionContext executionContext = executionContextFactory.createExecutionContext(defaultInsertPrepareParseResult);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getInsertJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof AffectedRowsExecuteResult) {
                AffectedRowsExecuteResult affectedRowsExecuteResult = (AffectedRowsExecuteResult) executeResult;
                return affectedRowsExecuteResult.getRows();
            }
            throw new UnsupportedOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));

        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        Set<Class<?>> shardingEntities = easyPrepareParser.parse(entityExpressionBuilder);
        DefaultEntityPrepareParseResult defaultInsertPrepareParseResult = new DefaultEntityPrepareParseResult(shardingEntities, entityExpressionBuilder, (List<Object>) entities);
        ExecutionContext executionContext = executionContextFactory.createExecutionContext(defaultInsertPrepareParseResult);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getExecuteBatchJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof AffectedRowsExecuteResult) {
                AffectedRowsExecuteResult affectedRowsExecuteResult = (AffectedRowsExecuteResult) executeResult;
                return affectedRowsExecuteResult.getRows();
            }
            throw new UnsupportedOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));

        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EasyEntitySqlExpression easyEntitySqlExpression) {
        return 0;
//        PrepareParseResult prepareParseResult = easyPrepareParser.parse(easyEntitySqlExpression);
//        ExecutionContext executionContext = executionContextFactory.createExecutionContext(prepareParseResult);
//        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getExecuteUpdateJDBCExecuteResult(executorContext, executionContext);
//             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
//            if (executeResult instanceof AffectedRowsExecuteResult) {
//                AffectedRowsExecuteResult affectedRowsExecuteResult = (AffectedRowsExecuteResult) executeResult;
//                return affectedRowsExecuteResult.getRows();
//            }
//            throw new UnsupportedOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));
//
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
    }

    private EasyQueryJDBCExecutor getQueryJDBCExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultQueryEasyQueryJDBCExecutor(new DefaultStreamMergeContext(executorContext, executionContext));
    }
    private EasyQueryJDBCExecutor getInsertJDBCExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultInsertEasyQueryJDBCExecutor(new DefaultStreamMergeContext(executorContext, executionContext));
    }
    private EasyQueryJDBCExecutor getExecuteBatchJDBCExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultExecuteBatchEasyQueryJDBCExecutor(new DefaultStreamMergeContext(executorContext, executionContext));
    }
    private EasyQueryJDBCExecutor getExecuteUpdateJDBCExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultExecuteUpdateEasyQueryJDBCExecutor(new DefaultStreamMergeContext(executorContext, executionContext));
    }

}
