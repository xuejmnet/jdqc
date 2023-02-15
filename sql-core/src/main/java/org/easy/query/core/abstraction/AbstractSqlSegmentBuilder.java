package org.easy.query.core.abstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: DefaultSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:30
 * @Created by xuejiaming
 */
public abstract class AbstractSqlSegmentBuilder implements SqlSegment0Builder{
    private final List<SqlSegment> sqlSegments=new ArrayList<>();
    @Override
    public void append(SqlSegment sqlSegment) {
        sqlSegments.add(sqlSegment);
    }

    @Override
    public abstract String toSql();
    @Override
    public List<SqlSegment> getSqlSegments() {
        return sqlSegments;
    }

    @Override
    public boolean isEmpty() {
        return sqlSegments.isEmpty();
    }
}
