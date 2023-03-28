package com.easyquery.springbootdemo.interceptor;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlLambdaEntityExpression;
import com.easy.query.core.util.EasyUtil;
import org.springframework.stereotype.Component;

/**
 * @FileName: AgeSelectInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/13 08:22
 * @author xuejiaming
 */
@Component
public class AgeSelectInterceptorStrategy implements EasyPredicateFilterInterceptor {

    @Override
    public String name() {
        return "xxxxxx";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return true;
    }

    @Override
    public void configure(Class<?> entityClass, SqlLambdaEntityExpression sqlLambdaEntityExpression, SqlPredicate<Object> sqlPredicate) {
        Property<Object,?> name = EasyUtil.getPropertyLambda(entityClass, "name", String.class);
        sqlPredicate.isNotNull(name);
    }
}
