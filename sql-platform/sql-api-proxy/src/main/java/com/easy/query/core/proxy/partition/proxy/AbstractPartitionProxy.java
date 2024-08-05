package com.easy.query.core.proxy.partition.proxy;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.BasicResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.ValueObjectProxyEntity;
import com.easy.query.core.proxy.partition.Partition1;

/**
 * create time 2024/8/4 19:53
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractPartitionProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements PartitionProxy {

    private static final Class<Partition1> entityClass = Partition1.class;

    private TSourceProxy selectTable;
    private final ResultColumnMetadata[] propTypes;

    public AbstractPartitionProxy(TSourceProxy table, int capacity) {
        selectTable = table;
        this.propTypes = new ResultColumnMetadata[capacity];
    }

    public TSourceProxy partitionTable() {
        return selectTable;
    }

    protected void setSelectTable(TSourceProxy selectTable) {
        this.selectTable = selectTable;
    }

    @Override
    public ResultColumnMetadata[] getPartitionByPropTypes() {
        return propTypes;
    }

    @Override
    public <TProperty> void fetch(int index, PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn) {

        SQLSelectAsExpression sqlSelectAsExpression = column.as(tablePropColumn);
        if (sqlSelectAsExpression instanceof ValueObjectProxyEntity) {
            throw new EasyQueryInvalidOperationException("draft result not support value object columns");
        }

        if (column instanceof SQLColumn && column.getTable() != null) {
            EntityMetadata entityMetadata = column.getTable().getEntityMetadata();
            ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(column.getValue());
            if (columnMetadata != null) {
                propTypes[index] = new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
            } else {
                propTypes[index] = new BasicResultColumnMetadata(column.getPropertyType(), null, new BasicJdbcProperty(index, column.getPropertyType()));
            }
        } else {
            propTypes[index] = new BasicResultColumnMetadata(column.getPropertyType(), null, new BasicJdbcProperty(index, column.getPropertyType()));
        }
        selectExpression(sqlSelectAsExpression);
    }
}
