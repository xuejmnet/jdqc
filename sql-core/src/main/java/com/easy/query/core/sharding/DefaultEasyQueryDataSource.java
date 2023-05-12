package com.easy.query.core.sharding;

import com.easy.query.core.basic.jdbc.con.ConnectionStrategyEnum;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.replica.ReplicaDataSourceManager;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/12 16:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultEasyQueryDataSource implements EasyQueryDataSource {

    private final EasyQueryOption easyQueryOption;
    private final DataSourceManager dataSourceManager;

    public DefaultEasyQueryDataSource(EasyQueryOption easyQueryOption, DataSourceManager dataSourceManager) {
        this.easyQueryOption = easyQueryOption;
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public String getDefaultDataSourceName() {
        return dataSourceManager.getDefaultDataSourceName();
    }

    @Override
    public DataSource getDefaultDataSource() {
        return dataSourceManager.getDefaultDataSource();
    }

    @Override
    public boolean addDataSource(String dataSourceName, DataSource dataSource) {
       return dataSourceManager.addDataSource(dataSourceName,dataSource);
    }

    @Override
    public Map<String, DataSource> getAllDataSource() {
        return dataSourceManager.getAllDataSource();
    }

    @Override
    public DataSource getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {
        return dataSourceManager.getDataSourceOrNull(dataSourceName,connectionStrategy);
    }
}
