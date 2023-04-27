package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.result.impl.EmptyStreamResult;

import java.sql.PreparedStatement;

/**
 * create time 2023/4/14 16:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryExecuteResult implements ExecuteResult{
    private static final QueryExecuteResult instance=new QueryExecuteResult(EmptyStreamResult.getInstance());
    public static QueryExecuteResult empty(){
        return instance;
    }
    private final StreamResult streamResult;
    private final PreparedStatement preparedStatement;

    public QueryExecuteResult(StreamResult streamResult){
       this(streamResult,null);
    }

    public QueryExecuteResult(StreamResult streamResult, PreparedStatement preparedStatement){
        this.streamResult = streamResult;

        this.preparedStatement = preparedStatement;
    }

    public StreamResult getStreamResult() {
        return streamResult;
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
        if(preparedStatement!=null){
            preparedStatement.close();
        }
    }
}
