package com.personal.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
	String value() default "";
}
