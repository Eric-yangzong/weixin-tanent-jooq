package bdhb.usershiro.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DeleteWhereStep;
import org.jooq.Field;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.exception.CurdException;
import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.util.JOOQHelper;
import bdhb.usershiro.service.TableService;
import com.bdhanbang.base.jooq.ISchemaSwitch;
import com.bdhanbang.base.message.ErrorMessage;

@Service
public class TableServiceImpl implements TableService {

	@Autowired
	protected DSLContext dsl;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends TableImpl<?>> int deleteEntity(String schema, Class<T> clazz, Object id) {
		T tableImpl = createTableImpl(schema, clazz);

		DeleteWhereStep<?> deleteStep = dsl.delete((Table) tableImpl);

		TableField<?, Object> tableIdField = getTableIdField(tableImpl);

		deleteStep.where(tableIdField.eq(id));

		return deleteStep.execute();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends TableImpl<?>> int deleteEntitysByQuery(String schema, Class<T> clazz, Query query) {

		List<Query> queryList = query.getQuerys();

		T tableImpl = createTableImpl(schema, clazz);

		DeleteWhereStep<?> deleteStep = dsl.delete((Table) tableImpl);

		Condition delWhere = JOOQHelper.analyzeQuery(tableImpl, queryList);

		deleteStep.where(delWhere);

		return deleteStep.execute();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends TableImpl<?>, E extends Serializable> int insertEntity(String schema, Class<T> clazz, E entity) {
		T tableImpl = createTableImpl(schema, clazz);

		Field<?>[] fields = tableImpl.fields();
		List<Object> values = getValueList(fields, entity);

		InsertValuesStepN<?> insertStep = dsl.insertInto((Table) tableImpl).columns(fields);

		insertStep.values(values);// 进行赋值工作

		return insertStep.execute();// 执行写入工作
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends TableImpl<?>, E extends Serializable> int insertEntityBatch(String schema, Class<T> clazz,
			List<E> entitys) {
		T tableImpl = createTableImpl(schema, clazz);

		Field<?>[] fields = tableImpl.fields();
		InsertValuesStepN<?> insertStep = dsl.insertInto((Table) tableImpl).columns(fields);
		for (E entity : entitys) {
			List<Object> values = getValueList(fields, entity);
			insertStep.values(values);// 进行赋值工作
		}
		return insertStep.execute();// 执行写入工作
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends TableImpl<?>, E extends Serializable> int updateEntity(String schema, Class<T> clazz, E entity) {
		T tableImpl = createTableImpl(schema, clazz);

		TableField<?, Object> tableIdField = getTableIdField(tableImpl);
		Object idValue = getFieldIdValue(getTableIdField(tableImpl).getName(), entity);

		UpdateSetFirstStep updateFirstStep = dsl.update((Table) tableImpl);
		UpdateSetMoreStep<?> updateStep = null;

		Field<?>[] fields = tableImpl.fields();
		List<Object> values = getValueList(fields, entity);

		for (int i = 0; i < fields.length; i++) {
			TableField<?, Object> tableField = (TableField<?, Object>) fields[i];
			if (Objects.isNull(updateStep)) {
				updateStep = updateFirstStep.set(tableField, values.get(i));
			} else {
				updateStep.set(tableField, values.get(i));
			}
		}

		updateStep.where(tableIdField.eq(idValue));

		return updateStep.execute();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends TableImpl<?>, E extends Serializable> int updateColumn(String schema, Class<T> clazz,
			String column, Object value, Query query) {
		T tableImpl = createTableImpl(schema, clazz);

		Field<Object> field = (Field<Object>) tableImpl.field(column);

		Condition condition = JOOQHelper.analyzeQuery(tableImpl, query.getQuerys());

		return dsl.update((Table) tableImpl).set(field, value).where(condition).execute();
	}

	@Override
	public <T extends TableImpl<?>, E extends Serializable> E getEntity(String schema, Class<T> clazz,
			Class<E> entityClass, Object id) {
		T tableImpl = createTableImpl(schema, clazz);

		Field<?>[] fields = tableImpl.fields();

		SelectJoinStep<Record> selectStep = dsl.select(fields).from(tableImpl);

		TableField<?, Object> tableIdField = getTableIdField(tableImpl);

		selectStep.where(tableIdField.eq(id));

		int fetchCount = dsl.fetchCount(selectStep);

		if (fetchCount == 0) {
			return null;
		}

		return selectStep.fetchOne().into(entityClass);

	}

	@Override
	public <T extends TableImpl<?>, E extends Serializable> List<E> queryList(String schema, Class<T> clazz,
			Class<E> entityClass, Query query) {
		T tableImpl = createTableImpl(schema, clazz);

		Field<?>[] fields = tableImpl.fields();

		SelectJoinStep<Record> selectStep = dsl.select(fields).from(tableImpl);

		// 生成查询条件
		Condition condition = JOOQHelper.analyzeQuery(tableImpl, query.getQuerys());

		// 生成where
		if (!Objects.isNull(condition)) {
			selectStep.where(condition);
		}

		return selectStep.fetch().into(entityClass);
	}

	@Override
	public <T extends TableImpl<?>, E extends Serializable> QueryResults<E> queryPage(String schema, Class<T> clazz,
			Class<E> entityClass, QueryPage queryPage) {

		T tableImpl = createTableImpl(schema, clazz);

		Field<?>[] fields = tableImpl.fields();

		SelectJoinStep<Record> selectStep = dsl.select(fields).from(tableImpl);

		JOOQHelper.analyzeQuery(tableImpl, selectStep, queryPage);

		int rowCount = dsl.fetchCount(selectStep); // 得到总行数

		JOOQHelper.analyzeOrder(tableImpl, selectStep, queryPage);

		List<E> list = selectStep.fetch().into(entityClass);

		return new QueryResults<E>(list, Long.valueOf(queryPage.getSize()),
				Long.valueOf((queryPage.getPage() - 1) * queryPage.getSize()), Long.valueOf(rowCount));
	}

	/**
	 * @Title: createTableImpl
	 * @Description: 生成jooq的tableImpl
	 * @param @param  schema
	 * @param @param  clazz
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws:
	 */
	protected <T extends TableImpl<?>> T createTableImpl(String schema, Class<T> clazz) {
		try {
			T tableImpl = clazz.newInstance();

			GenSchema s = new GenSchema(schema);
			((ISchemaSwitch) tableImpl).setSchema(s);

			return tableImpl;
		} catch (Exception e) {
			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	protected <T extends TableImpl<?>> TableField<?, Object> getTableIdField(T tableImpl) {
		return (TableField<?, Object>) tableImpl.getPrimaryKey().getFields().get(0);
	}

	protected <E extends Serializable> List<Object> getValueList(Field<?>[] fields, E entity) {
		try {

			List<Object> values = new ArrayList<>();

			for (Field<?> field : fields) {

				java.lang.reflect.Field fieldVal = entity.getClass()
						.getDeclaredField(JOOQHelper.UnderlineToHump(field.getName()));
				fieldVal.setAccessible(true);

				values.add(fieldVal.get(entity));

			}
			return values;
		} catch (Exception e) {
			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());
		}
	}

	protected <T extends TableImpl<?>, E extends Serializable> Object getFieldIdValue(String idName, E entity) {

		try {

			java.lang.reflect.Field fieldVal = entity.getClass().getDeclaredField(JOOQHelper.UnderlineToHump(idName));
			fieldVal.setAccessible(true);

			return fieldVal.get(entity);

		} catch (NoSuchFieldException e) {

			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());

		} catch (SecurityException e) {

			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());

		} catch (IllegalArgumentException e) {

			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());

		} catch (IllegalAccessException e) {

			throw new CurdException(e, ErrorMessage.CURD_ERROR.getStatus(), ErrorMessage.CURD_ERROR.getMessage());
		}

	}

	@Override
	public <T> T getFunctionValue(AbstractRoutine<T> routine, Class<T> clazz) {
		T fetchOneInto = dsl.select(routine.asField()).fetchOneInto(clazz);
		return fetchOneInto;
	}

}
