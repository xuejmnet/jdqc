package com.easy.query.api.proxy.entity.update.impl;

import com.easy.query.api.proxy.entity.insert.extension.ProxyColumnConfigurer;
import com.easy.query.api.proxy.entity.insert.extension.ProxyColumnConfigurerImpl;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;

import java.util.function.Function;

/**
 * create time 2023/12/7 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityUpdatable<TProxy, T> {

    @Override
    public EntityUpdatable<TProxy, T> ignoreVersion(boolean ignored) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> noInterceptor() {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useInterceptor(String name) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useInterceptor() {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> batch(boolean use) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {

    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public EntityUpdatable<TProxy, T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return null;
    }

    @Override
    public TProxy getTProxy() {
        return null;
    }

    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return null;
    }

    @Override
    public EntityUpdatable<TProxy, T> configure(SQLExpression1<ContextConfigurer> configurer) {
        return this;
    }


    @Override
    public EntityUpdatable<TProxy, T> columnConfigure(SQLExpression2<TProxy, ProxyColumnConfigurer<TProxy, T>> columnConfigureExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> setColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> setIgnoreColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> whereColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return this;
    }
}
