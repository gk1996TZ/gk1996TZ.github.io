package com.litang.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.litang.domain.PostMessage;

@Repository
public interface PostMessageMapper extends BaseMapper<PostMessage>{

	/**
	 * 添加发布信息图片关联表信息的操作
	 * @param postMessageId传入一个发布信息的id
	 * @param imageIds 传入一个图片id的列表
	 * @return 返回受影响行数
	 */
	public Integer insertPostMessageImage(@Param("postMessageId")String postMessageId,@Param("imageIds")List<String> imageIds);
	
	/**
	 * 根据用户id查询发布信息
	 * 返回列表
	 * */
	public List<PostMessage> selectMessagesByUserId(@Param("userId")String userId);
	/**
	 * 获取指定时间前最新的一条某个用户发布信息（不传用户id时返回所有的中最新的一条）
	 * @param userId 传入一个用户id
	 * @param date 传入一个指定的时间
	 * @return 返回指定时间前某一个用户发布的最新的一个发布信息
	 */
	public PostMessage queryTheNewestBeforeAssignTime(@Param("userId")String userId,@Param("date")Date date);
	
	//根据用户id删除发布信息
	public void deleteByUserId(@Param("userId")String userId);
}
