package com.litang.facade;
/**
 * 用来表示redis存储键的前缀
 * @author huangfushou
 * @date 2018-02-02
 * */
public interface RedisPrefix {
    /**用于用户TOKEN信息的存储键，例如：code:4235RESTFRSDT34*/
	public static final String TOKEN_CODE_PRE="code:"; 
	/**用于存储跟用户有关的信息，user:张三*/
	public static final String USER_PRE="user:";
	/**用于存放当前注册的用户总数*/
	public static final String USER_REGISTER="registerCount";
	/**用于存放当前审核通过的用户总数*/
	public static final String USER_AUDIT_TRANSIT="userAuditTransitCount";
	/**用于存放当前未审核通过的用户总数*/
	public static final String USER_AUDIT_NOT_TRANSIT="userAuditNotTransitCount";
	/**用于存储最新的10条发布信息，postMessageTopTen:ArrayList<PostMessage>()*/
	public static final String POSTMESSAGE_TOPTEN_PRE="postMessageTopTen:allUser";
	/**用于存放用户发布的最新的10条发布信息，userPostMessageTopTen:张三*/
	public static final String USER_POSTMESSAGE_TOPTEN_PRE="userPostMessageTopTen:";
	/**用于存放所有用户的发布信息数*/
	public static final String POSTMESSAGE_COUNT = "postCount:allUser";
	/**用于存放用户发布信息的总数,例如：userPostCount:张三*/
	public static final String USER_POSTMESSAGE_COUNT="userPostCount:";
}
