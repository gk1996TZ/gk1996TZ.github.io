package com.litang.service;

import java.util.Date;
import java.util.List;

import com.litang.bean.ImagePath;
import com.litang.domain.PostMessage;

public interface PostMessageService extends BaseService<PostMessage>{

	/**
	 * 添加发布信息图片信息的操作
	 * @param postMessageId 传入一个发布信息的id
	 * @param imagePaths 传入一个图片路径的列表
	 * @return 返回是否添加成功
	 */
	public boolean insertPostMessageImage(String postMessageId,List<String> imagePaths);
	
	/**
	 * 根据用户id查询发布信息
	 * */
	public List<PostMessage> queryByUserId(String userId);
	/**
	 * 获取指定时间前最新的一条某个用户发布信息（不传用户id时返回所有的中最新的一条）
	 * @param userId 传入一个用户id
	 * @param date 传入一个指定时间
	 * @return 返回指定时间前最新的一条发布信息
	 */
	public PostMessage queryTheNewestBeforeAssignTime(String userId,Date date);
	
	public boolean postMessage(PostMessage postMessage,List<ImagePath>imagePaths);
}
