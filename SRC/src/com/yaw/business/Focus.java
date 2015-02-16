/**
 * 
 */
package com.yaw.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类型描述:
 * </br>创建时期: 2015年2月11日
 * @author hyq
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Focus {
	/** 关注目标:0:会员,1:相片,2:约请计划*/
	byte target() default(0);
}
