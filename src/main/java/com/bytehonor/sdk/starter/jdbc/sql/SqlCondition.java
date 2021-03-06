package com.bytehonor.sdk.starter.jdbc.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.bytehonor.sdk.lang.spring.constant.QueryLogic;

public class SqlCondition {

    private SqlOrder order;

    private SqlPage page;

    private final SqlArgHolder holder;

    private SqlCondition(QueryLogic logic, SqlPage page) {
        this.order = null;
        this.page = page;
        this.holder = SqlArgHolder.create(logic);
    }

    public static SqlCondition create() {
        return create(QueryLogic.AND, SqlPage.create());
    }

    public static SqlCondition create(QueryLogic logic) {
        return create(logic, SqlPage.create());
    }

    public static SqlCondition create(QueryLogic logic, SqlPage page) {
        Objects.requireNonNull(logic, "logic");

        return new SqlCondition(logic, page);
    }

    public static SqlCondition id(Long id) {
        Objects.requireNonNull(id, "id");
        SqlCondition condition = SqlCondition.create(QueryLogic.AND, SqlPage.of(0, 1));
        return condition.eq("id", id);
    }

    public SqlCondition eq(String key, String value) {
        return this.safeAdd(SqlMatcher.eq(key, value));
    }

    public SqlCondition eq(String key, Long value) {
        return this.safeAdd(SqlMatcher.eq(key, value));
    }

    public SqlCondition eq(String key, Integer value) {
        return this.safeAdd(SqlMatcher.eq(key, value));
    }

    public SqlCondition eq(String key, Boolean value) {
        return this.safeAdd(SqlMatcher.eq(key, value));
    }

    public SqlCondition neq(String key, String value) {
        return this.safeAdd(SqlMatcher.neq(key, value));
    }

    public SqlCondition neq(String key, Long value) {
        return this.safeAdd(SqlMatcher.neq(key, value));
    }

    public SqlCondition neq(String key, Integer value) {
        return this.safeAdd(SqlMatcher.neq(key, value));
    }

    public SqlCondition neq(String key, Boolean value) {
        return this.safeAdd(SqlMatcher.neq(key, value));
    }

    public SqlCondition gt(String key, Long value) {
        return this.safeAdd(SqlMatcher.gt(key, value));
    }

    public SqlCondition gt(String key, Integer value) {
        return this.safeAdd(SqlMatcher.gt(key, value));
    }

    public SqlCondition egt(String key, Long value) {
        return this.safeAdd(SqlMatcher.egt(key, value));
    }

    public SqlCondition egt(String key, Integer value) {
        return this.safeAdd(SqlMatcher.egt(key, value));
    }

    public SqlCondition lt(String key, Long value) {
        return this.safeAdd(SqlMatcher.lt(key, value));
    }

    public SqlCondition lt(String key, Integer value) {
        return this.safeAdd(SqlMatcher.lt(key, value));
    }

    public SqlCondition elt(String key, Long value) {
        return this.safeAdd(SqlMatcher.elt(key, value));
    }

    public SqlCondition elt(String key, Integer value) {
        return this.safeAdd(SqlMatcher.elt(key, value));
    }

    public SqlCondition like(String key, String value) {
        return this.safeAdd(SqlMatcher.like(key, value));
    }

    public SqlCondition likeLeft(String key, String value) {
        return this.safeAdd(SqlMatcher.likeLeft(key, value));
    }

    public SqlCondition likeRight(String key, String value) {
        return this.safeAdd(SqlMatcher.likeRight(key, value));
    }

    public SqlCondition strings(String key, Collection<String> value) {
        return this.safeAdd(SqlMatcher.strings(key, value));
    }

    public SqlCondition longs(String key, Collection<Long> value) {
        return this.safeAdd(SqlMatcher.longs(key, value));
    }

    public SqlCondition integers(String key, Collection<Integer> value) {
        return this.safeAdd(SqlMatcher.integers(key, value));
    }

    public SqlCondition descBy(String key) {
        this.order = SqlOrder.descOf(key);
        return this;
    }

    public SqlCondition ascBy(String key) {
        this.order = SqlOrder.ascOf(key);
        return this;
    }

    public SqlCondition safeAdd(SqlMatcher column) {
        // this.columns.add(column);
        this.holder.safeAdd(column);
        return this;
    }

    public static boolean isArgEmpty(SqlCondition condition) {
        Objects.requireNonNull(condition, "condition");

        return condition.getHolder().isEmpty();
    }

    public String toSql() {
        if (holder.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ").append(holder.toSql());
        return sb.toString();
    }

    public List<Object> values() {
        if (holder == null) {
            return new ArrayList<Object>();
        }
        return holder.getValues();
    }

    public List<Integer> types() {
        if (holder == null) {
            return new ArrayList<Integer>();
        }
        return holder.getSqlTypes();
    }

    @Override
    public String toString() {
        return toSql();
    }

    public SqlOrder getOrder() {
        return order;
    }

    public void setOrder(SqlOrder order) {
        this.order = order;
    }

    public SqlPage getPage() {
        return page;
    }

    public void setPage(SqlPage page) {
        this.page = page;
    }

//    public List<SqlColumn> getColumns() {
//        return columns;
//    }

    public SqlArgHolder getHolder() {
        return holder;
    }

}
