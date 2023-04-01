package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.parser.abstraction.internal.IndexAware;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: TableSegment.java
 * @Description:  [table | (table expression)]  [alias] | [on predicate] [where]
 * @Date: 2023/3/3 22:06
 * @author xuejiaming
 */
public interface TableExpressionSegment extends ExpressionSegment, IndexAware {
    Class<?> entityClass();
    PredicateSegment getOn();
    boolean hasOn();
    String getAlias();
    int getIndex();
    String getSelectTableSource();
//    String getSqlColumnSegment(String propertyName);
}