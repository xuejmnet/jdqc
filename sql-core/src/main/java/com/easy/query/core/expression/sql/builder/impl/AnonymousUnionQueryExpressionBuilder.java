package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.SqlUnionEnum;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SqlAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.SqlEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySqlExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQueryExpressionBuilder extends QueryExpressionBuilder implements SqlAnonymousUnionEntityQueryExpressionBuilder {

    private final List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders;
    private final SqlUnionEnum sqlUnion;

    public AnonymousUnionQueryExpressionBuilder(List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders, ExpressionContext queryExpressionContext, SqlUnionEnum sqlUnion) {
        super(queryExpressionContext);
        this.entityQueryExpressionBuilders = entityQueryExpressionBuilders;
        this.sqlUnion = sqlUnion;
    }

    @Override
    public List<EntityQueryExpressionBuilder> getEntityQueryExpressionBuilders() {
        return entityQueryExpressionBuilders;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public EasyQuerySqlExpression toExpression() {
       List<EasyQuerySqlExpression> easyQuerySqlExpressions = new ArrayList<>(entityQueryExpressionBuilders.size());
        for (EntityQueryExpressionBuilder entityQueryExpressionBuilder : entityQueryExpressionBuilders) {

            EasyQuerySqlExpression expression = entityQueryExpressionBuilder.toExpression();
            easyQuerySqlExpressions.add(expression);
        }
        return new AnonymousUnionQuerySqlExpression(getRuntimeContext(), easyQuerySqlExpressions,sqlUnion);
    }

//    @Override
//    public QueryExpressionBuilder cloneSqlQueryExpressionBuilder() {
//        AnonymousQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousQueryExpressionBuilder(sql, sqlExpressionContext);
//
//        for (EntityTableExpressionBuilder table : super.tables) {
//            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
//        }
//        return anonymousQueryExpressionBuilder;
//    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        AnonymousUnionQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders, sqlExpressionContext,sqlUnion);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}