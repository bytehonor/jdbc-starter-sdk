package com.bytehonor.sdk.starter.jdbc.statement;

import com.bytehonor.sdk.starter.jdbc.exception.JdbcSdkException;
import com.bytehonor.sdk.starter.jdbc.sql.SqlCondition;
import com.bytehonor.sdk.starter.jdbc.util.SqlInjectUtils;
import com.bytehonor.sdk.starter.jdbc.util.SqlStringUtils;

/**
 * SELECT columns FROM TableName WHERE condition Order Page
 * 
 * @author lijianqiang
 *
 */
public class SelectPrepareStatement extends AbstractPrepareStatement {

    public SelectPrepareStatement(Class<?> clazz, SqlCondition condition) {
        super(clazz, condition);
    }

    @Override
    public String sql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(table.getFullColumns()).append(" FROM ").append(table.getTableName());

        sql.append(SqlStringUtils.toWhereSql(condition));
        sql.append(SqlStringUtils.toOrderSql(condition.getOrder()));
        sql.append(SqlStringUtils.toLimitSql(condition.getPage()));
        return sql.toString();
    }

    @Override
    public Object[] args() {
        if (SqlCondition.isArgEmpty(condition)) {
            if (condition.getPage() == null) {
                // 禁全表无分页查询
                throw new JdbcSdkException("select sql condition args isEmpty");
            }
            return new Object[0];
        }
        return condition.values().toArray();
    }

    @Override
    public int[] types() {
        if (SqlCondition.isArgEmpty(condition)) {
            return new int[0];
        }
        return SqlInjectUtils.listArray(condition.types());
    }
}
