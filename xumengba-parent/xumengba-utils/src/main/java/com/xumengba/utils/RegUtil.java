package com.xumengba.utils;

import java.util.regex.Pattern;

/**
 * 用来验证后台安全性
 * @author huangfushou
 * @date 2018-02-02
 * */
public class RegUtil {
	
	 /**
     * 正则表达式：验证用户名
     */
    //public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    public static final String REGEX_USERNAME = "^[a-zA-Z\u4e00-\u9fa5][\u4e00-\u9fa5,\\w]{1,17}$";
    /**
     * 正则表达式：验证密码
     */
   // public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$"
    public static final String REGEX_PASSWORD = "^[a-zA-Z][\\w,]{5,17}$";
 
    /**
     * 正则表达式：验证手机号
     */
    //public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE="^1[3|4|5|7|8][0-9]\\d{8}$";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{0,10}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证人名只能输入一到四个汉字
     */
    public static final String REGEX_PERSON_NAME = "[\u4e00-\u9fa5]{2,4}";
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
    /**
     * 校验是否为整数 
     * 
     * @param str 传入的字符串 
     * @return 是整数返回true,否则返回false 
     */
     public static boolean isInteger(String str) {  
           Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
           return pattern.matcher(str).matches();  
     }
     /**
      * 校验人名
      * 
      * @param name
      * @return 校验通过返回true，否则返回false
      */
     public static boolean isPersonName(String name) {
         return Pattern.matches(REGEX_PERSON_NAME, name);
     }
     
     
     public static void main(String[] args) {
		
    	 
    	 String password="y1dfageeeeeeeeeeee2";
    	 boolean falg=isPassword(password);
    	 System.out.println(falg);
    	 
    	 String username="1fdfaf";
    	 boolean isture=isUsername(username);
    	 System.out.println(isture);
	}
}
