package com.easy.query.core.func.column;

import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/18 13:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionExpression extends ColumnExpression{
    SQLFunction getSQLFunction();
}
