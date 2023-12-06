package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAsImpl extends SQLSelectImpl implements SQLSelectAsExpression {

    private final Consumer<AsSelector> asSelectConsumer;

    public SQLSelectAsImpl(Consumer<Selector> selectorConsumer,Consumer<AsSelector> asSelectConsumer){
        super(selectorConsumer);
        this.asSelectConsumer = asSelectConsumer;
    }

    @Override
    public void accept(AsSelector f) {
        asSelectConsumer.accept(f);
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(s->{
            throw new UnsupportedOperationException();
        },s -> {
            s.columnAs(this.getTable(), this.getValue(), propColumn.getValue());
        });
    }


    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }
}