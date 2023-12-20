package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.common.tuple.MergeSelectTuple4;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression5;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression4<T1Proxy,T2Proxy,T3Proxy,T4Proxy, DraftFetcher<TR,TRProxy>> selectExpression){
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy());
        ClientQueryable<TR> select = getClientQueryable4().select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraftMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, DraftFetcher<TR,TRProxy>> selectExpression){
        return selectDraft((t1,t2,t3,t4)->selectExpression.apply(new MergeTuple4<>(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy())));
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass, SQLFuncExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, TRProxy, SQLSelectAsExpression> selectExpression) {
        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
        ClientQueryable<TR> select = getClientQueryable4().select(resultEntityClass, (t, t1, t2, t3) -> {
            SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), trProxy);
            sqlSelectAsExpression.accept(t.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectMerge(Class<TR> resultEntityClass, SQLFuncExpression1<MergeSelectTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, TRProxy>, SQLSelectAsExpression> selectExpression) {
        return select(resultEntityClass, (t, t1, t2, t3, tr) -> {
            return selectExpression.apply(new MergeSelectTuple4<>(t, t1, t2, t3, tr));
        });
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(TRProxy trProxy, SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLSelectAsExpression> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable4().select(trProxy.getEntityClass(), (t, t1, t2, t3) -> {
            SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            sqlSelectAsExpression.accept(t.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(TRProxy trProxy, SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, SQLSelectAsExpression> selectExpression) {
        return select(trProxy, (t, t1, t2, t3) -> {
            return selectExpression.apply(new MergeTuple4<>(t, t1, t2, t3));
        });
    }
}
