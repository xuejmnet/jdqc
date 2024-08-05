package com.easy.query.core.proxy.core.draft.proxy;

import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class Draft3Proxy<T1,T2,T3> extends AbstractDraftProxy<Draft3Proxy<T1,T2,T3>, Draft3<T1,T2,T3>> {

    private static final Class<Draft3> entityClass = Draft3.class;

    public static <TR1,TR2,TR3> Draft3Proxy<TR1,TR2,TR3> createTable() {
        return new Draft3Proxy<>();
    }

    public Draft3Proxy() {
        super(3);
    }

    /**
     * {@link Draft3#getValue1}
     */
    public SQLAnyTypeColumn<Draft3Proxy<T1,T2,T3>, T1> value1() {
        return getAnyTypeColumn("value1",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[0]).map(o->o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Draft3#getValue2()}
     */
    public SQLAnyTypeColumn<Draft3Proxy<T1,T2,T3>, T2> value2() {
        return getAnyTypeColumn("value2",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[1]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Draft3#getValue3()}
     */
    public SQLAnyTypeColumn<Draft3Proxy<T1,T2,T3>, T3> value3() {
        return getAnyTypeColumn("value3",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[2]).map(o->o.getPropertyType()).orElse(null)));
    }


    @Override
    public Class<Draft3<T1,T2,T3>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public Draft3ProxyFetcher<T1,T2,T3> FETCHER = new Draft3ProxyFetcher<>(this, null, SQLSelectAsExpression.empty);


    public static class Draft3ProxyFetcher<TF1,TF2,TF3> extends AbstractFetcher<Draft3Proxy<TF1,TF2,TF3>, Draft3<TF1,TF2,TF3>, Draft3ProxyFetcher<TF1,TF2,TF3>> {

        public Draft3ProxyFetcher(Draft3Proxy<TF1,TF2,TF3> proxy, Draft3ProxyFetcher<TF1,TF2,TF3> prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link Draft3#getValue1}
         */
        public Draft3ProxyFetcher<TF1,TF2,TF3> value1() {
            return add(getProxy().value1());
        }


        /**
         * {@link Draft3#getValue2}
         */
        public Draft3ProxyFetcher<TF1,TF2,TF3> value2() {
            return add(getProxy().value2());
        }
        /**
         * {@link Draft3#getValue3}
         */
        public Draft3ProxyFetcher<TF1,TF2,TF3> value3() {
            return add(getProxy().value3());
        }


        @Override
        protected Draft3ProxyFetcher<TF1,TF2,TF3> createFetcher(
                Draft3Proxy<TF1,TF2,TF3> cp,
                AbstractFetcher<Draft3Proxy<TF1,TF2,TF3>, Draft3<TF1,TF2,TF3>, Draft3ProxyFetcher<TF1,TF2,TF3>> prev,
                SQLSelectAsExpression sqlSelectExpression
        ) {
            return new Draft3ProxyFetcher<>(cp, this, sqlSelectExpression);
        }
    }

}
