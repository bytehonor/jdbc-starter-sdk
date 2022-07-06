package com.bytehonor.sdk.starter.jdbc.query;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.define.spring.query.QueryCondition;
import com.bytehonor.sdk.starter.jdbc.Student;
import com.bytehonor.sdk.starter.jdbc.condition.SqlAdapter;
import com.bytehonor.sdk.starter.jdbc.sql.PrepareStatement;
import com.bytehonor.sdk.starter.jdbc.sql.PrepareStatementBuilder;

public class PrepareStatementBuilderQueryTest {
    private static final Logger LOG = LoggerFactory.getLogger(PrepareStatementBuilderQueryTest.class);

    @Test
    public void test() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        QueryCondition condition = QueryCondition.and();
        condition.integers("age", set);
        condition.gt("create_at", System.currentTimeMillis());
        condition.like("nickname", "boy");
        condition.descBy("age");

        PrepareStatement select = PrepareStatementBuilder.select(Student.class, SqlAdapter.convert(condition));
        LOG.info("select sql:{}", select.sql());
        select.check();

        PrepareStatement selectById = PrepareStatementBuilder.selectById(Student.class, 1L);
        LOG.info("selectById sql:{}", selectById.sql());
        selectById.check();

        PrepareStatement count = PrepareStatementBuilder.count(Student.class, SqlAdapter.convert(condition));
        LOG.info("count sql:{}", count.sql());
        count.check();

        PrepareStatement delete = PrepareStatementBuilder.delete(Student.class, SqlAdapter.convert(condition));
        LOG.info("delete sql:{}", delete.sql());
        delete.check();

        PrepareStatement deleteById = PrepareStatementBuilder.deleteById(Student.class, 1L);
        LOG.info("deleteById sql:{}", deleteById.sql());
        deleteById.check();
    }
}