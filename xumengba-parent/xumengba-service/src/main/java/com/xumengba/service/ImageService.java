package com.xumengba.service;

import com.xumengba.domain.Image;

/***
 * 	图片Service	
*/
public interface ImageService extends BaseService<Image>{

	/**
	 * @Description: 根据图片排序序号获取图片的id
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:43:36
	 * @param: imageSeq 传入图片的排序序号
	 * @return: Long 返回图片id
	 */
	public Long getIdBySeq(Integer imageSeq);
	/**
	 * @Description: 修改图片排序序号
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:44:12
	 * @param: id 传入一个图片id
	 * @param: imageSeq 传入图片排序序号
	 */
	public void changeSeqById(String id,Integer imageSeq);
}
