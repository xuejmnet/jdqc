package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderSelector extends SQLProxyNative<ProxyOrderSelector> {
    OrderSelector getOrderSelector();


    default <TProxy extends ProxyEntity<TProxy,T>,T> ProxyOrderSelector columns(SQLColumn<TProxy,?>... columns) {
        if (columns != null) {
            for (SQLColumn<TProxy,?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }


   default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyOrderSelector column(SQLColumn<TProxy,TProperty> column){
       getOrderSelector().column(column.getTable(), column.value());
       return this;
   }

   default ProxyOrderSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getOrderSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }
}
