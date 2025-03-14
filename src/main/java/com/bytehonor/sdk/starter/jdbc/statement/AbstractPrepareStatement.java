package com.bytehonor.sdk.starter.jdbc.statement;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.starter.jdbc.exception.JdbcSdkException;
import com.bytehonor.sdk.starter.jdbc.meta.MetaTable;
import com.bytehonor.sdk.starter.jdbc.meta.MetaTableParser;
import com.bytehonor.sdk.starter.jdbc.model.ModelGetterMapper;
import com.bytehonor.sdk.starter.jdbc.model.ModelKeyValue;
import com.bytehonor.sdk.starter.jdbc.sql.SqlCondition;
import com.bytehonor.sdk.starter.jdbc.util.SqlAdaptUtils;
import com.bytehonor.sdk.starter.jdbc.util.SqlInjectUtils;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractPrepareStatement implements PrepareStatement {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPrepareStatement.class);

    protected final Class<?> clazz;

    protected final MetaTable table;

    protected final SqlCondition condition;

    public AbstractPrepareStatement(Class<?> clazz, SqlCondition condition) {
        this.clazz = clazz;
        this.table = MetaTableParser.parse(clazz);
        this.condition = condition;
    }

    @Override
    public <T> List<ModelKeyValue> prepare(T model, ModelGetterMapper<T> mapper) {
        return new ArrayList<ModelKeyValue>();
    }

    @Override
    public void check() {
        int argLength = args().length;
        int typeLength = types().length;
        LOG.debug("argSize:{}, typeSize:{}", argLength, typeLength);

        if (argLength != typeLength) {
            LOG.error("args:{} not equals types:{}", SqlInjectUtils.toString(args()), types());
            throw new JdbcSdkException("args not equals types");
        }

        if (LOG.isDebugEnabled()) {
            Object[] args = args();
            int[] types = types();
            for (int i = 0; i < argLength; i++) {
                LOG.debug("arg:({}), type:{}, {}", args[i], types[i], SqlAdaptUtils.toJavaType(types[i]));
            }
        }

    }

    public Class<?> getClazz() {
        return clazz;
    }

    public MetaTable getTable() {
        return table;
    }

    public SqlCondition getCondition() {
        return condition;
    }

}
