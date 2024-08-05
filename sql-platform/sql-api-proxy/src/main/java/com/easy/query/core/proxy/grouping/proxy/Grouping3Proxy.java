package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.grouping.Grouping2;
import com.easy.query.core.proxy.grouping.Grouping3;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/28 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping3Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
        TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
        TSourceProxy> extends AbstractGroupingProxy<Grouping3Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2,TKey3Proxy, TKey3, TSourceProxy>, Grouping3<TKey1,TKey2,TKey3>,TSourceProxy> {

    private static final Class<Grouping2> entityClass = Grouping2.class;
    private final TKey1Proxy k1;
    private final TKey2Proxy k2;
    private final TKey3Proxy k3;

    public Grouping3Proxy(TKey1Proxy k1, TKey2Proxy k2, TKey3Proxy k3, TSourceProxy tSourceProxy) {
        super(tSourceProxy);
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
    }


    @Override
    public Class<Grouping3<TKey1,TKey2,TKey3>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    public TKey1Proxy key1() {
        return k1;
    }
    public TKey2Proxy key2() {
        return k2;
    }
    public TKey3Proxy key3() {
        return k3;
    }
    @Override
    public void accept(GroupSelector s) {
        acceptGroupSelector(k1,s);
        acceptGroupSelector(k2,s);
        acceptGroupSelector(k3,s);
    }
}