package com.easy.query.processor.templates;

/**
 * create time 2023/11/8 17:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptConstant {

    public static final String PROXY_TEMPLATE = "package @{package};\n" +
            "\n" +
            "import com.easy.query.core.expression.parser.core.available.TableAvailable;\n" +
            "import com.easy.query.core.proxy.AbstractProxyEntity;\n" +
            "import com.easy.query.core.proxy.SQLColumn;\n" +
            "@{imports}" +
            "\n" +
            "/**\n" +
            " * this file automatically generated by easy-query, don't modify it\n" +
            " *\n" +
            " * @author xuejiaming\n" +
            " */\n" +
            "public class @{entityClassProxy} extends AbstractProxyEntity<@{entityClassProxy}, @{entityClass}> {\n" +
            "\n" +
            "    private static final Class<@{entityClass}> entityClass = @{entityClass}.class;\n" +
            "    public static @{entityClassProxy} createTable() {\n" +
            "        return new @{entityClassProxy}();\n" +
            "    }\n" +
            "\n" +
            "    public @{entityClassProxy}() {\n" +
            "    }\n" +
            "\n" +
            "    @{fieldContent}" +
            "\n" +
            "    @Override\n" +
            "    public Class<@{entityClass}> getEntityClass() {\n" +
            "        return entityClass;\n" +
            "    }\n" +
            "\n" +
            "    @{valueObjectContext}\n" +
            "\n" +
            "    @{selectorContext}\n" +
            "}";




    public static final String FIELD_TEMPLATE = "\n" +
            "    @{comment}\n" +
            "    public SQLColumn<@{entityClassProxy},@{propertyType}> @{property}(){\n" +
            "        return get(\"@{property}\");\n" +
            "    }";
    public static final String FIELD_VALUE_OBJECT_TEMPLATE = "\n" +
            "    @{comment}\n" +
            "    public @{entityClass}Proxy @{property}() {\n" +
            "        return getValueObject(new @{entityClass}Proxy(getTable(), getValueProperty(\"@{property}\")));\n" +
            "    }";


    public static final String FIELD_VALUE_OBJECT_CLASS_TEMPLATE = "\n" +
            "    public static class @{entityClass}Proxy extends AbstractValueObjectProxyEntity<@{mainEntityClassProxy}, @{entityClass}> {\n" +
            "\n" +
            "        private @{entityClass}Proxy(TableAvailable table, String propertyName) {\n" +
            "            super(table, propertyName);\n" +
            "        }\n" +
            "\n" +
            "        @{fieldContent}" +
            "\n" +
            "        @{valueObjectContext}" +
            "    }";


    public static final String PROXY_SELECTOR_TEMPLATE =
            "    public @{selectorName} selector() {\n" +
            "        return new @{selectorName}(this);\n" +
            "    }\n" +
            "\n" +
            "    public static class @{selectorName} extends AbstractSelector<@{entityClassProxy},@{entityClass}, @{selectorName}> {\n" +
            "\n" +
            "        public @{selectorName}(@{entityClassProxy} proxy) {\n" +
            "            super(proxy);\n" +
            "        }\n" +
            "\n" +
            "        @{fieldSelectorContent}" +
            "    }";
    public static final String FIELD_SELECTOR_PROPERTY_TEMPLATE = "\n" +
            "    @{comment}\n" +
            "    public @{selectorName} @{property}() {\n" +
            "            return add(getProxy().@{property}());\n" +
            "    }";
}
