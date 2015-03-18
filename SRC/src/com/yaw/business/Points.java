package com.yaw.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 类型描述:注解累加积分:注解给会产生积分方法上;代表把积分累加给当前登陆用户;
 * </br>创建时期: 2015年1月6日
 * @author hyq
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Points {
	/**
	 * 会员操作类型码:
	 * @return ActionType类型的值
	 */
	PointsActionType action();
	/**
	 * 指出方法参数列表中,为会员ID值参数的下标;默认为-1,意为取当前登陆用户的ID;
	 * @return
	 */
	int index() default(-1);
}
