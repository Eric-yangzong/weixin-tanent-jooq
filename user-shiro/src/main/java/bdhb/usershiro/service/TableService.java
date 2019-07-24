package bdhb.usershiro.service;

import java.io.Serializable;
import java.util.List;

import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.TableImpl;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;

/**
 * @ClassName: TableService
 * @Description: 定义基础的增删除改查的接口
 * @author yangxz
 * @date 2019年5月14日 下午2:26:14
 * 
 */
public interface TableService {

	/**
	 * @Title: getFunctionValue
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param  routine
	 * @param @param  clazz
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws:
	 */
	<T> T getFunctionValue(AbstractRoutine<T> routine, Class<T> clazz);

	/**
	 * @Title: deleteEntity
	 * @Description: 根据id删除数据
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  id
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>> int deleteEntity(String schema, Class<T> clazz, Object id);

	/**
	 * @Title: deleteEntitysByQuery
	 * @Description: 根据查询结果删除
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  query
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>> int deleteEntitysByQuery(String schema, Class<T> clazz, Query query);

	/**
	 * @Title: insertEntity
	 * @Description: 写入数据库实体
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  entity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>, E extends Serializable> int insertEntity(String schema, Class<T> clazz, E entity);

	/**
	 * @Title: insertEntityBatch
	 * @Description: 批量插入实体信息
	 * @param schema  模式
	 * @param clazz   Q开头实体类.class
	 * @param entitys 要插入的生成的实体类集合
	 * @return int 影响行数
	 * @throws @author guomh
	 * @date 2018年9月13日 上午9:59:58
	 */
	<T extends TableImpl<?>, E extends Serializable> int insertEntityBatch(String schema, Class<T> clazz,
			List<E> entitys);

	/**
	 * @Title: updateEntity
	 * @Description: 跟新
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  entity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>, E extends Serializable> int updateEntity(String schema, Class<T> clazz, E entity);

	<T extends TableImpl<?>, E extends Serializable> int updateColumn(String schema, Class<T> clazz, String column,
			Object value, Query query);

	/**
	 * @Title: getEntity
	 * @Description: 得到单个实体
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  entityClass
	 * @param @param  id
	 * @param @return 设定文件
	 * @return E 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>, E extends Serializable> E getEntity(String schema, Class<T> clazz, Class<E> entityClass,
			Object id);

	/**
	 * @Title: queryList
	 * @Description: 查询不分页
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  entityClass
	 * @param @param  query
	 * @param @return 设定文件
	 * @return List<E> 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>, E extends Serializable> List<E> queryList(String schema, Class<T> clazz,
			Class<E> entityClass, Query query);

	/**
	 * @Title: queryPage
	 * @Description: 分页查询
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @param  entityClass
	 * @param @param  queryPage
	 * @param @return 设定文件
	 * @return QueryResults<E> 返回类型
	 * @throws:
	 */
	<T extends TableImpl<?>, E extends Serializable> QueryResults<E> queryPage(String schema, Class<T> clazz,
			Class<E> entityClass, QueryPage queryPage);

}
