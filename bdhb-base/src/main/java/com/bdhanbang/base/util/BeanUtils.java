package com.bdhanbang.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtils extends org.springframework.beans.BeanUtils {

	/**
	 * @Title: copyPropertiesLambda
	 * @Description: TODO(进行数据拷备,内部使用lambda)
	 * @param @param sourceList
	 * @param @param targetClass
	 * @param @return 设定文件
	 * @return List<T> 返回类型
	 * @throws: BeansException 设定文件
	 */
	public static <S, T> List<T> copyPropertiesLambda(List<S> sourceList, Class<T> targetClass) throws BeansException {

		List<T> targetList = sourceList.stream().map(x -> {
			return BeanUtils.copyProperties(x, targetClass);
		}).collect(Collectors.toList());

		return targetList;

	}

	/**
	 * @Title: copyProperties
	 * @Description: TODO( 进行数据间的拷备)
	 * @param @param sourceList
	 * @param @param targetClass
	 * @param @return
	 * @param @throws BeansException
	 * @return List<T> 数据List<T>
	 * @throws:
	 */
	public static <S, T> List<T> copyProperties(List<S> sourceList, Class<T> targetClass) throws BeansException {

		List<T> targetList = new ArrayList<T>();
		for (S s : sourceList) {
			targetList.add(copyProperties(s, targetClass));
		}

		return targetList;

	}

	/**
	 * @Title: getSaveIterable
	 * @Description: TODO(返回可用于Iterable的数据)
	 * @param @param sourceList
	 * @param @param targetClass
	 * @param @return
	 * @param @throws InstantiationException
	 * @param @throws IllegalAccessException 设定文件
	 * @return Iterable<T> 返回类型
	 * @throws:
	 */
	public static <S, T> Iterable<T> getSaveIterable(List<S> sourceList, Class<T> targetClass) throws BeansException {
		List<T> targetList = copyProperties(sourceList, targetClass);
		Iterable<T> targetIter = new ArrayList<>(targetList);
		return targetIter;
	}

	/**
	 * @Title: getSaveIterableLambda
	 * @Description: TODO(返回可用于Iterable的数据,内部数据转换用的是lambda)
	 * @param @param sourceList
	 * @param @param targetClass
	 * @param @return
	 * @param @throws InstantiationException
	 * @param @throws IllegalAccessException 设定文件
	 * @return Iterable<T> 返回类型
	 * @throws:
	 */
	public static <S, T> Iterable<T> getSaveIterableLambda(List<S> sourceList, Class<T> targetClass)
			throws BeansException {

		List<T> targetList = copyPropertiesLambda(sourceList, targetClass);
		Iterable<T> targetIter = new ArrayList<>(targetList);
		return targetIter;
	}

	/**
	 * @Title: copyProperties
	 * @Description: TODO(复制对象，且忽略源中值为null的字段)
	 * @param @param source
	 * @param @param targetClass
	 * @param @return
	 * @param @throws BeansException 设定文件
	 * @return T 返回类型
	 * @throws:
	 */
	public static <T> T copyProperties(Object source, Class<T> targetClass) throws BeansException {
		try {
			if (Objects.isNull(source)) {
				return null;
			} else {
				PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClass);
				T target = targetClass.newInstance();
				for (PropertyDescriptor targetPd : targetPds) {
					if (targetPd.getWriteMethod() != null) {
						PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
						if (sourcePd != null && sourcePd.getReadMethod() != null) {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
							if (value != null) {
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
						}
					}
				}
				return target;
			}
		} catch (Throwable ex) {
			if (ex.getCause() instanceof InstantiationException) {
				throw new FatalBeanException(BeanUtils.class.getName() + " InstantiationException ", ex);
			} else if (ex.getCause() instanceof IllegalAccessException) {
				throw new FatalBeanException(BeanUtils.class.getName() + " IllegalAccessException ", ex);
			} else {
				throw new FatalBeanException(" Could not copy properties from source to target", ex);
			}
		}

	}

	/**
	 * @Title: copyProperties
	 * @Description: TODO(复制对象，且忽略源中值为null的字段)
	 * @param source 源对象
	 * @param target 目标对象
	 * @param        @throws BeansException
	 * @return void
	 * @throws @author guomh
	 * @date 2017年9月5日 下午4:28:03
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	public static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> targetClass) {
		ObjectMapper mapper = new ObjectMapper();
		T target = null;
		try {
			// add by yangxz date 2019-4-29 begin
			// reason 没有的值不获取
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// add by yangxz date 2019-4-29 end

			target = mapper.readValue(mapper.writeValueAsString(jsonNode), targetClass);
			if (Objects.isNull(target)) {
				target = targetClass.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return target;
	}

	public static JsonNode objectToJsonNode(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(mapper.writeValueAsString(object));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jsonNode;
	}
}
