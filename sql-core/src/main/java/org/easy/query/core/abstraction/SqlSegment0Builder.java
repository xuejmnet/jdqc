package org.easy.query.core.abstraction;

import java.util.List;

/**
 * @FileName: SqlSegment0Builder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 12:49
 * @Created by xuejiaming
 */
public interface SqlSegment0Builder {
    List<SqlSegment> getSqlSegments();
    void append(SqlSegment sqlSegment);
    boolean isEmpty();
    String toSql();
}
