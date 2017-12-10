package cn.lhkj.commons.defined;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OptLog {
	String info() default ""; 
}
