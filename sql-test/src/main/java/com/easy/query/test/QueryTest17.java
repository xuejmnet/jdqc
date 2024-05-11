package com.easy.query.test;

import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.nop.MyObject;
import com.easy.query.test.nop.OtherTable;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/5/6 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest17 extends BaseTest{
    @Test
    public void test1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().nullOrDefault("123").eq("123");
                    b.id().subString(1, 20).eq("456");
                    b.expression().concat(x->x.expression(b.content()).value("123").expression(b.id())).eq("789");
                    b.content().toUpper().eq("abc");
                    b.content().toLower().eq("def");
                    b.content().trim().eq("a");
                    b.content().trimStart().eq("b");
                    b.content().trimEnd().eq("c");
                    b.content().replace("123", "456").eq("aaa");
                    b.content().leftPad(2, 'a').eq("aa");
                    b.content().rightPad(2, 'a').eq("aa");
                    b.content().length().eq(1);
                    b.content().compareTo("aaaa").ge(0);
                }).toList();

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void test2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyQuery.queryable(BlogEntity.class)
                .where(b -> {
                    LambdaSQLFunc<BlogEntity> fx = b.fx();
                    SQLFunction nullOrDefault = fx.nullOrDefault(BlogEntity::getId, "123");
                    b.eq(nullOrDefault,"123");

                    SQLFunction subString = fx.subString(BlogEntity::getId, 1, 20);
                    b.eq(subString,"456");

                    SQLFunction concat = fx.concat(x -> x.column(BlogEntity::getContent).value("123").column(BlogEntity::getId));
                    b.eq(concat,"789");

                    SQLFunction upper = fx.toUpper(BlogEntity::getContent);
                    b.eq(upper,"abc");
                    SQLFunction lower = fx.toLower(BlogEntity::getContent);
                    b.eq(lower,"def");

                    SQLFunction trim = fx.trim(BlogEntity::getContent);
                    b.eq(trim,"a");

                    SQLFunction trimStart = fx.trimStart(BlogEntity::getContent);
                    b.eq(trimStart,"b");
                    SQLFunction trimEnd = fx.trimEnd(BlogEntity::getContent);
                    b.eq(trimEnd,"c");

                    SQLFunction replace = fx.replace(BlogEntity::getContent, "123", "456");
                    b.eq(replace,"aaa");

                    SQLFunction leftPad = fx.leftPad(BlogEntity::getContent, 2, 'a');
                    b.eq(leftPad,"aa");

                    SQLFunction rightPad = fx.rightPad(BlogEntity::getContent, 2, 'a');
                    b.eq(rightPad,"aa");

                    SQLFunction length = fx.length(BlogEntity::getContent);
                    b.eq(length,1);

                    SQLFunction stringCompareTo = fx.stringCompareTo(BlogEntity::getContent, "aaaa");
                    b.ge(stringCompareTo,0);
                }).toList();

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void test3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyQueryClient.queryable(BlogEntity.class)
                .where(b -> {
                    SQLFunc fx = b.fx();
                    SQLFunction nullOrDefault = fx.nullOrDefault("id", "123");
                    b.eq(nullOrDefault, "123");

                    SQLFunction subString = fx.subString("id", 1, 20);
                    b.eq(subString, "456");

                    SQLFunction concat = fx.concat(x -> x.column("content").value("123").column("id"));
                    b.eq(concat, "789");

                    SQLFunction upper = fx.toUpper("content");
                    b.eq(upper, "abc");
                    SQLFunction lower = fx.toLower("content");
                    b.eq(lower, "def");

                    SQLFunction trim = fx.trim("content");
                    b.eq(trim, "a");

                    SQLFunction trimStart = fx.trimStart("content");
                    b.eq(trimStart, "b");
                    SQLFunction trimEnd = fx.trimEnd("content");
                    b.eq(trimEnd, "c");

                    SQLFunction replace = fx.replace("content", "123", "456");
                    b.eq(replace, "aaa");

                    SQLFunction leftPad = fx.leftPad("content", 2, 'a');
                    b.eq(leftPad, "aa");

                    SQLFunction rightPad = fx.rightPad("content", 2, 'a');
                    b.eq(rightPad, "aa");

                    SQLFunction length = fx.length("content");
                    b.eq(length, 1);

                    SQLFunction stringCompareTo = fx.stringCompareTo("content", "aaaa");
                    b.ge(stringCompareTo, 0);
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
//
//    @Test
//    public  void testxxxx(){
////        String sql = easyQueryClient.queryable(Topic.class).from(BlogEntity.class)
////                .where(t -> {
////                    t.eq("id", 123);
////                    t.ne("title", (Object) null);
////                }).toSQL();
////        System.out.println(sql);
//
////        String sql1 = easyQueryClient.queryable(Topic.class)
////                .select(Topic.class, t -> t.column("id").column("title"))
////                .where(t -> t.eq("title", "123"))
////                .toSQL();
////
////        System.out.println(sql1);
//
//        ClientQueryable<Topic> select = easyQueryClient.queryable(Topic.class)
//                .select(Topic.class, t -> t.column("id").column("title"));
//
//        String sql = select.from(BlogEntity.class)
//                .toSQL();
//
//        System.out.println(sql);
//        List<Map<String, Object>> list = easyQueryClient.mapQueryable("select * from t_topic")
//                .toList();
//        List<Map<String, Object>> list1 = easyQueryClient.mapQueryable()
//                .asTable("table1")
//                .join(MultiTableTypeEnum.LEFT_JOIN, easyQueryClient.mapQueryable("select * from t_topic"), on -> {
//                    WherePredicate<?> wherePredicate0 = on.getWherePredicate(0);
//                    WherePredicate<?> wherePredicate1 = on.getWherePredicate(1);
//
//                    wherePredicate0.eq(wherePredicate1, "id", "id");
//                })
//                .toList();
//    }

//
    @Test
     public void testNopQuery(){
////         select v.f1,sum(v.f2), count(u.f3)
////         from (select o.f1, o.f2 from MyObject o left join o.parent
////                 where o.parent.children.myChild.name > 3 ) v,
////                 OtherTable u
////         where v.f1 = u.type group by u.type
//
//        //myObject oneToOne parent
//        //parent oneToMany children
//        //children onToMany myChild
//
//        //查询object的父级下的children中存在myChild里面name是大于3的
//
//
//
//        List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement().name().gt("3");
//                }).toList();
//
//        List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().where(o->{
//                        o.name().gt("3");
//                    }).any();
//                }).toList();
//
//
//
//
//        List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children()
//                            .where(children -> {
//                                children.myChildren().where(myChild -> {
//                                    myChild.name().gt("3");
//                                }).any();
//                            }).any();
//                }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
//                .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
//                .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.groupTable().t1.value2().sum(),
//                        group.groupTable().t2.type().count()
//                )).toList();
    }


    @Test
    public void testFlatElement1(){
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().where(o->{
                                o.name().gt("3");
                            }).any();
                        }).toList();
            }catch (Exception ignored){

            }

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().flatElement().name().gt("3");
                        }).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testFlatElement2(){
//
//
//
//        List<MyObject> list2 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children()
//                            .where(children -> {
//                                children.myChildren().where(myChild -> {
//                                    myChild.name().gt("3");
//                                }).any();
//                            }).any();
//                }).toList();
//
//
//        List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement().myChildren().flatElement().name().gt("3");
//                }).toList();


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children()
                                    .where(children -> {
                                        children.myChildren().where(myChild -> {
                                            myChild.name().gt("3");
                                        }).any();
                                    }).any();
                        }).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().flatElement().myChildren().flatElement().name().gt("3");
                        }).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().where(children->{
                                children.myChildren().flatElement().name().gt("3");
                            }).any();
                        }).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
     public void testNop2(){
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {
                List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children()
                                    .where(children -> {
                                        children.myChildren().where(myChild -> {
                                            myChild.name().gt("3");
                                        }).any();
                                    }).any();
                        }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
                        .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
                        .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
                        .select(group -> Select.DRAFT.of(
                                group.key1(),
                                group.groupTable().t1.value2().sum(),
                                group.groupTable().t2.type().count()
                        )).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`value1` AS `value1`,SUM(t4.`value2`) AS `value2`,COUNT(t5.`type`) AS `value3` FROM (SELECT t.`f1` AS `value1`,t.`f1` AS `value2` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)) t4 INNER JOIN `OtherTable` t5 ON t4.`value1` = t5.`type` GROUP BY t4.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {
                List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {

                            m.parent().children().flatElement().myChildren().flatElement().name().gt("3");

                        }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
                        .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
                        .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
                        .select(group -> Select.DRAFT.of(
                                group.key1(),
                                group.groupTable().t1.value2().sum(),
                                group.groupTable().t2.type().count()
                        )).toList();
            }catch (Exception ignored){

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`value1` AS `value1`,SUM(t4.`value2`) AS `value2`,COUNT(t5.`type`) AS `value3` FROM (SELECT t.`f1` AS `value1`,t.`f1` AS `value2` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)) t4 INNER JOIN `OtherTable` t5 ON t4.`value1` = t5.`type` GROUP BY t4.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
     }

}
