/*
 * This file is generated by jOOQ.
*/
package com.generator.tables.daos;


import com.fasterxml.jackson.databind.JsonNode;
import com.generator.tables.TLogOperation;
import com.generator.tables.records.TLogOperationRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class TLogOperationDao extends DAOImpl<TLogOperationRecord, com.generator.tables.pojos.TLogOperation, UUID> {

    /**
     * Create a new TLogOperationDao without any configuration
     */
    public TLogOperationDao() {
        super(TLogOperation.T_LOG_OPERATION, com.generator.tables.pojos.TLogOperation.class);
    }

    /**
     * Create a new TLogOperationDao with an attached configuration
     */
    public TLogOperationDao(Configuration configuration) {
        super(TLogOperation.T_LOG_OPERATION, com.generator.tables.pojos.TLogOperation.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(com.generator.tables.pojos.TLogOperation object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchById(UUID... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.generator.tables.pojos.TLogOperation fetchOneById(UUID value) {
        return fetchOne(TLogOperation.T_LOG_OPERATION.ID, value);
    }

    /**
     * Fetch records that have <code>app_code IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByAppCode(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.APP_CODE, values);
    }

    /**
     * Fetch records that have <code>app_name IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByAppName(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.APP_NAME, values);
    }

    /**
     * Fetch records that have <code>module_id IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByModuleId(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.MODULE_ID, values);
    }

    /**
     * Fetch records that have <code>user_code IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByUserCode(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.USER_CODE, values);
    }

    /**
     * Fetch records that have <code>user_name IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByUserName(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.USER_NAME, values);
    }

    /**
     * Fetch records that have <code>opt_time IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByOptTime(LocalDateTime... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.OPT_TIME, values);
    }

    /**
     * Fetch records that have <code>quote_addr IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByQuoteAddr(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.QUOTE_ADDR, values);
    }

    /**
     * Fetch records that have <code>opt_mark IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByOptMark(String... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.OPT_MARK, values);
    }

    /**
     * Fetch records that have <code>jsonb IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLogOperation> fetchByJsonb(JsonNode... values) {
        return fetch(TLogOperation.T_LOG_OPERATION.JSONB, values);
    }
}
