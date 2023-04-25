package com.easy.query.core.sharding.route.datasource.abstraction;

import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

/**
 * create time 2023/4/19 13:10
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRoute implements DataSourceRoute {
    private final EntityMetadataManager entityMetadataManager;

    public AbstractDataSourceRoute(EntityMetadataManager entityMetadataManager){

        this.entityMetadataManager = entityMetadataManager;
    }

    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }
}
