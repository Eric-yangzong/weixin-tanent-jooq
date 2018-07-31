/*
 * This file is generated by jOOQ.
*/
package com.generator.tables;


import com.bdhanbang.base.jooq.PostgresJsonBinding;
import com.fasterxml.jackson.databind.JsonNode;
import com.generator.Indexes;
import com.generator.Keys;
import com.generator.Userlib;
import com.generator.tables.records.TLogLoginRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TLogLogin extends TableImpl<TLogLoginRecord> {

    private static final long serialVersionUID = 430013688;

    /**
     * The reference instance of <code>userlib.t_log_login</code>
     */
    public static final TLogLogin T_LOG_LOGIN = new TLogLogin();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TLogLoginRecord> getRecordType() {
        return TLogLoginRecord.class;
    }

    /**
     * The column <code>userlib.t_log_login.id</code>.
     */
    public final TableField<TLogLoginRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>userlib.t_log_login.user_code</code>.
     */
    public final TableField<TLogLoginRecord, String> USER_CODE = createField("user_code", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>userlib.t_log_login.user_name</code>.
     */
    public final TableField<TLogLoginRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR(300).nullable(false), this, "");

    /**
     * The column <code>userlib.t_log_login.login_type</code>.
     */
    public final TableField<TLogLoginRecord, Short> LOGIN_TYPE = createField("login_type", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>userlib.t_log_login.login_time</code>.
     */
    public final TableField<TLogLoginRecord, LocalDateTime> LOGIN_TIME = createField("login_time", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>userlib.t_log_login.login_ip</code>.
     */
    public final TableField<TLogLoginRecord, String> LOGIN_IP = createField("login_ip", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>userlib.t_log_login.jsonb</code>.
     */
    public final TableField<TLogLoginRecord, JsonNode> JSONB = createField("jsonb", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "", new PostgresJsonBinding());

    /**
     * Create a <code>userlib.t_log_login</code> table reference
     */
    public TLogLogin() {
        this(DSL.name("t_log_login"), null);
    }

    /**
     * Create an aliased <code>userlib.t_log_login</code> table reference
     */
    public TLogLogin(String alias) {
        this(DSL.name(alias), T_LOG_LOGIN);
    }

    /**
     * Create an aliased <code>userlib.t_log_login</code> table reference
     */
    public TLogLogin(Name alias) {
        this(alias, T_LOG_LOGIN);
    }

    private TLogLogin(Name alias, Table<TLogLoginRecord> aliased) {
        this(alias, aliased, null);
    }

    private TLogLogin(Name alias, Table<TLogLoginRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userlib.USERLIB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LOG_LOGIN_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TLogLoginRecord> getPrimaryKey() {
        return Keys.LOG_LOGIN_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TLogLoginRecord>> getKeys() {
        return Arrays.<UniqueKey<TLogLoginRecord>>asList(Keys.LOG_LOGIN_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TLogLogin as(String alias) {
        return new TLogLogin(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TLogLogin as(Name alias) {
        return new TLogLogin(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TLogLogin rename(String name) {
        return new TLogLogin(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TLogLogin rename(Name name) {
        return new TLogLogin(name, null);
    }
}
