package org.easy.query.core.basic.api.select;

import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;


/**
 *
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 * @Created by xuejiaming
 */
public interface Queryable3<T1,T2,T3> extends Queryable<T1, Queryable3<T1,T2,T3>> {
   default Queryable3<T1,T2,T3> where(SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> whereExpression)
   {
       return where(true,whereExpression);
   }
    Queryable3<T1,T2,T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> whereExpression);
    default Queryable3<T1,T2,T3> groupBy(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return groupBy(true,selectExpression);
    }
    Queryable3<T1,T2,T3> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
    default Queryable3<T1,T2,T3> orderByAsc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return orderByAsc(true,selectExpression);
    }
     Queryable3<T1,T2,T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
    default Queryable3<T1,T2,T3> orderByDesc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return orderByDesc(true,selectExpression);
    }
     Queryable3<T1,T2,T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
}
