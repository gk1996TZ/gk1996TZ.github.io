package com.litang.utils;
/*package com.litang.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public  class NarrowImage {

	// 图片uuid
	private String uuid;

	// 图片原图路径
	private String srcPath;

	// 图片的缩略图路径
	private String thumPath;
	
	

	public NarrowImage() {
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getThumPath() {
		return thumPath;
	}

	public void setThumPath(String thumPath) {
		this.thumPath = thumPath;
	}

	*//**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 *//*
	public BufferedImage zoomImage(String src) {

		BufferedImage result = null;

		try {
			File srcfile = new File(src);

			BufferedImage im = ImageIO.read(srcfile);

			 原始图像的宽度和高度 
			int width = im.getWidth();
			int height = im.getHeight();

			// 压缩计算
			float resizeTimes = 0.5f;  这个参数是要转化成的倍数,如果是1就是转化成1倍 

			 调整后的图片的宽度和高度 
			int toWidth = (int) (width * resizeTimes);
			int toHeight = (int) (height * resizeTimes);

			 新生成结果图片 
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);

		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;

	}

	public void writeHighQuality(BufferedImage im, String destPath) {
		try {
			FileOutputStream newimage = new FileOutputStream(destPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(im);
			param.setQuality(0.9f, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(im, param);
			 近JPEG编码 
			newimage.close();

			int height = im.getHeight();
			int width = im.getWidth();
			if(height >= width){
				height = width;
			}
			if(height < width){
				width = height;
			}
			cut(new File(destPath), new File(destPath), 0,0,height,width);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeHighQuality(BufferedImage im, String destPath) {
		try {
			FileOutputStream newimage = new FileOutputStream(destPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(im);
			param.setQuality(0.9f, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(im, param);
			 近JPEG编码 
			newimage.close();

			int height = im.getHeight();
			int width = im.getWidth();
			int x = 0;
			int y = 0;
			if (height > 100) {
				x = (height - 100) / 2;
			}
			if (width > 100) {
				y = (width - 100) / 2;
			}
			cut(new File(destPath), new File(destPath), y, x, 100, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cut(File srcfile, File outfile, int x, int y, int width, int height) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 读取图片文件
			is = new FileInputStream(srcfile);

			
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 
			reader.setInput(iis, true);

			
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 
			ImageReadParam param = reader.getDefaultReadParam();

			
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, "jpg", outfile);
		} finally {
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
	}
	

	public static void main(String[] args) throws Exception{

		String inputFoler = "d:/111.png";
		 这儿填写你存放要缩小图片的文件夹全地址 
		String outputFolder = "d:/444.jpg";
		 这儿填写你转化后的图片存放的文件夹 
		NarrowImage narrowImage = new NarrowImage();
		narrowImage.writeHighQuality(narrowImage.zoomImage(inputFoler), outputFolder);
	}
	
	
}
*/