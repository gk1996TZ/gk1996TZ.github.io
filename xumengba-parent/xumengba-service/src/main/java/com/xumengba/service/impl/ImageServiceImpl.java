package com.xumengba.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.Article;
import com.xumengba.domain.Image;
import com.xumengba.exception.BizException;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.mapper.ImageMapper;
import com.xumengba.page.PageView;
import com.xumengba.response.StatusCode;
import com.xumengba.service.ImageService;

/**
 * 	图片Service
*/
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService {

	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	public PageView<Image> queryPageData(Long currentPageNum, Long pageSize, String whereSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";
		// 用来查询数据总数的sql
		String sql = "select count(id) from t_image i where 1 = 1 " + whereSQL;
		// 用来查询数据的sql
		String selectSQL = "select i.*,c.category_name "
				+ " from t_image i left join t_category c on i.category_id = c.id "
				+ " where 1 = 1 " + whereSQL + limit;
		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = imageMapper.selectTotalRecoreds(sql);
		PageView<Image> pageView = new PageView<>(count, currentPageNum, pageSize);
		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		pageView.setDatas(imageMapper.selectPageData(selectSQL));
		return pageView;
	}
	@Override
	public List<Image> queryData(String whereSQL, List<Object> whereParams) {
		// 第一步、获取where条件
		String where = StringUtils.isBlank(whereSQL) ? " where 1 = 1 " : " where " + whereSQL;
		// 第二步、拼接查询sql
		String selectSQL = "select i.*,c.category_name "
				+ " from t_image i left join t_category c on i.category_id = c.id "
				+ where;
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}

	@Override
	public Long getIdBySeq(Integer imageSeq) {
		return imageMapper.getIdBySeq(imageSeq);
	}
	@Override
	public void changeSeqById(String id, Integer imageSeqNew) {
		if(imageSeqNew == null){
			throw new BizException(StatusCode.CHANGE_IMAGE_SEQ_BLANK);
		}
		//根据id查询图片对象
		Image image = queryById(id);
		// 获取排序之前的排序序号
		Integer imageSeq = image.getImageSeq();
		if(imageSeqNew  > imageSeq){
			//如果执行的是向下移动的操作
			//根据排序序号获取下一张图片的id
			Long imageId = getIdBySeq(imageSeq+1);
			//将下一张图片的排序序号减一
			imageMapper.changeSeqById(imageId, imageSeq);
		}else if (imageSeqNew < imageSeq){
			//如果执行的是向上移动的操作
			//根据排序序号获取上一张图片的id
			if(imageSeq-1 > 0){
				Long imageId = getIdBySeq(imageSeq-1);
				//将上一张图片的排序序号加一
				imageMapper.changeSeqById(imageId, imageSeq);
			}
		}
		//修改当前图片的排序序号
		if(imageSeqNew > 0){
			imageMapper.changeSeqById(Long.parseLong(id), imageSeqNew);
		}
	}
	@Override
	protected BaseMapper<Image> getMapper() {
		return this.imageMapper;
	}
	@Override
	protected String getFields() {
		return " * ";
	}
}
