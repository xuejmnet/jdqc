package com.easy.query.core.func.def.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/18 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class CountSQLFunctionImpl extends AbstractExpressionSQLFunction implements DistinctDefaultSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private boolean distinct = false;
    private boolean hasDefaultValue;

    public CountSQLFunctionImpl(List<ColumnExpression> columnExpressions) {

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
        if (hasDefaultValue) {
            if (distinct) {
                return String.format("COUNT(DISTINCT IFNULL(%s))", String.join(",", params));
            }
            return String.format("COUNT(IFNULL(%s))", String.join(",", params));
        } else {
            if (distinct) {
                return String.format("COUNT(DISTINCT %s)", String.join(",", params));
            }
            return String.format("COUNT(%s)", String.join(",", params));
        }
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

    @Override
    public AggregationType getAggregationType() {
        if (distinct) {
            return AggregationType.COUNT_DISTINCT;
        }
        return AggregationType.COUNT;
    }

    @Override
    public DistinctDefaultSQLFunction distinct() {
        distinct = true;
        return this;
    }

    @Override
    public DistinctDefaultSQLFunction valueOrDefault(Object value) {
        if(hasDefaultValue){
            throw new EasyQueryInvalidOperationException("can not repeat value or default");
        }
        if (value != null) {
            hasDefaultValue = true;
            columnExpressions.add(new ColumnFuncValueExpressionImpl(value));
        }
        return this;
    }
}
