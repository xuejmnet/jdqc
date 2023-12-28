package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalTimeProxy extends AbstractBasicProxyEntity<LocalTimeProxy, LocalTime> {
    public static LocalTimeProxy createTable() {
        return new LocalTimeProxy();
    }
    private static final Class<LocalTime> entityClass = LocalTime.class;

    private LocalTimeProxy() {
    }
    public LocalTimeProxy(LocalTime val) {
        set(val);
    }


    public LocalTimeProxy(SQLColumn<?,LocalTime> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<LocalTime>> LocalTimeProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }
    @Override
    public Class<LocalTime> getEntityClass() {
        return entityClass;
    }
}