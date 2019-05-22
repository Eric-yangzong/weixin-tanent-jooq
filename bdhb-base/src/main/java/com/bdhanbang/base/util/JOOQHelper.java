package com.bdhanbang.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import com.bdhanbang.base.common.Order;
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.exception.ValidException;
import com.bdhanbang.base.message.ErrorMessage;


/**
 * @ClassName: JOOQHelper
 * @Description: JOOQHelper增、删、改查的帮助类
 * @author yangxz
 * @date 2018年7月12日 上午8:58:12
 * 
 */
public class JOOQHelper {

	/**
	 * @ClassName: Od
	 * @Description: 排序
	 * @author yangxz
	 * @date 2018年9月19日 下午4:10:30
	 * 
	 */
	public static class Od {
		/**
		 * @Fields asc : asc
		 */
		public final static int asc = 1;
		/**
		 * @Fields desc : desc
		 */
		public final static int desc = 2;
	}

	/**
	 * @ClassName: relation
	 * @Description: 条件间的关系
	 * @author yangxz
	 * @date 2018年9月19日 下午4:11:22
	 * 
	 */
	public static class Relation {
		/**
		 * @Fields and : and
		 */
		public final static int and = 1;
		/**
		 * @Fields or : or
		 */
		public final static int or = 2;
	}

	/**
	 * @ClassName: op
	 * @Description: 条件操作符
	 * @author yangxz
	 * @date 2018年9月19日 下午4:11:56
	 * 
	 */
	public static class Op {
		/**
		 * @Fields equals : =
		 */
		public final static int equals = 1;
		/**
		 * @Fields notEquals : !=
		 */
		public final static int notEquals = 2;
		/**
		 * @Fields like : ilike(ignore case)
		 */
		public final static int like = 3;

		/**
		 * @Fields great : >
		 */
		public final static int great = 4;

		/**
		 * @Fields greatEquals : >=
		 */
		public final static int greatEquals = 5;

		/**
		 * @Fields less : <
		 */
		public final static int less = 6;

		/**
		 * @Fields lessEquals : <=
		 */
		public final static int lessEquals = 7;

		/**
		 * @Fields notLike : not like
		 */
		public final static int notLike = 8;
		/**
		 * @Fields isNull : isNull
		 */
		public final static int isNull = 9;
		/**
		 * @Fields isNotNull : is not null
		 */
		public final static int isNotNull = 10;
		/**
		 * @Fields in : in
		 */
		public final static int in = 11;

		/**
		 * @Fields notIn : notIn
		 */
		public final static int notIn = 12;
	}

	public static Field<Object> jsonObject(Field<?> field, String name) {
		return DSL.field("{0}->{1}", Object.class, field, DSL.inline(name));
	}

	public static Field<Object> jsonText(Field<?> field, String name) {
		return DSL.field("{0}->>{1}", Object.class, field, DSL.inline(name));
	}

	public static void analyzeQuery(TableImpl<?> tableImpl, SelectJoinStep<Record> selectStep, QueryPage queryPage) {

		// 生成查询条件
		Condition condition = analyzeQuery(tableImpl, queryPage.getQuerys());

		// 生成where
		if (!Objects.isNull(condition)) {
			selectStep.where(condition);
		}

	}

	/**
	 * @Title: analyzeQuery
	 * @Description: 分析查询条件
	 * @param @param tableImpl
	 * @param @param queryList
	 * @param @return 设定文件
	 * @return Condition 返回类型
	 * @throws:
	 */
	public static Condition analyzeQuery(TableImpl<?> tableImpl, List<Query> queryList) {

		int relation = Relation.and;
		Condition condition = null;

		if (Objects.isNull(queryList)) {
			return condition;
		}

		for (Query query : queryList) {

			Field<?> field = null;
			String fieldName = query.getField();

			if (!"".equals(fieldName) && !Objects.isNull(fieldName)) {
				int i = fieldName.indexOf(".");

				if (i > 0) {
					String[] fieldList = fieldName.split("\\.");
					for (int j = 0; j < fieldList.length; j++) {

						if (Objects.isNull(field)) {
							field = tableImpl.field(JOOQHelper.HumpToUnderline(fieldList[j]));
						}

						if (j + 2 == fieldList.length) {
							field = jsonText(field, fieldList[j + 1]);
							j = j + 2;
						} else {
							field = jsonObject(field, fieldList[j + 1]);
						}
					}
				} else {
					checkFieldExist(tableImpl, query.getField());
					field = tableImpl.field(HumpToUnderline(query.getField()));
				}

				if (Objects.isNull(condition)) {
					condition = analyzeOperation(field, query.getValue(), query.getOp());

					relation = query.getRelation();

				} else {
					Condition conditionNext = analyzeOperation(field, query.getValue(), query.getOp());
					if (!Objects.isNull(conditionNext)) {
						if (Relation.and == relation) {
							condition = condition.and(conditionNext);
						} else {
							condition = condition.or(conditionNext);
						}
						relation = query.getRelation();
					}
				}
			} else {
				relation = query.getRelation();// add by yangxz date 2018-12-29 reason:条件为空也要保留关系运算
			}

			// 子查询条件
			List<Query> queryChildren = query.getQuerys();
			if (queryChildren.size() > 0) {
				Condition conditionChildren = analyzeQuery(tableImpl, queryChildren);
				if (Objects.isNull(condition)) {
					condition = DSL.condition(conditionChildren.toString());// reason:括号加的不对bug修复
				} else {
					if (Relation.and == relation) {
						condition = condition.and(conditionChildren.toString());
					} else {
						condition = condition.or(conditionChildren.toString());
					}
				}

			}

		}

		return condition;
	}

	@SuppressWarnings("unchecked")
	public static Condition analyzeOperation(Field<?> field, Object value, Integer op) {
		// 操作符1:=,2:!=,3:like,4:>,5:>=,6:<,7:<=,8:not like, 9:isNull,10:isNotNull,11:in

		Condition condition = null;

		// 如果值为空且操作符不是9或10条件无效
		if (Objects.isNull(value) && op != Op.isNull && op != Op.isNotNull) {
			return condition;
		}

		if (field.getDataType().isDateTime()) {
			if (value.toString().length() > 10) {
				condition = analyzeOperationTime((Field<OffsetDateTime>) field, value.toString(), op);
			} else {
				condition = analyzeOperation((Field<OffsetDateTime>) field, value.toString(), op);
			}
		}else if (field.getDataType().isArray()) {
			condition = analyzeOperationList((Field<List<String>>) field, value.toString(), op);
		} else {
			if (op != Op.in) {
				if (field.getDataType().isNumeric()) {
					if (!isNumeric(value.toString())) {
						// 不是数字，抛出错误
						throw new ValidException(new TypeNotPresentException(field.getName(), null),
								ErrorMessage.NUMBER_TYPE.getStatus(), ErrorMessage.NUMBER_TYPE.getMessage());

					}
				}
			}
			condition = analyzeOperationMain((Field<Object>) field, value, op);
		}

		return condition;
	}

	/**
	 * @Title: isNumeric
	 * @Description: 是否是数字
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws:
	 */
	public static boolean isNumeric(String str) {

		String[] strList = str.split("\\.");

		if (strList.length == 2) {
			str = str.replace(".", "");
		} else if (strList.length > 2) {
			return false;
		}

		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @Title: analyzeOperationMain
	 * @Description: 条件如要处理方式
	 * @param @param field
	 * @param @param value
	 * @param @param op
	 * @param @return 设定文件
	 * @return Condition 返回类型
	 * @throws:
	 */
	@SuppressWarnings("unchecked")
	public static Condition analyzeOperationMain(Field<Object> field, Object value, Integer op) {
		Condition condition = null;
		switch (op) {
		case Op.equals:
			condition = field.eq(value);
			break;
		case Op.notEquals:
			condition = field.notEqual(value);
			break;
		case Op.like:
			condition = field.likeIgnoreCase(value.toString());
			break;
		case Op.great:
			condition = field.gt(value);
			break;
		case Op.greatEquals:
			condition = field.ge(value);
			break;
		case Op.less:
			condition = field.lt(value);
			break;
		case Op.lessEquals:
			condition = field.le(value);
			break;
		case Op.notLike:
			condition = field.notLikeIgnoreCase(value.toString());
			break;
		case Op.isNull:
			condition = field.isNull();
			break;
		case Op.isNotNull:
			condition = field.isNotNull();
			break;
		case Op.in:
			if (value instanceof Select) {
				condition = field.in(value);
			} else if (value instanceof String) {
				condition = field.in(Arrays.asList(value.toString().split(",")));
			} else {
				List<String> strList = (List<String>) value;
				condition = field.in(strList);
			}
			break;
		case Op.notIn:
			if (value instanceof Select) {
				condition = field.notIn(value);
			} else if (value instanceof String) {
				condition = field.notIn(Arrays.asList(value.toString().split(",")));
			} else {
				List<String> strList = (List<String>) value;
				condition = field.notIn(strList);
			}
			break;
		default:
			condition = field.eq(value);
			break;
		}

		return condition;
	}
	
	public static Condition analyzeOperationList(Field<List<String>> field, String value, Integer op) {
		Condition condition = null;

		switch (op) {
		case Op.equals:
			condition = DSL.cast(value, String.class).eq(DSL.function("any", String.class, field));
			break;
		default:
			condition = DSL.cast(value, String.class).eq(DSL.function("any", String.class, field));
			break;
		}

		return condition;
	}

	public static Condition analyzeOperation(Field<OffsetDateTime> field, String value, Integer op) {
		// 操作符1:=,2:!=,3:like,4:>,5:>=,6:<,7:<=,8:not like, 9:isNull,10:isNotNull
		Condition condition = null;

		OffsetDateTime dayMinTime = getOffsetDateTime(value, "00:00:00");
		OffsetDateTime dayMaxTime = getOffsetDateTime(value, "23:59:59");

		switch (op) {
		case Op.equals:
			condition = field.ge(dayMinTime);
			condition = condition.and(field.le(dayMaxTime));
			break;
		case Op.notEquals:
			condition = field.lt(dayMinTime);
			condition = condition.and(field.gt(dayMaxTime));
			break;
		case Op.like:
			condition = field.ge(dayMinTime);
			condition = condition.and(field.le(dayMaxTime));
			break;
		case Op.great:
			condition = field.gt(dayMaxTime);
			break;
		case Op.greatEquals:
			condition = field.ge(dayMinTime);
			break;
		case Op.less:
			condition = field.lt(dayMinTime);
			break;
		case Op.lessEquals:
			condition = field.le(dayMaxTime);
			break;
		case Op.notLike:
			condition = field.lt(dayMinTime);
			condition = condition.and(field.gt(dayMaxTime));
			break;
		case Op.isNull:
			condition = field.isNull();
			break;
		case Op.isNotNull:
			condition = field.isNotNull();
			break;
		default:
			condition = field.ge(dayMinTime);
			condition = condition.and(field.le(dayMaxTime));
			break;
		}

		return condition;
	}

	public static Condition analyzeOperationTime(Field<OffsetDateTime> field, String value, Integer op) {
		// 操作符4:>,5:>=,6:<,7:<=
		Condition condition = null;

		OffsetDateTime dayMinTime = OffsetDateTime.parse(value);
		switch (op) {
		case Op.great:
			condition = field.gt(dayMinTime);
			break;
		case Op.greatEquals:
			condition = field.ge(dayMinTime);
			break;
		case Op.less:
			condition = field.lt(dayMinTime);
			break;
		case Op.lessEquals:
			condition = field.le(dayMinTime);
			break;
		default:
			condition = field.ge(dayMinTime);
			break;
		}

		return condition;
	}

	/**
	 * @Title: getDateTime
	 * @Description: 得到期对应时间
	 * @param @param dateStr
	 * @param @param time
	 * @param @return 设定文件
	 * @return LocalDateTime 返回类型
	 * @throws:
	 */
	public static LocalDateTime getDateTime(String dateStr, String time) {

		try {
			LocalDate localDate = LocalDate.parse(dateStr);
			LocalTime localTime = LocalTime.parse(time);

			return LocalDateTime.of(localDate, localTime);

		} catch (Exception e) {
			throw new ValidException(e, ErrorMessage.DATE_TYPE.getStatus(), ErrorMessage.DATE_TYPE.getMessage());
		}

	}

	public static OffsetDateTime getOffsetDateTime(String dateStr, String time) {

		try {
			LocalDate localDate = LocalDate.parse(dateStr);
			LocalTime localTime = LocalTime.parse(time);

			return OffsetDateTime.of(localDate, localTime, ZoneOffset.ofHours(8));

		} catch (Exception e) {
			throw new ValidException(e, ErrorMessage.DATE_TYPE.getStatus(), ErrorMessage.DATE_TYPE.getMessage());
		}

	}

	public static void analyzeOrder(TableImpl<?> tableImpl, SelectJoinStep<Record> selectStep, QueryPage queryPage) {

		List<OrderField<?>> orderFieldList = analyzeOrder(tableImpl, queryPage.getOrders());

		// 排序
		if (orderFieldList.size() > 0) {
			selectStep.orderBy(orderFieldList);
		}

		selectStep.limit(queryPage.getSize()); // 设置页面限制
		selectStep.offset((queryPage.getPage() - 1) * queryPage.getSize());// 设置

	}

	public static List<OrderField<?>> analyzeOrder(TableImpl<?> tableImpl, List<Order> orderList) {

		List<OrderField<?>> orderFieldList = new ArrayList<>();

		for (Order order : orderList) {
			Field<?> field = null;
			String fieldName = order.getFieldName();

			int i = fieldName.indexOf(".");
			if (i > 0) {
				String[] fieldList = fieldName.split("\\.");
				for (int j = 0; j < fieldList.length; j++) {

					if (Objects.isNull(field)) {
						field = tableImpl.field(HumpToUnderline(fieldList[j]));
					}

					if (j + 2 == fieldList.length) {
						field = jsonText(field, fieldList[j + 1]);
						j = j + 2;
					} else {
						field = jsonObject(tableImpl.field(fieldList[j]), fieldList[j + 1]);
					}
				}
			} else {
				checkFieldExist(tableImpl, fieldName);
				field = tableImpl.field(HumpToUnderline(fieldName));
			}

			if (Od.asc == order.getOrder()) {
				orderFieldList.add(field.asc().nullsLast());
			} else {
				orderFieldList.add(field.desc().nullsLast());
			}

		}

		return orderFieldList;
	}

	/**
	 * @Title: checkFieldExist
	 * @Description: 验证字段是否存在
	 * @param @param tableImpl
	 * @param @param filedName 设定文件
	 * @return void 返回类型
	 * @throws:ValidException
	 */
	public static void checkFieldExist(TableImpl<?> tableImpl, String filedName) {

		if (tableImpl.field(HumpToUnderline(filedName)) == null) {
			// 请求字段不存在错误
			throw new ValidException(new TypeNotPresentException(filedName, null),
					ErrorMessage.FIELD_NOT_EXIST.getStatus(), ErrorMessage.FIELD_NOT_EXIST.getMessage());
		}

	}

	/***
	 * 驼峰命名转为下划线命名
	 * 
	 * @param para 驼峰命名的字符串
	 */

	public static String HumpToUnderline(String para) {
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;// 定位
		for (int i = 0; i < para.length(); i++) {
			if (Character.isUpperCase(para.charAt(i))) {
				sb.insert(i + temp, "_");
				temp += 1;
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * @Title: UnderlineToHump
	 * @Description: 下画线转驼峰
	 * @param @param para
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws:
	 */
	public static String UnderlineToHump(String para) {

		StringBuilder hump = new StringBuilder();
		String underlines[] = para.split("_");

		for (String underline : underlines) {
			if (hump.length() == 0) {
				hump.append(underline.toLowerCase());
			} else {
				hump.append(underline.substring(0, 1).toUpperCase());
				hump.append(underline.substring(1).toLowerCase());
			}
		}

		return hump.toString();
	}

}