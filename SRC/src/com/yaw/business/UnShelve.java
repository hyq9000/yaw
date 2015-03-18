/**
 * 
 */
package com.yaw.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.mapping.Map;

import antlr.collections.List;

/**
 * 类型描述: 标识需要进行下架处理的查询方法；
 * 为实现将散在各个角度的查询结果进行统一的下架处理逻辑封装到一个类中，
 * 以实现对会员相片，约请计划，会员列表等的下架处理
 * </br>创建时期: 2015年2月11日
 * @author hyq
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnShelve {
	
	/**
	 * 返回结果集中的对象类型,默认为Map.class
	 */
	Class type() default(Map.class);
	/**
	 * 返回结果集中的对象类型中为会员ID的属性名
	 */
	String property() default("maLoginName");
}
