/*
 * This file is generated by jOOQ.
*/
package com.generator.tables.daos;


import com.generator.tables.TLoginType;
import com.generator.tables.records.TLoginTypeRecord;

import java.util.List;

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
public class TLoginTypeDao extends DAOImpl<TLoginTypeRecord, com.generator.tables.pojos.TLoginType, Short> {

    /**
     * Create a new TLoginTypeDao without any configuration
     */
    public TLoginTypeDao() {
        super(TLoginType.T_LOGIN_TYPE, com.generator.tables.pojos.TLoginType.class);
    }

    /**
     * Create a new TLoginTypeDao with an attached configuration
     */
    public TLoginTypeDao(Configuration configuration) {
        super(TLoginType.T_LOGIN_TYPE, com.generator.tables.pojos.TLoginType.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Short getId(com.generator.tables.pojos.TLoginType object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLoginType> fetchById(Short... values) {
        return fetch(TLoginType.T_LOGIN_TYPE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.generator.tables.pojos.TLoginType fetchOneById(Short value) {
        return fetchOne(TLoginType.T_LOGIN_TYPE.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.generator.tables.pojos.TLoginType> fetchByName(String... values) {
        return fetch(TLoginType.T_LOGIN_TYPE.NAME, values);
    }
}
