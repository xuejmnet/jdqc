package com.easy.query.core.api.client;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.api.insert.Insertable;

import java.util.Collection;

/**
 *
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 * @Created by xuejiaming
 */
public interface EasyQuery {
    EasyQueryRuntimeContext getRuntimeContext();
    <T> Queryable<T> query(Class<T> clazz);
    <T> Queryable<T> query(Class<T> clazz, String alias);
    default Transaction beginTransaction(){
        return beginTransaction(null);
    }
    Transaction beginTransaction(Integer isolationLevel);

    <T> Insertable<T> insert(T entity);
    <T> Insertable<T> insert(Collection<T> entities);
    <T> ExpressionUpdatable<T> update(Class<T> entityClass);
    <T> EntityUpdatable<T> update(T entity);
    <T> EntityUpdatable<T> update(Collection<T> entities);
    <T> EntityDeletable<T> delete(T entity);
    <T> EntityDeletable<T> delete(Collection<T> entities);
    <T> ExpressionDeletable<T> delete(Class<T> entityClass);
}