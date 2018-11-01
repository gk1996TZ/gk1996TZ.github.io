package com.litang.utils;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 上传文件工具类
 */
public class UploadUtil {
	private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);

	/**
	 * 根据路径和servlet请求中的文件上传请求上传多个文件 
	 * @param resources 本地存放资源文件的目录
	 * @param subpath
	 *            传入上传的文件需要放入的文件夹目录，该目录为服务器系统目录，
	 *            同时用来区分图片的类型，例如:传入轮播图时path值为shiffing，传入用户头像时path值为userhead
	 * @param imageServer 图片服务器ip加端口
	 * @param request
	 *            传入一个request请求
	 * @return 返回需要存放到数据库的文件路径列表
	 */
	public synchronized static List<NarrowImage> uploadImages(String resources,String subPath,String imageServer, HttpServletRequest request) {
		List<NarrowImage> paths = new ArrayList<NarrowImage>();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		multipartResolver.setMaxUploadSize(1024 * 1024);
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			String tempPath = subPath;
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile mul = multiRequest.getFile(iter.next());
				// 存放图片的相对路径
				String image_path = "";
				if (mul != null && !mul.getOriginalFilename().trim().equals("")) {
					if (subPath != null && !subPath.isEmpty()) {
						// 如果参数传入有图片子目录，则将图片存放到images的相应子目录下
						image_path = File.separator + "images" + File.separator + generateDirByHashCode(subPath,File.separator+ System.currentTimeMillis()+"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())))  ;
						/*thum_image_path = File.separator + "images" + File.separator + generateDirByHashCode(subPath,File.separator
								+ "thum"+System.currentTimeMillis()+"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())))  ;*/
					} else {
						// 如果参数传入没有存放的路径，则将图片存放到images目录下
						image_path = File.separator + generateDirByHashCode("images",File.separator + System.currentTimeMillis()+"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())));
						/*thum_image_path = File.separator + generateDirByHashCode("images",File.separator + "thum"+System.currentTimeMillis()+"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())));*/
					}
					// 判断当前的服务器系统类型，给path前面追加服务器本地的存放路径
					if (OSUtil.isWindows()) {
						if(StringUtils.isBlank(resources)){
							resources = "C:"+File.separator;
						}
						// 如果服务器是windows操作系统
						subPath = resources + image_path;
					} else if (OSUtil.isLinux()) {
						if(StringUtils.isBlank(resources)){
							resources = File.separator+"usr"+File.separator+"data";
						}
						// 如果服务器是linux操作系统
						subPath = resources + image_path;
					}
					try {
						File file = new File(subPath);
					/*	File thum_file = new File(resources +thum_image_path);*/
						FileNameMap fileNameMap = URLConnection.getFileNameMap();
						String type = fileNameMap.getContentTypeFor(subPath);
						if ("image/jpg".equals(type) | "image/jpeg".equals(type) | "image/gif".equals(type)
								| "image/png".equals(type)) {
							// 如果该file是文件，则将获取到的文件输入流放到指定的目录
							// mul.transferTo(file);
							
							FileUtils.copyInputStreamToFile(mul.getInputStream(), file);
						/*	FileUtils.copyInputStreamToFile(mul.getInputStream(), thum_file);*/
							/*String thum_subPath = resources +thum_image_path;*/
							//storeThumbnail(subPath, subPath);
							//image_path = thumPath.replace(resources, "");
							
							NarrowImage narrowImage = new NarrowImage();
							String fileName = mul.getOriginalFilename().trim();
							narrowImage.setUuid(UUID.randomUUID().toString().replaceAll("-", "")+fileName.substring(fileName.lastIndexOf(".")));
							String srcPath = "http://" + imageServer +image_path;
							StringBuilder sb = new StringBuilder("");
							sb.append(ImageUtil.thumbnailImage(subPath,100, 150).replaceAll(resources, ""));
							sb.delete(0,resources.length()+2);
							if(sb.length() > 0){
								sb.delete(0,resources.length());
							}
							image_path = sb.toString();
							String thumPath = "http://" + imageServer+image_path;
							narrowImage.setSrcPath(srcPath);
							narrowImage.setThumPath(thumPath);
							paths.add(narrowImage);
						}
					} catch (IOException e) {
						logger.error("图片上传路径不存在！");
					}
					subPath = tempPath;
				}
			}
		}
		return paths;
	}
	
	/*public synchronized static List<NarrowImage> uploadImages(String resources,String subPath,String imageServer, HttpServletRequest request,boolean thum) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		multipartResolver.setMaxUploadSize(1024 * 1024);
		// 判断 request 是否有文件上传,即多部分请求
		List<NarrowImage> datas = new ArrayList<NarrowImage>();
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			String tempPath = subPath;
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile mul = multiRequest.getFile(iter.next());
				// 存放图片的相对路径
				String save_dir = "";
				String fileName = UUID.randomUUID().toString().replaceAll("-", "");
				if (mul != null && !mul.getOriginalFilename().trim().equals("")) {
					if (subPath != null && !subPath.isEmpty()) {
						// 如果参数传入有图片子目录，则将图片存放到images的相应子目录下
//						image_path = File.separator + "images" + File.separator + generateDirByHashCode(subPath,File.separator
//								+ fileName +"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())))  ;
						save_dir = File.separator + "images" + File.separator + generateDir(subPath, fileName);
					} else {
						// 如果参数传入没有存放的路径，则将图片存放到images目录下
						// image_path = File.separator + generateDirByHashCode("images",File.separator + fileName+"."+(FilenameUtils.getExtension(mul.getOriginalFilename().trim())));
						save_dir = File.separator + generateDir("images", fileName);
					}
					// 判断当前的服务器系统类型，给path前面追加服务器本地的存放路径
					if (OSUtil.isWindows()) {
						if(StringUtils.isBlank(resources)){
							resources = "C:"+File.separator;
						}
						// 如果服务器是windows操作系统
						subPath = resources + save_dir;
					} else if (OSUtil.isLinux()) {
						if(StringUtils.isBlank(resources)){
							resources = File.separator+"usr"+File.separator+"data";
						}
						// 如果服务器是linux操作系统
						subPath = resources + save_dir;
					}
					try {
						String saveFileName = fileName + "."+ FilenameUtils.getExtension(mul.getOriginalFilename().trim());
						String saveThumFileName = fileName + "_sl" + "."+ FilenameUtils.getExtension(mul.getOriginalFilename().trim());
						File file = new File(subPath,saveFileName);
						FileNameMap fileNameMap = URLConnection.getFileNameMap();
						String type = fileNameMap.getContentTypeFor(saveFileName);
						if ("image/jpg".equals(type) | "image/jpeg".equals(type) | "image/gif".equals(type)
								| "image/png".equals(type)) {
							FileUtils.copyInputStreamToFile(mul.getInputStream(), file);
							
							NarrowImage narrowImage = new NarrowImage();
							// subPath : 源文件全路径
							BufferedImage bufferImage = narrowImage.zoomImage(subPath +File.separator+ saveFileName);
							// destPath : 缩略图的路径
							narrowImage.writeHighQuality(bufferImage, subPath +File.separator+ saveThumFileName);
							
							narrowImage.setUuid(fileName);
							narrowImage.setSrcPath("http://" + imageServer + save_dir  +File.separator+ saveFileName);
							narrowImage.setThumPath("http://" + imageServer + save_dir  +File.separator+ saveThumFileName);
							
							datas.add(narrowImage);
						}
					} catch (IOException e) {
						logger.error("图片上传路径不存在！");
					}
					subPath = tempPath;
				}
			}
		}
		return datas;
	}*/
	
	/**
	 * 	生成目录
	 * 		根据一个文件名来去计算这个文件名的hash值(根据一个字符串来去计算一个字符串的hash值)
	 * 
	 * 		1、通过文件名计算hashcode
	 * 		2、我拿hashcode值的低4位作为一级目录
	 * 		3、我拿hashcode值的5-8位作为二级目录
	*/
	public static String generateDirByHashCode(String filePathDir , String fileName){
		
		int hashCode = fileName.hashCode();
		
		int dirLevel1 = hashCode & 0X0F;
		int dirLevel2 = (hashCode & 0XF0) >> 4;
		
		String realDir = filePathDir + File.separator + dirLevel1 + File.separator + dirLevel2;
		File file = new File(realDir);
		if(!file.exists()){
			file.setWritable(true, false);
			file.mkdirs();
		}
		realDir=realDir+fileName;
		return realDir;
	}
	
	public static String generateDir(String filePathDir , String fileName){
		
		int hashCode = fileName.hashCode();
		
		int dirLevel1 = hashCode & 0X0F;
		int dirLevel2 = (hashCode & 0XF0) >> 4;
		
		String realDir = filePathDir + File.separator + dirLevel1 + File.separator + dirLevel2;
		File file = new File(realDir);
		if(!file.exists()){
			file.mkdirs();
		}
		return realDir;
	}
	
	/**
	* @param standardImgPath 原图片path
	* @param thumName 缩略图path
	*/
	/*private static String storeThumbnail(String standardImgPath, String thumName) {
	    File file = new File(standardImgPath);
	    if(file!=null&&file.isFile()){
	        try {
	            File outFIle = new File(thumName);
	           // OutputStream os=new FileOutputStream(outFIle);
	            if(!outFIle.exists()){
	            	outFIle.setWritable(true, false);
	            	outFIle.createNewFile();
	            }
	            Thumbnails.of(file).scale(0.2f).toFile(outFIle);
	          //  os.close();
	            return outFIle.getName();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}*/
	
}
