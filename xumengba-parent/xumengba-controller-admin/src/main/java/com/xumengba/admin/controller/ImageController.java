package com.xumengba.admin.controller;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Category;
import com.xumengba.domain.Image;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.query.ImageQueryForm;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.CategoryService;
import com.xumengba.service.ImageService;

/**
 * 图片controller
 */
@RestController("AdminImageController")
@RequestMapping("/admin/image")
public class ImageController extends BaseController{

	@Autowired
	private ImageService imageService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * @Description: 根据id查询图片详情
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午2:26:34
	 * @param: id 传入一个图片id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String id){
		return ResponseResult.ok(imageService.queryById(id));
	}
	/**
	 * @Description: 添加图片的操作
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:36:56
	 * @param: image 传入一个图片对象
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("save.action")
	public ResponseResult save(Image image){
		imageService.save(image);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id真实删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:36:30
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String id){
		imageService.deleteByIdReal(id);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id逻辑删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:36:03
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String id){
		imageService.deleteById(id);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id修改图片信息
	 * @author:
	 * @date: 2018年10月10日 下午3:35:14
	 * @param: image 传入一个图片
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("editById.action")
	public ResponseResult editById(Image image){
		Date date = new Date();
		image.setUpdatedTime(date);
		imageService.editById(image);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据条件查询图片列表
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:34:43
	 * @param: imageQueryForm 传入查询条件
	 * @return: ResponseResult 返回图片列表
	 */
	@RequestMapping("getImages.action")
	public ResponseResult getImages(ImageQueryForm imageQueryForm){
		QueryHelper queryHelper = new QueryHelper();
		String categoryName = imageQueryForm.getCategoryName();
		if(!StringUtils.isBlank(categoryName)){
			Category category = categoryService.queryByName(categoryName);
			if(category != null){
				queryHelper.addCondition(category.getId(),"i.category_id = %d " , true);
			}
		}
		queryHelper.addCondition(imageQueryForm.getCategoryId(),"i.category_id = %d " , true);
		if(!StringUtils.isBlank(imageQueryForm.getImageTitle())){
			queryHelper.addCondition("%"+imageQueryForm.getImageTitle()+"%", "i.image_title like '%s'", false);
		}
		queryHelper.addCondition(imageQueryForm.getImageSeq(), "i.image_seq = %d", false);
		queryHelper.addCondition(1, "i.deleted = %d", false);
		return ResponseResult.ok(imageService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}
	/**
	 * @Description: 获取图片分页数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:34:12
	 * @param: imageQueryForm 传入查询条件
	 * @return: ResponseResult 返回分页数据
	 */
	@RequestMapping("getImagePage.action")
	public ResponseResult getImagePage(ImageQueryForm imageQueryForm){
		// 创建分页对象
		PageView<Image> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("i.created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		String categoryName = imageQueryForm.getCategoryName();
		if(!StringUtils.isBlank(categoryName)){
			Category category = categoryService.queryByName(categoryName);
			if(category != null){
				queryHelper.addCondition(category.getId(),"i.category_id = %d " , true);
			}
		}
		queryHelper.addCondition(imageQueryForm.getCategoryId(),"i.category_id = %d " , true);
		if(!StringUtils.isBlank(imageQueryForm.getImageTitle())){
			queryHelper.addCondition("%"+imageQueryForm.getImageTitle()+"%", "i.image_title like '%s'", false);
		}
		queryHelper.addCondition(imageQueryForm.getImageSeq(), "i.image_seq = %d", false);
		queryHelper.addCondition(1, "i.deleted = %d", false);
		pageView = imageService.queryPageData(imageQueryForm.getPageNum(), imageQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}
	
	/**
	 * @Description: 修改图片序号
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午3:33:04
	 * @param: imageQueryForm 传入一个图片请求实体类
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("changeSeqById.action")
	public ResponseResult changeSeq(ImageQueryForm imageQueryForm){

		imageService.changeSeqById(imageQueryForm.getId(), imageQueryForm.getImageSeq());
		/*// 创建分页对象
		PageView<Image> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(imageQueryForm.getCategoryId(),"category_id" , true);
		queryHelper.addCondition(1, "deleted = %d", false);
		
		pageView = imageService.queryPageData(imageQueryForm.getPageNum(), imageQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);*/
		return ResponseResult.ok();
	}
	
}
