package com.easy.query.sql.starter;

import com.easy.query.core.basic.jdbc.con.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.con.DefaultEasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

/**
 * @FileName: SpringConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/3/10 22:01
 * @author xuejiaming
 */
public class SpringConnectionManager extends DefaultConnectionManager {
    public SpringConnectionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean currentThreadInTransaction() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Override
    protected EasyConnection doGetEasyConnection(Integer isolationLevel) {
        return new DefaultEasyConnection( DataSourceUtils.getConnection(dataSource),isolationLevel);
    }

    @Override
    public void closeEasyConnection(EasyConnection easyConnection) {
        if(easyConnection==null){
            return;
        }
        if(!this.currentThreadInTransaction()&&super.currentThreadInTransaction()){
            try {
                easyConnection.close();
            } catch (Exception e) {
                throw new EasyQueryException(e);
            }
        }else{
            DataSourceUtils.releaseConnection(easyConnection.getConnection(),dataSource);
        }

    }
}
