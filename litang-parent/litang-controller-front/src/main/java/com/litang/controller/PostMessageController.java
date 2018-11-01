package com.litang.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.litang.bean.ImagePath;
import com.litang.domain.PostMessage;
import com.litang.domain.User;
import com.litang.exception.base.BizException;
import com.litang.helper.QueryHelper;
import com.litang.page.PageView;
import com.litang.request.PostMessageForm;
import com.litang.response.ResponseResult;
import com.litang.response.StatusCode;
import com.litang.service.PostMessageService;
import com.litang.service.PropertiesService;
import com.litang.utils.NarrowImage;
import com.litang.utils.UploadUtil;

@RestController("PostMessageController")
@RequestMapping("/front/postMessage")
public class PostMessageController {
	@Autowired
	private PostMessageService postMessageService;
	@Autowired
	private PropertiesService propertiesService;
	/***
	@RequestMapping("postMessage.action")
	public ResponseResult postMessage(PostMessageForm postMessageForm) {
		PostMessage postMessage = new PostMessage();
		User user = new User();
		user.setId(postMessageForm.getUserId());
		postMessage.setUser(user);
		postMessage.setMessageContent(postMessageForm.getMessageContent());
		postMessage.setMessageLongitude(postMessageForm.getLongitude());
		postMessage.setMessageLatitude(postMessageForm.getLatitude());
		postMessage.setMessageAddress(postMessageForm.getLocation());
		if(postMessageService.insert(postMessage)){
			postMessageService.insertPostMessageImage(postMessage.getId(),Arrays.asList(postMessageForm.getPaths()));
		}
		Object obj =  JedisUtils.getObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE);
		if(obj != null){
			if(obj instanceof ArrayList){
				List<Object> list = (List<Object>) obj;
				if(list.size() > 0){
					Object o = list.get(0);
					if(o instanceof PostMessage){
						//方式1：
						List<PostMessage> listPost = (List<PostMessage>) obj;
						List<PostMessage> listTemp = new ArrayList<PostMessage>();
						listTemp.add(postMessage);
						listTemp.addAll(listPost);
						listTemp.remove(listTemp.size()-1);
						JedisUtils.setObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE, listTemp);
						//方式2：
						//从左边入队一个发布信息
						//JedisUtils.lpush(RedisPrefix.POSTMESSAGE_TOPTEN_PRE, postMessage);
						//从右边出对一个发布信息
						//JedisUtils.rpop(RedisPrefix.POSTMESSAGE_TOPTEN_PRE);
					}
				}
			}
		}
		// 维护缓存中发布信息总数(所有的总数，某个用户的总数)
		//单个用户的
		User u = userService.queryById(postMessageForm.getUserId());
		if(u == null){
			StatusCode statusCode = StatusCode.ERROR;
			return new ResponseResult(statusCode.getCode(),statusCode.getMessage());
		}
		Object oU = JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName());
		Integer countU = 0;
		if(oU != null && oU instanceof Integer){
			countU = (Integer) oU;
		}
		//将缓存中当前用户的发布数加一
		JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_COUNT+u.getUserName(), countU++);
		
		
		//所有用户的
		Integer countA = 0;
		Object oA = JedisUtils.getObject(RedisPrefix.POSTMESSAGE_COUNT);
		if(oA != null && oA instanceof Integer){
			countA = (Integer) oA;
		}
		//将缓存中所有用户发布数加一
		JedisUtils.setObject(RedisPrefix.POSTMESSAGE_COUNT, countA++);
		return ResponseResult.ok();
	}**/
	
	/***
	 * 	发布消息
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "postMessage.action" , method = RequestMethod.POST)
	public ResponseResult postMessage(PostMessageForm postMessageForm,String imagePaths) {
		
		
		System.out.println(imagePaths);
		Object obj = JSONObject.parse(imagePaths);
		List<JSONObject> imagePath = (List<JSONObject>) obj;
		List<ImagePath> images = new ArrayList<ImagePath>();
		for(JSONObject jsonObject : imagePath){
			images.add(JSONObject.toJavaObject((JSON)jsonObject, ImagePath.class));
		}
		System.out.println(images);
		/*Map<String,String> map  = (HashMap<String,String>)obj;
		Set<Entry<String, String>> entrySet = map.entrySet();
		Set<String> keySet = map.keySet();
		for(Entry<String,String> entry : entrySet){
			System.out.println(entry);
			ImagePath image = new ImagePath();
			Class<ImagePath> cls = (Class<ImagePath>) image.getClass();
			Field[] filelds = cls.getFields();
			try {
				for(Field field : filelds){
					for(String key : keySet){
						if(field.getName().equals(key)){
							field.set(image, entry.getValue());
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("iamge:"+image);
		}*/
		
		// 第一步:维护基本信息
		PostMessage postMessage = new PostMessage();
		User user = new User();
		user.setId(postMessageForm.getUserId());
		postMessage.setUser(user);
		postMessage.setMessageContent(postMessageForm.getMessageContent());
		postMessage.setMessageLongitude(postMessageForm.getLongitude());
		postMessage.setMessageLatitude(postMessageForm.getLatitude());
		postMessage.setMessageAddress(postMessageForm.getLocation());
		
		// 第二步:保存
		postMessageService.postMessage(postMessage ,images);
		
		return ResponseResult.ok();
	}

	/**
	 * 根据id删除（逻辑删除）
	 * @param id 传入一个id
	 * @return 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String postMessageId){
		postMessageService.deleteById(postMessageId);
		
		//维护缓存中发布信息
		//所有的发布信息
		/*Object oA = JedisUtils.getObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE);
		if(oA != null){
			if(oA instanceof List){
				List<Object> list = (List<Object>) oA;
				if(list.size() > 0){
					Object o = list.get(0);
					if(o instanceof PostMessage){
						List<PostMessage> listMessage = (List<PostMessage>) oA;
						PostMessage temp = null;
						for(PostMessage pm : listMessage){
							if(id.equals(pm.getId())){
								temp = pm;
							}
						}
						//删除缓存中当前删除的的
						listMessage.remove(temp);
						//向缓存中追加一条数据库中除了剩下的最新的
							//获取当前list中最后一个发布信息
						PostMessage lastOne = listMessage.get(listMessage.size()-1);
						//获取除了缓存中九条最新的发布信息外最新的发布信息
						PostMessage newOne = postMessageService.queryTheNewestBeforeAssignTime(null,lastOne.getCreatedTime());
						//将获取的最新的追加到缓存的列表中
						listMessage.add(newOne);
						JedisUtils.setObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE,listMessage);
					}
				}
			}
		}*/
		//当前用户的发布信息
		return ResponseResult.ok();
	}
	
	public ResponseResult deleteByIdReal(String id){
		postMessageService.deleteByIdReal(id);
		return ResponseResult.ok();
	}
	
	/**
	 * 信息发布首页 查询带分页信息
	 */
	@RequestMapping(value = "showHomeAllList.action", method = RequestMethod.POST)
	public ResponseResult showHome(Long pageNum, Long pageSize) {
		
//		Object obj =  JedisUtils.getObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE);
//		if(obj != null){
//			if(obj instanceof ArrayList){
//				List<Object> list = (List<Object>) obj;
//				if(list.size() > 0){
//					Object o = list.get(0);
//					if(o instanceof PostMessage){
//						return ResponseResult.ok((List<PostMessage>)obj);
//					}
//				}
//			}
//		}
		QueryHelper queryHelper = new QueryHelper();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("tpm.created_time", "desc");
		// 创建一个查询对象
		queryHelper.addCondition(1, " tpm.deleted = %d", false);
		PageView<PostMessage> pageView = postMessageService.queryPageData(pageNum, pageSize, queryHelper.getWhereSQL(),
				queryHelper.getWhereParams(), orderby);
		if (pageView == null) {
			return ResponseResult.notFound();
		}
//		if (pageNum == 1) {
//			if (pageView.getDatas() != null && pageView.getDatas().size() > 0) {
//				JedisUtils.setObject(RedisPrefix.POSTMESSAGE_TOPTEN_PRE, pageView.getDatas());
//			}
//		}
		return ResponseResult.ok(pageView);
	}

	/**
	 * 根据用户id查询用户发布的信息
	 */
	@RequestMapping(value = "getUserPostMessage.action", method = RequestMethod.POST)
	public ResponseResult getUserPostMessage(Long pageSize,Long pageNum,String userId) {
//		Object obj =  JedisUtils.getObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE);
//		if(obj != null){
//			if(obj instanceof ArrayList){
//				List<Object> list = (List<Object>) obj;
//				if(list.size() > 0){
//					Object o = list.get(0);
//					if(o instanceof PostMessage){
//						return ResponseResult.ok((List<PostMessage>)obj);
//					}
//				}
//			}
//		}
		if (StringUtils.isNotBlank(userId)) {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("tpm.created_time", "desc"
					);
			
			QueryHelper queryHelper=new QueryHelper();
			queryHelper.addCondition(userId,"tpm.user_id=%d", true)
			.addCondition(1, " tpm.deleted = %d", false);
			
			PageView<PostMessage>pageView = postMessageService.queryPageData(pageNum, pageSize, queryHelper.getWhereSQL(), 
					queryHelper.getWhereParams(), orderby);
			if (pageView == null) {
				return ResponseResult.notFound();
			}
//			if (pageNum == 1) {
//				if (pageView.getDatas() != null && pageView.getDatas().size() > 0) {
//					JedisUtils.setObject(RedisPrefix.USER_POSTMESSAGE_TOPTEN_PRE, pageView.getDatas());
//				}
//			}
			return ResponseResult.ok(pageView);
		}
		throw new BizException(StatusCode.USER_ID_NULL);
	}

	/**
	 * 上传发布信息的图片
	 * 
	 * @param request
	 *            传入一个HttpServletRequest 
	 * @return 返回图片路径
	 */
	@RequestMapping("uploadImage.action")
	public ResponseResult uploadImage(HttpServletRequest request) {
		List<NarrowImage> narrowImages = UploadUtil.uploadImages(propertiesService.getWindowsRootPath(), "postMessage",
				propertiesService.getCurrentServerForImage(), request);
		
		return ResponseResult.ok(narrowImages);
	}
	
	
	/**
	 * 根据发布信息id查询单个发布信息
	 * */
	@RequestMapping(value="getPostMessageById.action",method=RequestMethod.GET)
	public ResponseResult getPostMessageById(String postMessageId){
		
		//传入的id不能为空
		if(StringUtils.isNotBlank(postMessageId)){
			PostMessage postMessage=postMessageService.queryById(postMessageId);
			if(postMessage==null){
				return ResponseResult.notFound();
			}
			return ResponseResult.ok(postMessage);
			
		}
		//返回的错误信息
		StatusCode statusCode=StatusCode.POST_MESSAGE_ID_NULL;
		return new ResponseResult(statusCode.getCode(),statusCode.getMessage());
	}
	
	/**
	 * 根据输入内容模糊查询
	 * */
	@RequestMapping(value="queryByContent.action",method=RequestMethod.POST)
	public ResponseResult queryByContent(String messageCont,Long pageSize,Long pageNum,String userId){
		
		//排序规则
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("tpm.created_time", "desc");
		
		QueryHelper queryHelper=new QueryHelper();
		queryHelper.addCondition(1, "tpm.deleted=%d", false);
		
		if(StringUtils.isNotBlank(messageCont)){
			queryHelper.addCondition("%"+messageCont+"%", "tpm.message_content like '%s'", false);
		}
		if(StringUtils.isNotBlank(userId)){
			queryHelper.addCondition(userId,"tpm.user_id= %d" , true);
		}
		//根据条件查询的分页数据
		PageView<PostMessage>pageView=postMessageService.queryPageData(pageNum, pageSize, queryHelper.getWhereSQL(),
				queryHelper.getWhereParams(), orderby);
		if(pageView==null){
			return ResponseResult.notFound();
		}
		
		return ResponseResult.ok(pageView);
	}
	
	
}
