/*
 * This file is generated by jOOQ.
*/
package com.generator.tables;


import com.bdhanbang.base.jooq.PostgresJsonBinding;
import com.fasterxml.jackson.databind.JsonNode;
import com.generator.Indexes;
import com.generator.Keys;
import com.generator.Tat0003ModLogin;
import com.generator.tables.records.AuthCredentialsRecord;

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
public class AuthCredentials extends TableImpl<AuthCredentialsRecord> {

    private static final long serialVersionUID = -1529928663;

    /**
     * The reference instance of <code>tat0003_mod_login.auth_credentials</code>
     */
    public static final AuthCredentials AUTH_CREDENTIALS = new AuthCredentials();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthCredentialsRecord> getRecordType() {
        return AuthCredentialsRecord.class;
    }

    /**
     * The column <code>tat0003_mod_login.auth_credentials._id</code>.
     */
    public final TableField<AuthCredentialsRecord, UUID> _ID = createField("_id", org.jooq.impl.SQLDataType.UUID.nullable(false).defaultValue(org.jooq.impl.DSL.field("gen_random_uuid()", org.jooq.impl.SQLDataType.UUID)), this, "");

    /**
     * The column <code>tat0003_mod_login.auth_credentials.jsonb</code>.
     */
    public final TableField<AuthCredentialsRecord, JsonNode> JSONB = createField("jsonb", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "", new PostgresJsonBinding());

    /**
     * Create a <code>tat0003_mod_login.auth_credentials</code> table reference
     */
    public AuthCredentials() {
        this(DSL.name("auth_credentials"), null);
    }

    /**
     * Create an aliased <code>tat0003_mod_login.auth_credentials</code> table reference
     */
    public AuthCredentials(String alias) {
        this(DSL.name(alias), AUTH_CREDENTIALS);
    }

    /**
     * Create an aliased <code>tat0003_mod_login.auth_credentials</code> table reference
     */
    public AuthCredentials(Name alias) {
        this(alias, AUTH_CREDENTIALS);
    }

    private AuthCredentials(Name alias, Table<AuthCredentialsRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuthCredentials(Name alias, Table<AuthCredentialsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Tat0003ModLogin.TAT0003_MOD_LOGIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.AUTH_CREDENTIALS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AuthCredentialsRecord> getPrimaryKey() {
        return Keys.AUTH_CREDENTIALS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthCredentialsRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthCredentialsRecord>>asList(Keys.AUTH_CREDENTIALS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthCredentials as(String alias) {
        return new AuthCredentials(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthCredentials as(Name alias) {
        return new AuthCredentials(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthCredentials rename(String name) {
        return new AuthCredentials(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthCredentials rename(Name name) {
        return new AuthCredentials(name, null);
    }
}
