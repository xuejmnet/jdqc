package com.easy.query.gauss.db.func;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.impl.AvgSQLFunction;

import java.util.List;

/**
 * create time 2023/10/19 09:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class GaussDBAvgSQLFunction extends AvgSQLFunction {
    public GaussDBAvgSQLFunction(List<ColumnExpression> columnExpressions) {
        super(columnExpressions);
    }

    @Override
    protected SQLFunction createValueOrDefaultSQLFunction(List<ColumnExpression> columnExpressions) {
        return new GaussDBNullDefaultSQLFunction(columnExpressions);
    }
}
