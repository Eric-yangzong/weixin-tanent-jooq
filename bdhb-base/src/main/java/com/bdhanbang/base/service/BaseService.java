package com.bdhanbang.base.service;

import java.io.Serializable;
import java.util.List;

import org.jooq.impl.TableImpl;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;

/**
 * @ClassName: BaseService
 * @Description: 定义基础的增删除改查的接口
 * @author yangxz
 * @date 2018年7月12日 上午10:44:33
 * 
 */
public interface BaseService<T extends TableImpl<?>, E extends Serializable> {

	int deleteEntity(String schema, Class<T> clazz, Object id);

	int deleteEntitys(String schema, Class<T> clazz, List<Query> queryList);

	int insertEntity(String schema, Class<T> clazz, E entity);

	int insertEntityBatch(String schema, Class<T> clazz, List<E> entitys);

	int updateEntity(String schema, Class<T> clazz, E entity);

	E getEntity(String schema, Class<T> clazz, Class<E> entityClass, Object id);

	List<E> queryList(String schema, Class<T> clazz, Class<E> entityClass, List<Query> queryList);

	QueryResults<E> queryPage(String schema, Class<T> clazz, Class<E> entityClass, QueryPage queryPage);

}
