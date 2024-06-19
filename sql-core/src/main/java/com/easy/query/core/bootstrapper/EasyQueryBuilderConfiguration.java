package com.easy.query.core.bootstrapper;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.client.DefaultEasyQueryClient;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.def.DefaultSQLClientApiFactory;
import com.easy.query.core.api.dynamic.executor.query.DefaultWhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.DefaultObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.basic.extension.formater.DefaultSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.EmptyJdbcExecutorListener;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.track.DefaultTrackManager;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.conn.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.EasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultEasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultEasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.DefaultEasyShardingExecutorService;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.EasyQueryOptionBuilder;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.dialect.DefaultSQLKeyword;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.context.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.datasource.DefaultDataSourceManager;
import com.easy.query.core.datasource.DefaultDataSourceUnitFactory;
import com.easy.query.core.datasource.replica.DefaultReplicaDataSourceManager;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.DefaultAssertExceptionFactory;
import com.easy.query.core.expression.executor.parser.DefaultEasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.query.DefaultExecutionContextFactory;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.func.ColumnFunctionFactory;
import com.easy.query.core.expression.func.DefaultColumnFunctionFactory;
import com.easy.query.core.expression.include.EasyIncludeProcessorFactory;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.parser.factory.DefaultSQLExpressionInvokeFactory;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.factory.DefaultSQLSegmentFactory;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.factory.DefaultEasyExpressionBuilderFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.DefaultEasyExpressionFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.include.DefaultIncludeParserEngine;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.inject.impl.ServiceCollectionImpl;
import com.easy.query.core.job.DefaultEasyTimeJobManager;
import com.easy.query.core.job.EasyTimeJobManager;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.DefaultEasyQueryDataSource;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.JavaLanguageShardingComparer;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.manager.DefaultShardingQueryCountManager;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.rewrite.DefaultRewriteContextFactory;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.router.DefaultRouteContextFactory;
import com.easy.query.core.sharding.router.RouteContextFactory;
import com.easy.query.core.sharding.router.datasource.DataSourceRouter;
import com.easy.query.core.sharding.router.datasource.ShardingDataSourceRouter;
import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.router.datasource.engine.DefaultDataSourceRouteEngine;
import com.easy.query.core.sharding.router.descriptor.DefaultRouteDescriptorFactor;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptorFactory;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sharding.router.manager.impl.DefaultDataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.impl.DefaultTableRouteManager;
import com.easy.query.core.sharding.router.table.ShardingTableRouter;
import com.easy.query.core.sharding.router.table.TableRouter;
import com.easy.query.core.sharding.router.table.engine.DefaultTableRouteEngine;
import com.easy.query.core.sharding.router.table.engine.TableRouteEngine;

import javax.sql.DataSource;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2023/4/26 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryBuilderConfiguration {
    protected DataSource dataSource;
    private String name;
    protected final EasyQueryOptionBuilder easyQueryOptionBuilder = new EasyQueryOptionBuilder();
    private final ServiceCollection serviceCollection = new ServiceCollectionImpl();

    public EasyQueryBuilderConfiguration() {
        defaultConfiguration();
    }

    public String getName() {
        return name;
    }

    public EasyQueryBuilderConfiguration setName(String name) {
        this.name = name;
        return this;
    }

    private void defaultConfiguration() {
        replaceService(EasyQueryDataSource.class, DefaultEasyQueryDataSource.class)
                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
                .replaceService(NameConversion.class, UnderlinedNameConversion.class)
                .replaceService(QueryConfiguration.class)
                .replaceService(EntityMetadataManager.class, DefaultEntityMetadataManager.class)
                .replaceService(SQLExpressionInvokeFactory.class, DefaultSQLExpressionInvokeFactory.class)
                .replaceService(ExpressionBuilderFactory.class, DefaultEasyExpressionBuilderFactory.class)
                .replaceService(SQLClientApiFactory.class, DefaultSQLClientApiFactory.class)
                .replaceService(TrackManager.class, DefaultTrackManager.class)
                .replaceService(EasyPageResultProvider.class, DefaultEasyPageResultProvider.class)
                .replaceService(EasyPrepareParser.class, DefaultEasyPrepareParser.class)
                .replaceService(ConnectionManager.class, DefaultConnectionManager.class)
                .replaceService(DataSourceRouteManager.class, DefaultDataSourceRouteManager.class)
                .replaceService(DataSourceRouter.class, ShardingDataSourceRouter.class)
                .replaceService(DataSourceRouteEngine.class, DefaultDataSourceRouteEngine.class)
                .replaceService(TableRouteManager.class, DefaultTableRouteManager.class)
                .replaceService(TableRouter.class, ShardingTableRouter.class)
                .replaceService(TableRouteEngine.class, DefaultTableRouteEngine.class)
                .replaceService(RouteContextFactory.class, DefaultRouteContextFactory.class)
                .replaceService(RewriteContextFactory.class, DefaultRewriteContextFactory.class)
                .replaceService(ExecutionContextFactory.class, DefaultExecutionContextFactory.class)
                .replaceService(EntityExpressionExecutor.class, DefaultEntityExpressionExecutor.class)
//                .replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class)
                .replaceService(ShardingExecutorService.class, DefaultEasyShardingExecutorService.class)
                .replaceService(ExpressionFactory.class, DefaultEasyExpressionFactory.class)
                .replaceService(ShardingComparer.class, JavaLanguageShardingComparer.class)
                .replaceService(JdbcTypeHandlerManager.class, EasyJdbcTypeHandlerManager.class)
                .replaceService(QueryRuntimeContext.class, DefaultEasyQueryRuntimeContext.class)
                .replaceService(EasyDataSourceConnectionFactory.class, DefaultEasyDataSourceConnectionFactory.class)
                .replaceService(EasyConnectionFactory.class, DefaultEasyConnectionFactory.class)
                .replaceService(DataSourceManager.class, DefaultDataSourceManager.class)
                .replaceService(ShardingQueryCountManager.class, DefaultShardingQueryCountManager.class)
                .replaceService(ColumnFunctionFactory.class, DefaultColumnFunctionFactory.class)
                .replaceService(RouteDescriptorFactory.class, DefaultRouteDescriptorFactor.class)
                .replaceService(DataSourceUnitFactory.class, DefaultDataSourceUnitFactory.class)
                .replaceService(SQLSegmentFactory.class, DefaultSQLSegmentFactory.class)
                .replaceService(EasyTimeJobManager.class, DefaultEasyTimeJobManager.class)
                .replaceService(IncludeProcessorFactory.class, EasyIncludeProcessorFactory.class)
                .replaceService(IncludeParserEngine.class, DefaultIncludeParserEngine.class)
                //whereObject的默认实现
                .replaceService(WhereObjectQueryExecutor.class, DefaultWhereObjectQueryExecutor.class)
                //orderByObject的默认实现
                .replaceService(ObjectSortQueryExecutor.class, DefaultObjectSortQueryExecutor.class)
                //jdbc执行的监听用于统计耗时sql
                .replaceService(JdbcExecutorListener.class, EmptyJdbcExecutorListener.class)
                //断言错误的异常工厂(firstNotNull singleNotNull findNotNull...)
                .replaceService(AssertExceptionFactory.class, DefaultAssertExceptionFactory.class)
                //sql参数打印格式化
                .replaceService(SQLParameterPrintFormat.class, DefaultSQLParameterPrintFormat.class)
                .replaceService(SQLFunc.class, SQLFuncImpl.class)
//                .replaceService(NavigateNamedGuess.class, DefaultNavigateNamedGuess.class)
                .replaceService(EasyQueryClient.class, DefaultEasyQueryClient.class);
    }

    public EasyQueryBuilderConfiguration setDefaultDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementType 依赖注入当前实例类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyQueryBuilderConfiguration replaceService(Class<TImplement> implementType) {
        serviceCollection.addService(implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementInstance 依赖注入当前实例
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyQueryBuilderConfiguration replaceService(TImplement implementInstance) {
        serviceCollection.addService(implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType   依赖注入的接口
     * @param implementType 依赖注入的实现类
     * @param <TService>    接口类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceService(Class<TService> serviceType, Class<TImplement> implementType) {
        serviceCollection.addService(serviceType, implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType       依赖注入的接口
     * @param implementInstance 依赖注入的实现
     * @param <TService>        接口类型
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceService(Class<TService> serviceType, TImplement implementInstance) {
        serviceCollection.addService(serviceType, implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType      依赖注入的接口
     * @param implementFactory 依赖注入的实现工厂
     * @param <TService>       接口类型
     * @param <TImplement>     实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyQueryBuilderConfiguration replaceServiceFactory(Class<TService> serviceType, Function<ServiceProvider, TImplement> implementFactory) {
        serviceCollection.addServiceFactory(serviceType, implementFactory);
        return this;
    }

    public EasyQueryBuilderConfiguration useDatabaseConfigure(DatabaseConfiguration databaseConfiguration) {
        databaseConfiguration.configure(serviceCollection);
        return this;
    }

    public EasyQueryBuilderConfiguration useStarterConfigure(StarterConfigurer starterConfigurer) {
        starterConfigurer.configure(serviceCollection);
        return this;
    }

    public EasyQueryBuilderConfiguration optionConfigure(Consumer<EasyQueryOptionBuilder> configure) {
        configure.accept(this.easyQueryOptionBuilder);
        if (this.easyQueryOptionBuilder.isUseReplica()) {
            replaceService(DataSourceManager.class, DefaultReplicaDataSourceManager.class);
        } else {
            replaceService(DataSourceManager.class, DefaultDataSourceManager.class);
        }
        return this;
    }

    /**
     * 创建对应的查询器
     *
     * @return EasyQuery实例
     */
    public EasyQueryClient build() {
        if (this.dataSource == null) {
            throw new IllegalArgumentException("data source null");
        }
        replaceService(DataSource.class, this.dataSource);
        EasyQueryOption easyQueryOption = easyQueryOptionBuilder.build();
        replaceService(easyQueryOption);
        ServiceProvider serviceProvider = serviceCollection.build();
        return serviceProvider.getService(EasyQueryClient.class);
    }

}
