package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: TableSegment.java
 * @Description:  [table | (table expression)]  [alias] | [on predicate] [where]
 * @Date: 2023/3/3 22:06
 * @author xuejiaming
 */
public interface TableExpressionBuilder extends ExpressionBuilder {
    Class<?> getEntityClass();
    PredicateSegment getOn();
    boolean hasOn();
    String getAlias();
    int getIndex();
//    String getSqlColumnSegment(String propertyName);
    EntityTableAvailable getEntityTable();
}
