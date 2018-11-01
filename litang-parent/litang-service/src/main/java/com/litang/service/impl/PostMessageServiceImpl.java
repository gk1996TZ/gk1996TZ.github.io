package com.litang.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.bean.ImagePath;
import com.litang.domain.Image;
import com.litang.domain.PostMessage;
import com.litang.domain.User;
import com.litang.facade.RedisPrefix;
import com.litang.mapper.BaseMapper;
import com.litang.mapper.ImageMapper;
import com.litang.mapper.PostMessageMapper;
import com.litang.mapper.UserMapper;
import com.litang.page.PageView;
import com.litang.service.PostMessageService;
import com.litang.utils.CollectionUtils;
import com.litang.utils.JedisUtils;

/**
 * 发布信息Service
 */
@Service
public class PostMessageServiceImpl extends BaseServiceImpl<PostMessage> implements PostMessageService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PostMessageMapper postMessageMapper;
	@Autowired
	ImageMapper imageMapper;
	@Autowired
	UserMapper userMapper;

//	/**
//	 * 重写父类父类insert方法
//	 */
//	public boolean insert(PostMessage postMessage) {
//
//		// 第一步 : 保存发布消息
//		super.insert(postMessage);
//
//		// 第二步 : 保存图片
//		Date currentDate = new Date();
//		List<Image> images = new ArrayList<Image>();
//		for (String path : postMessage.getPaths()) {
//			Image image = new Image();
//			image.setImageUrl(path);
//			image.setCreatedTime(currentDate);
//			image.setDeleted(true);
//			images.add(image);
//		}
//		imageMapper.insertAll(images);
//
//		// 第三步 : 保存中间表数据(t_post_message_image)
//		List<String> imageIds = new ArrayList<String>();
//		for (Image image : images) {
//			imageIds.add(image.getId());
//		}
//		postMessageMapper.insertPostMessageImage(postMessage.getId(), imageIds);
//
//		// 维护缓存中的发布信息
//		// 所有用户最新的10条发布信息
//		/*
//		 * Object obj =
//		 * JedisUtils.getObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE); if(obj !=
//		 * null){ if(obj instanceof ArrayList){ List<Object> list =
//		 * (List<Object>) obj; if(list.size() > 0){ Object o = list.get(0); if(o
//		 * instanceof PostMessage){ List<PostMessage> listPost =
//		 * (List<PostMessage>) obj; List<PostMessage> listTemp = new
//		 * ArrayList<PostMessage>(); listTemp.add(postMessage);
//		 * listTemp.addAll(listPost); listTemp.remove(listTemp.size()-1);
//		 * JedisUtils.setObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE, listTemp); }
//		 * } } } //当前用户最新的10条发布信息 try { User u =
//		 * userMapper.queryById(postMessage.getUser().getId()); Object objU =
//		 * JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE+u.
//		 * getUserName()); Integer countU = 0; if(objU != null && objU
//		 * instanceof List){ List<Object> list = (List<Object>) objU;
//		 * if(list.size() > 0){ Object o = list.get(0); if(o instanceof
//		 * PostMessage){ List<PostMessage> listPost = (List<PostMessage>) objU;
//		 * List<PostMessage> listTemp = new ArrayList<PostMessage>();
//		 * listTemp.add(postMessage); listTemp.addAll(listPost);
//		 * listTemp.remove(listTemp.size()-1);
//		 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE+u.
//		 * getUserName(), listTemp); } } } //将缓存中当前用户的发布数加一
//		 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
//		 * (), countU++); } catch (NullPointerException e) {
//		 * logger.info("维护单个用户，用户id为"+postMessage.getUser().getId()+
//		 * "在缓存中的发布信息时用户或发布信息为空"); throw new
//		 * NullPointerException("维护单个用户，用户id为"+postMessage.getUser().getId()+
//		 * "在缓存中的发布信息时用户或发布信息为空"); }
//		 * 
//		 * // 维护缓存中发布信息总数(所有的总数，某个用户的总数) //单个用户的 try { User u =
//		 * userMapper.queryById(postMessage.getUser().getId()); Object oU =
//		 * JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
//		 * ()); Integer countU = 0; if(oU != null && oU instanceof Integer){
//		 * countU = (Integer) oU; } //将缓存中当前用户的发布数加一
//		 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
//		 * (), countU++); } catch (NullPointerException e) {
//		 * logger.info("维护单个用户，用户id为"+postMessage.getUser().getId()+
//		 * "在缓存中的发布信息总数时用户或发布信息为空"); throw new
//		 * NullPointerException("维护单个用户，用户id为"+postMessage.getUser().getId()+
//		 * "在缓存中的发布信息时用户或发布信息为空"); }
//		 * 
//		 * //所有用户的 Integer countA = 0; Object oA =
//		 * JedisUtils.getObject(RedisPrefix.POSTMESSAGE_COUNT); if(oA != null &&
//		 * oA instanceof Integer){ countA = (Integer) oA; } //将缓存中所有用户发布数加一
//		 * JedisUtils.setObject(RedisPrefix.POSTMESSAGE_COUNT, countA++);
//		 */
//
//		return true;
//	}

	@Override
	public boolean insertPostMessageImage(String postMessageId, List<String> imagePaths) {
		// TODO 这里要加事务的操作
		Integer len = 0;
		try {
			len = imagePaths.size();
			if (len == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.info("要添加的发布信息Id为" + postMessageId + "的图片为空");
			throw new NullPointerException("要添加的发布信息Id为" + postMessageId + "的图片为空");
		}
		List<Image> images = new ArrayList<Image>();
		for (String path : imagePaths) {
			Image image = new Image();
			image.setImageUrl(path);
			images.add(image);
		}
		// 添加图片
		Integer count = imageMapper.insertAll(images);
		if (count != imagePaths.size()) {
			try {
				throw new RuntimeException();
			} catch (RuntimeException e) {
				logger.info("添加的发布信息Id为" + postMessageId + "的图片添加失败", e);
				throw new RuntimeException("添加的发布信息Id为" + postMessageId + "的图片添加失败");
			}
		}
		// 添加发布信息图片中间表信息
		List<String> imageIds = new ArrayList<String>();
		for (Image image : images) {
			imageIds.add(image.getId());
		}
		Integer cont = postMessageMapper.insertPostMessageImage(postMessageId, imageIds);
		if (len == cont) {
			return true;
		}
		return false;
	}

	@Override
	public PageView<PostMessage> queryPageData() {
		return this.queryPageData(-1L, -1L, null, null, null);
	}

	/**
	 * 联合查询带分页
	 */
	@Override
	public PageView<PostMessage> queryPageData(Long currentPageNum, Long pageSize, String whereSQL,
			List<Object> whereParams, LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 第四步、获取orderby条件
		String orderby = buildOrderby(orderBy);
		// 用来查询数据总数的sql
		String sql = "select count(tpm.id) from t_post_message tpm where 1 = 1 " + whereSQL;

		String selectSQL = "select "
				+ "tpm.id,tpm.user_id,tpm.message_content,tpm.message_address,tpm.message_longitude,tpm.message_latitude,tpm.created_time,"
				+ "ti.id as imId,ti.image_url,ti.image_url_sl,ti.image_type,ti.image_name, "
				+ "tu.id as uId,tu.user_name,tui.id as uiId,tui.user_head " + "from " + "t_post_message tpm "
				+ "left join t_post_message_image tpmi on tpm.id=tpmi.post_message_id "
				+ "left join t_image ti on tpmi.image_id=ti.id " + "left join t_user tu on tu.id=tpm.user_id "
				+ "left join t_userinfo tui on tui.user_id=tu.id " + "where 1 = 1 "
				+ "and tpm.id in (select temp.id from (select tpm.id from t_post_message tpm where 1 = 1 " + whereSQL
				+ " " + orderby + " " + limit + ") as temp)" + orderby;

		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = postMessageMapper.selectTotalRecoreds(sql);

		PageView<PostMessage> pageView = new PageView<PostMessage>(count, currentPageNum, pageSize);

		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		List<PostMessage> postMessages = postMessageMapper.selectPageData(selectSQL);
		pageView.setDatas(postMessages);

		return pageView;
	}

	@Override
	protected BaseMapper<PostMessage> getMapper() {
		return postMessageMapper;
	}

	@Override
	protected String getFields() {
		return " * ";
	}

	/**
	 * 根据用户id查询用户所发布的信息
	 */
	@Override
	public List<PostMessage> queryByUserId(String userId) {

		List<PostMessage> postMessages = postMessageMapper.selectMessagesByUserId(userId);

		return CollectionUtils.toList(postMessages);
	}

	/**
	 * 根据发布id删除发布信息并且删除图片
	 *
	 * @Override public boolean deleteById(String id) {
	 * 
	 *           //首先根据id查询出他的信息包括图片 PostMessage
	 *           postMessage=postMessageMapper.queryById(id);
	 *           if(postMessage!=null){
	 *           List<Image>images=postMessage.getImages(); StringBuilder sb=new
	 *           StringBuilder(); for(Image image:images){
	 *           sb.append(image.getId()).append(","); }
	 *           sb.deleteCharAt(sb.length()-1);
	 * 
	 *           //批量删除图片 imageMapper.deleteByIds(sb.toString()); } return
	 *           postMessageMapper.deleteById(id)==1; }
	 */

	@Override
	public PostMessage queryTheNewestBeforeAssignTime(String userId, Date date) {
		return postMessageMapper.queryTheNewestBeforeAssignTime(userId, date);
	}

	@Override
	public boolean postMessage(PostMessage postMessage, List<ImagePath> imagePaths) {
		// 第二步 : 保存图片
		Date currentDate = new Date();
		List<Image> images = null;
		if (imagePaths != null && imagePaths.size() > 0) {
			images = new ArrayList<Image>();
			for (ImagePath imagePath : imagePaths) {
				Image image = new Image();
				image.setCreatedTime(currentDate);
				image.setImageUrl(imagePath.getSrcPath());
				image.setImageUrl_SL(imagePath.getThumPath());
				image.setFileName(imagePath.getUuid());
				image.setDeleted(true);
				image.setUpdatedTime(currentDate);
				images.add(image);
			}
			imageMapper.insertAll(images);
			
			//保存发布信息返回主键id
			super.insert(postMessage);

			// 第三步 : 保存中间表数据(t_post_message_image)
			List<String> imageIds = new ArrayList<String>();
			for (Image image : images) {
				imageIds.add(image.getId());
			}
			postMessageMapper.insertPostMessageImage(postMessage.getId(), imageIds);

			// 维护缓存中的发布信息
			// 所有用户最新的10条发布信息
			/*
			 * Object obj =
			 * JedisUtils.getObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE); if(obj !=
			 * null){ if(obj instanceof ArrayList){ List<Object> list =
			 * (List<Object>) obj; if(list.size() > 0){ Object o = list.get(0); if(o
			 * instanceof PostMessage){ List<PostMessage> listPost =
			 * (List<PostMessage>) obj; List<PostMessage> listTemp = new
			 * ArrayList<PostMessage>(); listTemp.add(postMessage);
			 * listTemp.addAll(listPost); listTemp.remove(listTemp.size()-1);
			 * JedisUtils.setObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE, listTemp); }
			 * } } } //当前用户最新的10条发布信息 try { User u =
			 * userMapper.queryById(postMessage.getUser().getId()); Object objU =
			 * JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE+u.
			 * getUserName()); Integer countU = 0; if(objU != null && objU
			 * instanceof List){ List<Object> list = (List<Object>) objU;
			 * if(list.size() > 0){ Object o = list.get(0); if(o instanceof
			 * PostMessage){ List<PostMessage> listPost = (List<PostMessage>) objU;
			 * List<PostMessage> listTemp = new ArrayList<PostMessage>();
			 * listTemp.add(postMessage); listTemp.addAll(listPost);
			 * listTemp.remove(listTemp.size()-1);
			 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE+u.
			 * getUserName(), listTemp); } } } //将缓存中当前用户的发布数加一
			 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
			 * (), countU++); } catch (NullPointerException e) {
			 * logger.info("维护单个用户，用户id为"+postMessage.getUser().getId()+
			 * "在缓存中的发布信息时用户或发布信息为空"); throw new
			 * NullPointerException("维护单个用户，用户id为"+postMessage.getUser().getId()+
			 * "在缓存中的发布信息时用户或发布信息为空"); }
			 * 
			 * // 维护缓存中发布信息总数(所有的总数，某个用户的总数) //单个用户的 try { User u =
			 * userMapper.queryById(postMessage.getUser().getId()); Object oU =
			 * JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
			 * ()); Integer countU = 0; if(oU != null && oU instanceof Integer){
			 * countU = (Integer) oU; } //将缓存中当前用户的发布数加一
			 * JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName
			 * (), countU++); } catch (NullPointerException e) {
			 * logger.info("维护单个用户，用户id为"+postMessage.getUser().getId()+
			 * "在缓存中的发布信息总数时用户或发布信息为空"); throw new
			 * NullPointerException("维护单个用户，用户id为"+postMessage.getUser().getId()+
			 * "在缓存中的发布信息时用户或发布信息为空"); }
			 * 
			 * //所有用户的 Integer countA = 0; Object oA =
			 * JedisUtils.getObject(RedisPrefix.POSTMESSAGE_COUNT); if(oA != null &&
			 * oA instanceof Integer){ countA = (Integer) oA; } //将缓存中所有用户发布数加一
			 * JedisUtils.setObject(RedisPrefix.POSTMESSAGE_COUNT, countA++);
			 */
		}
		// 第一步 : 保存发布消息
		return true;
	}
}
