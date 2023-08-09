package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/8/8 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractInsertUpdateSetColumnSQLSegmentImpl {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;
    protected final ColumnMetadata columnMetadata;

    public AbstractInsertUpdateSetColumnSQLSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.columnMetadata=table.getEntityMetadata().getColumnNotNull(propertyName);
    }
    public String toSQLWithParameter(ToSQLContext toSQLContext, SQLParameter sqlParameter){

        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if(columnValueSQLConverter==null){
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return "?";
        }else{
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            columnValueSQLConverter.valueConverter(table,columnMetadata,sqlParameter,sqlPropertyConverter,runtimeContext);
            return sqlPropertyConverter.toSQL(toSQLContext);
        }
    }
}
