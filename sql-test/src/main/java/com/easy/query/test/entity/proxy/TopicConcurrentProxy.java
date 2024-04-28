//package com.easy.query.test.entity.proxy;
//
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.proxy.AbstractProxyEntity;
//import com.easy.query.core.proxy.SQLColumn;
//import com.easy.query.core.proxy.SQLSelectAsExpression;
//import com.easy.query.core.proxy.fetcher.AbstractFetcher;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.test.entity.TopicConcurrent;
//import com.easy.query.core.proxy.columns.SQLStringColumn;
//import com.easy.query.core.proxy.columns.SQLNumberColumn;
//import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
//
///**
// * this file automatically generated by easy-query, don't modify it
// * 当前文件是easy-query自动生成的请不要随意修改
// * 如果出现属性冲突请使用@ProxyProperty进行重命名
// *
// * @author easy-query
// */
//public class TopicConcurrentProxy extends AbstractProxyEntity<TopicConcurrentProxy, TopicConcurrent> {
//
//    private static final Class<TopicConcurrent> entityClass = TopicConcurrent.class;
//
//    public static TopicConcurrentProxy createTable() {
//        return new TopicConcurrentProxy();
//    }
//
//    public TopicConcurrentProxy() {
//    }
//
//    /**
//     * {@link TopicConcurrent#getId}
//     */
//    public SQLStringColumn<TopicConcurrentProxy, java.lang.String> id() {
//        return getStringColumn("id", java.lang.String.class);
//    }
//
//    /**
//     * {@link TopicConcurrent#getStars}
//     */
//    public SQLNumberColumn<TopicConcurrentProxy, java.lang.Integer> stars() {
//        return getNumberColumn("stars", java.lang.Integer.class);
//    }
//
//    /**
//     * {@link TopicConcurrent#getTitle}
//     */
//    public SQLStringColumn<TopicConcurrentProxy, java.lang.String> title() {
//        return getStringColumn("title", java.lang.String.class);
//    }
//
//    /**
//     * {@link TopicConcurrent#getCreateTime}
//     */
//    public SQLDateTimeColumn<TopicConcurrentProxy, java.time.LocalDateTime> createTime() {
//        return getDateTimeColumn("createTime", java.time.LocalDateTime.class);
//    }
//
//    /**
//     * {@link TopicConcurrent#getAlias}
//     */
//    public SQLStringColumn<TopicConcurrentProxy, java.lang.String> alias() {
//        return getStringColumn("alias", java.lang.String.class);
//    }
//
//
//    @Override
//    public Class<TopicConcurrent> getEntityClass() {
//        return entityClass;
//    }
//
//
//    /**
//     * 数据库列的简单获取
//     *
//     * @return
//     */
//    public TopicConcurrentProxyFetcher FETCHER = new TopicConcurrentProxyFetcher(this, null, SQLSelectAsExpression.empty);
//
//
//    public static class TopicConcurrentProxyFetcher extends AbstractFetcher<TopicConcurrentProxy, TopicConcurrent, TopicConcurrentProxyFetcher> {
//
//        public TopicConcurrentProxyFetcher(TopicConcurrentProxy proxy, TopicConcurrentProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
//            super(proxy, prev, sqlSelectAsExpression);
//        }
//
//
//        /**
//         * {@link TopicConcurrent#getId}
//         */
//        public TopicConcurrentProxyFetcher id() {
//            return add(getProxy().id());
//        }
//
//        /**
//         * {@link TopicConcurrent#getStars}
//         */
//        public TopicConcurrentProxyFetcher stars() {
//            return add(getProxy().stars());
//        }
//
//        /**
//         * {@link TopicConcurrent#getTitle}
//         */
//        public TopicConcurrentProxyFetcher title() {
//            return add(getProxy().title());
//        }
//
//        /**
//         * {@link TopicConcurrent#getCreateTime}
//         */
//        public TopicConcurrentProxyFetcher createTime() {
//            return add(getProxy().createTime());
//        }
//
//        /**
//         * {@link TopicConcurrent#getAlias}
//         */
//        public TopicConcurrentProxyFetcher alias() {
//            return add(getProxy().alias());
//        }
//
//
//        @Override
//        protected TopicConcurrentProxyFetcher createFetcher(TopicConcurrentProxy cp, AbstractFetcher<TopicConcurrentProxy, TopicConcurrent, TopicConcurrentProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
//            return new TopicConcurrentProxyFetcher(cp, this, sqlSelectExpression);
//        }
//    }
//
//}
