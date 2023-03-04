package org.easy.query.core.expression.segment.condition;

import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.internal.WherePredicate;
import org.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate0;
import org.easy.query.core.expression.segment.condition.predicate.ColumnPredicate0;
import org.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate0;
import org.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate0;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

import java.util.Collection;

/**
 * @FileName: DefaultSqlPredicate.java
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlPredicate<T1> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final PredicateSegment rootPredicateSegment;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlPredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment) {
        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public int getIndex() {
        return index;
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    protected void appendThisPredicate(Property<T1, ?> column, Object val, SqlPredicateCompareEnum condition) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        nextPredicateSegment.setPredicate(new ColumnValuePredicate0(table, propertyName, val, condition, sqlEntityExpression));
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.EQ);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GT);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LIKE);
            nextAnd();
        }
        return this;
    }
    @Override
    public SqlPredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NOT_LIKE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LT);
            nextAnd();
        }
        return this;
    }


    @Override
    public SqlPredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate0(table, propertyName, SqlPredicateCompareEnum.IS_NULL, sqlEntityExpression));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate0(table, propertyName, SqlPredicateCompareEnum.IS_NOT_NULL, sqlEntityExpression));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate0(table, propertyName,collection, SqlPredicateCompareEnum.IN, sqlEntityExpression));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate0(table, propertyName,collection, SqlPredicateCompareEnum.NOT_IN, sqlEntityExpression));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if (condition) {
            SqlEntityTableExpression leftTable = sqlEntityExpression.getTable(getIndex());
            String leftPropertyName = leftTable.getPropertyName(column1);
            SqlEntityTableExpression rightTable = sqlEntityExpression.getTable(sub.getIndex());
            String rightPropertyName = rightTable.getPropertyName(column2);
            nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate0(leftTable, leftPropertyName, rightTable, rightPropertyName, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
            nextAnd();

//            PredicateSegment0 predicateSegment =  PredicateSegment0.createColumn2Column(index, columnName, selectContext, SqlCompareEnum.EQ,sub.getIndex(),columnName2);
//            sqlSegment0Builder.append(predicateSegment);
        }
        return this;
    }

    @Override
    public <T2, TChain2> WherePredicate<T2, TChain2> then(WherePredicate<T2, TChain2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return sub;
    }

    @Override
    public SqlPredicate<T1> and(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> and(boolean condition, SqlExpression<SqlPredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlPredicate<T1> sqlPredicate = sqlEntityExpression.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, sqlEntityExpression, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> or(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> or(boolean condition, SqlExpression<SqlPredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlPredicate<T1> sqlPredicate = sqlEntityExpression.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, sqlEntityExpression, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }
}
