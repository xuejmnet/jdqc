package com.easy.query.core.query;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: EasySqlUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 * @Created by xuejiaming
 */
public abstract class EasySqlUpdateExpression extends AbstractSqlEntityExpression implements SqlEntityUpdateExpression{

    protected final boolean isExpressionUpdate;
    private SqlBuilderSegment setColumns;
    private  PredicateSegment where;
    private SqlBuilderSegment setIgnoreColumns;
    private SqlBuilderSegment whereColumns;
    public EasySqlUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
    }

    @Override
    public SqlBuilderSegment getSetColumns() {
        if(setColumns==null){
            setColumns=new UpdateSetSqlBuilderSegment();
        }
        return setColumns;
    }

    @Override
    public boolean hasSetColumns() {
        return setColumns!=null&&setColumns.isNotEmpty();
    }

    @Override
    public boolean hasWhere() {
        return where!=null&&where.isNotEmpty();
    }

    @Override
    public PredicateSegment getWhere() {
        if(where==null)
        {
            where=new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public SqlBuilderSegment getSetIgnoreColumns() {
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlBuilderSegment();
        }
        return setIgnoreColumns;
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return setIgnoreColumns!=null&&setIgnoreColumns.isNotEmpty();
    }


    @Override
    public SqlBuilderSegment getWhereColumns() {
        if(whereColumns==null){
            whereColumns=new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }
    @Override
    public boolean hasWhereColumns() {
        return whereColumns!=null&&whereColumns.isNotEmpty();
    }



    @Override
    public String toSql() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if(isExpressionUpdate){
            return expressionUpdate();
        }else{
            return entityUpdate();
        }
    }
    private String expressionUpdate(){

        if (!hasSetColumns()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        if (!hasWhere()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE' updates all table rows at once");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlEntityTableExpression table = getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(getSetColumns().toSql());
        sql.append(" WHERE ").append(getWhere().toSql());
        return sql.toString();
    }
    private String entityUpdate(){
        SqlEntityTableExpression table = getTable(0);

        if (!hasWhereColumns()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE' updates all table rows at once");
        }
        String tableName = table.getEntityMetadata().getTableName();
        return "UPDATE " + tableName + " SET " + getSetColumns().toSql() + " WHERE " +
                getWhereColumns().toSql();
    }
}