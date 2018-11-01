package com.litang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.Image;
import com.litang.mapper.BaseMapper;
import com.litang.mapper.ImageMapper;
import com.litang.service.ImageService;

/**
 * 图片Service实现
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService{

	@Autowired
	private ImageMapper imageMapper;
	@Override
	protected BaseMapper<Image> getMapper() {
		return imageMapper;
	}
	@Override
	protected String getFields() {
		return " * ";
	}
}
