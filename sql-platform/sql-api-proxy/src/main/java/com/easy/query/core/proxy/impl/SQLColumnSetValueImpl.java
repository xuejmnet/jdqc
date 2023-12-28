package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumnSetExpression;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetValueImpl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String property;
    private final Object val;

    public SQLColumnSetValueImpl(TableAvailable table, String property, Object val){
        this.table = table;
        this.property = property;
        this.val = val;
    }

    @Override
    public void accept(Setter s) {
        s.set(table,property,val);
    }

    @Override
    public void accept(Selector s) {
        s.sqlNativeSegment("{0}",c->{
            c.value(val);
            c.setPropertyAlias(property);
        });
    }

    @Override
    public void accept(AsSelector s) {
        s.sqlNativeSegment("{0}",c->{
            c.value(val);
            c.setPropertyAlias(property);
        });
    }
}
