package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.client.JQDCClient;
import org.jdqc.sql.core.abstraction.sql.Select1;
import org.jdqc.sql.core.config.JDQCConfiguration;

/**
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class DefaultJQDCClient implements JQDCClient {
    private final JDQCConfiguration configuration;
    public DefaultJQDCClient(JDQCConfiguration configuration){

        this.configuration = configuration;
    }
    @Override
    public <T1> Select1<T1> select(Class<T1> clazz) {
        return new MySqlSelect1<>(clazz,new SelectContext(configuration));
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz, String alias) {
        return new MySqlSelect1<>(clazz,new SelectContext(configuration,alias));
    }

}
