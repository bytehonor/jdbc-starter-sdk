package com.bytehonor.sdk.starter.jdbc.statement;

import com.bytehonor.sdk.lang.spring.function.ClassGetter;
import com.bytehonor.sdk.lang.spring.function.Getters;
import com.bytehonor.sdk.lang.spring.string.SpringString;
import com.bytehonor.sdk.starter.jdbc.exception.JdbcSdkException;
import com.bytehonor.sdk.starter.jdbc.sql.SqlCondition;
import com.bytehonor.sdk.starter.jdbc.sql.SqlFormatter;
import com.bytehonor.sdk.starter.jdbc.sql.SqlOrder;
import com.bytehonor.sdk.starter.jdbc.util.SqlColumnUtils;
import com.bytehonor.sdk.starter.jdbc.util.SqlInjectUtils;

/**
 * SELECT column, COUNT(PrimaryKey) as size FROM TableName WHERE condition GROUP
 * BY column;
 * 
 * @author lijianqiang
 *
 */
public class GroupCountPrepareStatement extends AbstractPrepareStatement {

    private final String column;

    public <T> GroupCountPrepareStatement(Class<T> clazz, ClassGetter<T, ?> getter, SqlCondition condition) {
        super(clazz, condition);
        this.column = SqlColumnUtils.camelToUnderline(Getters.field(getter));
    }

    @Override
    public String sql() {
        if (SpringString.isEmpty(column)) {
            throw new JdbcSdkException("GROUP BY column isEmpty");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT `").append(column).append("` AS `value`,");
        sql.append(" COUNT(").append(table.getPrimary()).append(") AS `size` FROM ").append(table.getName());

        SqlFormatter.connect(sql, condition.getWhere());
        sql.append(" GROUP BY `").append(column).append("` ");
        sql.append(orderBy((condition.getOrder())));
        return sql.toString();
    }

    private String orderBy(SqlOrder order) {
        String sql = order != null ? order.toSql() : "";
        if (SpringString.isEmpty(sql)) {
            return "ORDER BY NULL";
        }
        return sql;
    }

    @Override
    public Object[] args() {
        if (condition.nonFilter()) {
            return new Object[0];
        }
        return condition.values().toArray();
    }

    @Override
    public int[] types() {
        if (condition.nonFilter()) {
            return new int[0];
        }
        return SqlInjectUtils.listArray(condition.types());
    }

}
