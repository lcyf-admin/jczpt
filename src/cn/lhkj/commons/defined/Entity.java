package cn.lhkj.commons.defined;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	
	/**对应数据库表名*/
	String table() default ""; 
	
	/**表的主键*/
	String key() default "";
	
	/**分区字段*/
	String partition() default "";
	
}
