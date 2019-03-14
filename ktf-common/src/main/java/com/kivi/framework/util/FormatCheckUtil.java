package com.kivi.framework.util;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import com.kivi.framework.exception.ToolBoxException;
import com.kivi.framework.util.kit.StrKit;




public class FormatCheckUtil {
	//private static final Logger log = LoggerFactory.getLogger(FormatCheckUtil.class);
	
	private static ThreadLocal<StringBuffer> threadlocal = new ThreadLocal<StringBuffer>(){
		 protected StringBuffer initialValue() {
		        return new StringBuffer();
		    }
	};
	/**
	 * format验证
	 * <p>
	 * Create Date: 2014-3-3
	 * </p>
	 * 
	 * @param <T>
	 *                需要验证的实例化后的BEAN
	 * @param t
	 * @return
	 */		
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	public static <T> String getValidator(T t) {

		//首先对List结果集中的数据进行校验   递归调用将会检查所有的结果集
		Field[] fields = t.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			try {
				Object obj = field.get(t);
				if(obj != null && obj instanceof java.util.List<?>){
					java.util.List<?> list = (java.util.List<?>)obj;
					for(Object e:list){
						
						String ret =  getValidator(e);
						if(StrKit.isBlank(ret))
							continue;
						else
							return ret;
					}
				}
			} catch (Exception e) {
				throw new ToolBoxException(e);
			}
		}
		
		StringBuffer buf = threadlocal.get();
		buf.setLength(0);
		int i = 0;

		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<T>> violations = validator.validate(t);
		if (violations.size() != 0) {
			for (ConstraintViolation<T> violation : violations) {
				if (i == 0) {
					i++;
					buf.append(violation.getMessage());
				} else {
					buf.append("|");
					buf.append(violation.getMessage());
				}
			}
		}
		return buf.toString();
	}
}
