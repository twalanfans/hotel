package com.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Target 注解 标示该自定义的作用范围
 * @Retention 代表自定义注解的存活时长
 * 		Class 代表注解在编译时有效  运行时自动屏蔽  检查作用  @override
 * 		Runtime 代表注解在运行时有效   随着程序运行
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment {
	String value() default "ww";
}
