package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativeLambdaKtExpressionContextImpl<T1> implements SQLAliasNativeLambdaKtExpressionContext<T1> {
    private final SQLAliasNativePropertyExpressionContext sqlAliasNativePropertyExpressionContext;

    public SQLAliasNativeLambdaKtExpressionContextImpl(SQLAliasNativePropertyExpressionContext sqlAliasNativePropertyExpressionContext){

        this.sqlAliasNativePropertyExpressionContext = sqlAliasNativePropertyExpressionContext;
    }
    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1> expressionAlias(KProperty1<? super T1, ?> property) {
        sqlAliasNativePropertyExpressionContext.expressionAlias(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1> expression(KProperty1<? super T1, ?> property) {
        sqlAliasNativePropertyExpressionContext.expression(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativeLambdaKtExpressionContext<T1> expression(KtQueryable<TEntity> subQuery) {
        sqlAliasNativePropertyExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public <T2> SQLAliasNativeLambdaKtExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, KProperty1<? super T2, ?> property) {
        sqlAliasNativePropertyExpressionContext.expression(table.getTable(),EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1> value(Object val) {
        sqlAliasNativePropertyExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1> format(Object formatVal) {
        sqlAliasNativePropertyExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1> setAlias(String alias) {
        sqlAliasNativePropertyExpressionContext.setAlias(alias);
        return this;
    }
}
