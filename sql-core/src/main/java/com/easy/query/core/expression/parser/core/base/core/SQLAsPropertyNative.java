package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContextImpl;

/**
 * create time 2023/7/31 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsPropertyNative<TChain> extends SQLTableOwner {
    <T> SQLAsNative<T> getSQLAsNative();
    TChain castTChain();
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment){
        return sqlNativeSegment(true,sqlSegment);
    }
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(boolean condition,String sqlSegment){
        return sqlNativeSegment(condition,sqlSegment,c->{});
    }

    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        if(condition){
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativePropertyExpressionContextImpl(getTable(),context));
            });
        }
        return castTChain();
    }

}
