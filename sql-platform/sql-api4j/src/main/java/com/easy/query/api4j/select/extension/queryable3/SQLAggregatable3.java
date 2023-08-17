package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression3;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatable3<T1,T2,T3> extends ClientQueryable3Available<T1,T2,T3> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().sumBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().sumOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().maxOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().maxOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().minOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().minOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgFloatOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().avgBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable3().avgFloatOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def, resultClass);
    }
}