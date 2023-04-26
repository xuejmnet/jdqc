package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.util.SqlExpressionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 08:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertExecutionCreator extends BaseEntityExecutionCreator {
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final boolean fillAutoIncrement;

    public InsertExecutionCreator(String dataSource,InsertPrepareParseResult insertPrepareParseResult) {
       this(dataSource,insertPrepareParseResult.getEntityExpressionBuilder(),insertPrepareParseResult.getEntities(),insertPrepareParseResult.isFillAutoIncrement());
    }
    public InsertExecutionCreator(String dataSource, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        super(dataSource,entityInsertExpressionBuilder,entities);
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.fillAutoIncrement = fillAutoIncrement;
    }

    @Override
    protected EasySqlExpression cretateEasySqlExpression(Object entity) {
        return entityInsertExpressionBuilder.toExpression(entity);
    }

    @Override
    protected boolean getFillAutoIncrement() {
        return fillAutoIncrement;
    }
}
