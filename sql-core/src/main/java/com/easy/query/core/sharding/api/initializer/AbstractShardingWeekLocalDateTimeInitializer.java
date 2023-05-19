package com.easy.query.core.sharding.api.initializer;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingWeekLocalDateTimeInitializer<T> extends AbstractShardingLocalDateTimeInitializer<T> {

    @Override
    protected LocalDateTime getBeginTimeToStart(LocalDateTime beginTime) {
        return EasyUtil.getWeekStart(beginTime);
    }

    @Override
    protected LocalDateTime getNextTime(LocalDateTime currentTime) {
        return currentTime.plusWeeks(1);
    }

    @Override
    protected String formatTail(LocalDateTime time) {
        LocalDateTime weekEnd = EasyUtil.getWeekEnd(time);
        String dd = weekEnd.format(DateTimeFormatter.ofPattern("dd"));
        return time.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"_"+dd;
    }
}
