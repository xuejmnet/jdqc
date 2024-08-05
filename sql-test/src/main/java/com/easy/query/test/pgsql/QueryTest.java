package com.easy.query.test.pgsql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.partition.Partition1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/10 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest extends PgSQLBaseTest {

    @Test
    public void query1() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);

    }

    @Test
    public void query2() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97", blogEntity.getId());
    }

    @Test
    public void query3() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select(" 1 ");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT  1  FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());
    }

    @Test
    public void query4() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select("1 as id");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT 1 as id FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1", blogEntity.getId());
    }

    @Test
    public void query5() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("97", "98")));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" IN (?,?)", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
        Assert.assertEquals(true, blogs.get(1).getIsTop());
        Assert.assertEquals(true, blogs.get(1).getTop());
    }

    @Test
    public void query6() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .whereById("97");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1, blogs.size());
        Assert.assertEquals("97", blogs.get(0).getId());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
    }

    @Test
    public void query7() {
        List<BlogEntity> blogEntities = easyQuery.queryable(BlogEntity.class).orderByAsc(o -> o.column(BlogEntity::getCreateTime)).toList();
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr, blog.getId());
            Assert.assertEquals(indexStr, blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
            Assert.assertEquals(indexStr, blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
            Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
            Assert.assertEquals(i, (int) blog.getStar());
            Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
            Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
            Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0, blog.getIsTop());
            Assert.assertEquals(i % 2 == 0, blog.getTop());
            Assert.assertEquals(false, blog.getDeleted());
            i++;
        }
    }


    @Test
    public void query8() {
        Queryable<String> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"))
                .select(String.class,o->o.sqlFunc(o.fx().concat(BlogEntity::getId,BlogEntity::getId)));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT CONCAT(t.\"id\",t.\"id\") FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
        String blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("9797", blogEntity);
    }
    @Test
    public void query9() {
        Queryable<String> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"))
                .select(String.class,o->o.sqlFunc(o.fx().concat(BlogEntity::getId,BlogEntity::getTitle)));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT CONCAT(t.\"id\",t.\"title\") FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
        String blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97title97", blogEntity);
    }
//    @Test
//    public void query10() {
//        Queryable<String> queryable = easyQuery.queryable(BlogEntity.class)
//                .where(o -> o.eq(BlogEntity::getId, "97"))
//                .select(String.class,o->o.func(o.sqlFunc().join(",",BlogEntity::getId,BlogEntity::getTitle)));
//        String sql = queryable.toSQL();
//        Assert.assertEquals("SELECT t.\"id\" || ? || t.\"title\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
//        String blogEntity = queryable.firstOrNull();
//        Assert.assertNotNull(blogEntity);
//        Assert.assertEquals("97,title97", blogEntity);
//    }
@Test
public void query10() {
    Queryable<String> queryable = easyQuery.queryable(BlogEntity.class)
            .where(o -> o.eq(BlogEntity::getId, "97"))
            .select(String.class,o->o.sqlFunc(o.fx().valueOrDefault(BlogEntity::getId,"1")));
    String sql = queryable.toSQL();
    Assert.assertEquals("SELECT COALESCE(t.\"id\",?) FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
}



    @Test
    public void testDraft9() {
        String id = "123456zz9";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        entityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnit.DAYS),
                        o.createTime().plus(2, TimeUnit.SECONDS),
                        o.createTime().plus(3, TimeUnit.MINUTES)
                )).firstOrNull();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnit.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2,TimeUnit.SECONDS),DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(3,TimeUnit.MINUTES),DateTimeDurationEnum.Minutes)
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days)),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnit.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2,TimeUnit.SECONDS),DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnit.DAYS).plus(3,TimeUnit.MINUTES),DateTimeDurationEnum.Minutes)
                )).firstOrNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }



    @Test
    public void testDraft9_1() {
        String id = "123456zz91";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        entityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnitEnum.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2,TimeUnitEnum.SECONDS),DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(3,TimeUnitEnum.MINUTES),DateTimeDurationEnum.Minutes)
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days)),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnitEnum.DAYS), DateTimeDurationEnum.Days),
                        o.createTime().duration(o.createTime().plus(2,TimeUnitEnum.SECONDS),DateTimeDurationEnum.Seconds),
                        o.createTime().duration(o.createTime().plus(1,TimeUnitEnum.DAYS).plus(3,TimeUnitEnum.MINUTES),DateTimeDurationEnum.Minutes)
                )).firstOrNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }
    @Test
    public void test123(){

        List<BlogEntity> yyyy年MM月dd日 = entityQuery.queryable(BlogEntity.class)
                .where(m -> {
                    m.or(()->{
                        m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                        m.id().isNotNull();
                    });
                }).toList();
        String sql = entityQuery.queryable(BlogEntity.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                }).toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND TO_CHAR(((\"create_time\")::TIMESTAMP)::timestamp,'YYYY年MM月DD日') = ?",sql);
    }
    @Test
    public void testPartitionBy1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Partition1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PARTITION.of(
                        b,
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.partitionTable().star().gt(1);
                    partition.partitionColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__partition__column1\" AS \"__partition__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__partition__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__partition__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Partition1<BlogEntity, Long> blogEntityLongPartition1 : list) {

            BlogEntity entity = blogEntityLongPartition1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPartition1.getPartitionColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L, (long) partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PARTITION.of(
                        b,
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.partitionTable().star().gt(1);
                    partition.partitionColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partitionColumn1(),
                        p.partitionTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__partition__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__partition__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__partition__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }
}
