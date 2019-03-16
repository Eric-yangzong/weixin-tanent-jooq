/*
 * This file is generated by jOOQ.
*/
package com.generator.tables.records;


import com.fasterxml.jackson.databind.JsonNode;
import com.generator.tables.TMyData;

import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TMyDataRecord extends UpdatableRecordImpl<TMyDataRecord> implements Record3<UUID, String, JsonNode> {

    private static final long serialVersionUID = -1931615895;

    /**
     * Setter for <code>tat0003_mod_login.t_my_data.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>tat0003_mod_login.t_my_data.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>tat0003_mod_login.t_my_data.data_type</code>.
     */
    public void setDataType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>tat0003_mod_login.t_my_data.data_type</code>.
     */
    public String getDataType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>tat0003_mod_login.t_my_data.jsonb</code>.
     */
    public void setJsonb(JsonNode value) {
        set(2, value);
    }

    /**
     * Getter for <code>tat0003_mod_login.t_my_data.jsonb</code>.
     */
    public JsonNode getJsonb() {
        return (JsonNode) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UUID, String, JsonNode> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<UUID, String, JsonNode> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return TMyData.T_MY_DATA.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TMyData.T_MY_DATA.DATA_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<JsonNode> field3() {
        return TMyData.T_MY_DATA.JSONB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getDataType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode component3() {
        return getJsonb();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDataType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode value3() {
        return getJsonb();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMyDataRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMyDataRecord value2(String value) {
        setDataType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMyDataRecord value3(JsonNode value) {
        setJsonb(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMyDataRecord values(UUID value1, String value2, JsonNode value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMyDataRecord
     */
    public TMyDataRecord() {
        super(TMyData.T_MY_DATA);
    }

    /**
     * Create a detached, initialised TMyDataRecord
     */
    public TMyDataRecord(UUID id, String dataType, JsonNode jsonb) {
        super(TMyData.T_MY_DATA);

        set(0, id);
        set(1, dataType);
        set(2, jsonb);
    }
}