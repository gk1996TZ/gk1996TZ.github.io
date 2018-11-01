package com.muck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Image;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ImageMapper;
import com.muck.service.ImageService;

/**
 * 	图片Service
*/
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService {

	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	protected BaseMapper<Image> getMapper() {
		return this.imageMapper;
	}

	@Override
	protected String getFields() {
		return null;
	}
}
