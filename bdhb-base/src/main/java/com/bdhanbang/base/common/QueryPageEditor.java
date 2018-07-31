package com.bdhanbang.base.common;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bdhanbang.base.exception.ValidException;
import com.bdhanbang.base.message.ErrorMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: QueryPageEditor
 * @Description: 转换、验证queryPage
 * @author yangxz
 * @date 2018年7月21日 下午3:27:39
 * 
 */
public class QueryPageEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		ObjectMapper mapper = new ObjectMapper();

		// 没有的值不要
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		QueryPage queryPage = null;

		try {
			// 数据转换
			queryPage = mapper.readValue(text, QueryPage.class);

			// 数据验证
			ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
			Validator validator = vf.getValidator();
			Set<ConstraintViolation<QueryPage>> set = validator.validate(queryPage);
			StringBuffer buf = new StringBuffer();

			for (ConstraintViolation<QueryPage> constraintViolation : set) {
				buf.append(String.format("%s:%s", constraintViolation.getPropertyPath(),
						constraintViolation.getMessage()));
				buf.append(";");
			}

			if (buf.length() > 0) {
				buf.setLength(buf.length() - 1);
				throw new ValidException(new ValidationException(buf.toString()),
						ErrorMessage.UNEXPECTED_VALUE.getStatus(), ErrorMessage.UNEXPECTED_VALUE.getMessage());

			}

			// 数据清0重新使用
			buf.setLength(0);

			// order 验证
			for (Order order : queryPage.getOrders()) {

				Set<ConstraintViolation<Order>> setOrder = validator.validate(order);

				for (ConstraintViolation<Order> constraintViolation : setOrder) {
					buf.append(String.format("%s:%s", constraintViolation.getPropertyPath(),
							constraintViolation.getMessage()));
					buf.append(";");
				}

				if (buf.length() > 0) {
					buf.setLength(buf.length() - 1);
					throw new ValidException(new ValidationException(buf.toString()),
							ErrorMessage.UNEXPECTED_VALUE.getStatus(), ErrorMessage.UNEXPECTED_VALUE.getMessage());

				}

			}

		} catch (IOException e) {
			throw new ValidException(e, ErrorMessage.UNEXPECTED_VALUE.getStatus(),
					ErrorMessage.UNEXPECTED_VALUE.getMessage());
		}

		setValue(queryPage);

	}
}
