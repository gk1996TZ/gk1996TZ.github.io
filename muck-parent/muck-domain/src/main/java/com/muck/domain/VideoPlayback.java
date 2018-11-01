package com.muck.domain;

/**
* @Description: 视频回放实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午2:03:11
 */
public class VideoPlayback extends BaseEntity{

	// 视频回放保存路径
	private String videoPlaybackPath;

	public String getVideoPlaybackPath() {
		return videoPlaybackPath;
	}

	public void setVideoPlaybackPath(String videoPlaybackPath) {
		this.videoPlaybackPath = videoPlaybackPath;
	}
}
