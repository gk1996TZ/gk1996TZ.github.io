package com.xumengba.response;

/**
 * @Description: 统一返回的对象类型
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月13日 下午12:33:19
 */
public class ResponseResult {

	// 响应业务状态
	private Integer code;

	// 响应中的数据
	private Object data;

	// 响应消息
	private String msg;
	
	public ResponseResult(){
		
	}

	/**
	* @Description: 操作成功
	* @date: 2018年4月18日 下午2:58:41
	 */
	public static ResponseResult ok() {
		return new ResponseResult(null);
	}
	
	/**
	 * @Description: 要查询的数据不存在
	* @date: 2018年4月18日 下午2:58:57
	 */
	public static ResponseResult notFound() {
		StatusCode statusCode = StatusCode.NOT_FOUND;
		return ResponseResult.build(statusCode.getCode(),statusCode.getMessage());
	}

	public static ResponseResult ok(Object data) {
		return new ResponseResult(data);
	}

	public ResponseResult(Object data) {
		this.code = StatusCode.SUCESS.getCode();
		this.msg = StatusCode.SUCESS.getMessage();
		this.data = data;
	}

	public ResponseResult(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static ResponseResult build(Integer status, String msg) {
		return new ResponseResult(status, msg, null);
	}

	public static ResponseResult build(Integer status, String msg, Object data) {
		return new ResponseResult(status, msg, data);
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

	@Override
	public String toString() {
		return "[code=" + code + ", data=" + data + ", msg=" + msg + "]";
	}
}
