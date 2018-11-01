package com.litang.response;

public class ResponseResult {

	/**状态码*/
	private Integer code;
	/**返回数据*/
	private Object data;
	/**返回信息*/
	private String msg;
	
	public ResponseResult() {
	}

	public ResponseResult(Object data){
		this.code = StatusCode.SUCCESS.getCode();
		this.msg = StatusCode.SUCCESS.getMessage();
		this.data = data;
	}
	
	public ResponseResult(Integer code, Object data, String msg) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public ResponseResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @Description: 操作成功
	 * @Author: 朱俊亮
	 * @Date: 2018年7月18日 下午6:10:28
	 * @Return: ResponseResult 返回操作信息
	 */
	public static ResponseResult ok(){
		return new ResponseResult(null);
	}
	/**
	 * @Description: 操作成功并返回数据
	 * @Author: 朱俊亮
	 * @Date: 2018年7月18日 下午6:11:58
	 * @Param: data 传入返回给前台的数据
	 * @Return: ResponseResult 返回操作信息
	 */
	public static ResponseResult ok(Object data){
		return new ResponseResult(data);
	}
	/**
	 * 跳过登陆
	 * @param data
	 * @return
	 */
	public static ResponseResult jumpLogin(Object data){
		StatusCode statusCode = StatusCode.JUMP_LOGIN;
		return new ResponseResult(statusCode.getCode(),data,statusCode.getMessage());
	}
	/**
	 * @Description:
	 * @Author: 朱俊亮
	 * @Date: 2018年7月18日 下午6:16:54
	 * @return
	 */
	public static ResponseResult notFound(){
		StatusCode statusCode = StatusCode.NOT_FOUND;
		return new ResponseResult(statusCode.getCode(),statusCode.getMessage());
	}
	
	public static ResponseResult loginRestart(){
		StatusCode statusCode = StatusCode.LOGIN_RESTART;
		return new ResponseResult(statusCode.getCode(),statusCode.getMessage());
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
