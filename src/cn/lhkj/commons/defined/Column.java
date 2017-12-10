package cn.lhkj.commons.defined;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	/**对应数据库的字段名*/
	String value() default "";
	
	/**是否是clob字段*/
	boolean clob() default false;
	
}
