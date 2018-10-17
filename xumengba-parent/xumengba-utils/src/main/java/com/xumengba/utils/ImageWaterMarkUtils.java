package com.xumengba.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * @Description: 图片水印工具类
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月12日 下午2:40:13
 */
public final class ImageWaterMarkUtils {

	// 字体
	private static final String FONT_FAMILY = "微软雅黑";
	
	// 字体加粗
	private static final int FONT_STYLE = Font.BOLD;
	
	// 字体大小
	private static final int FONT_SIZE = 24;// 字体大小
	
	
	private ImageWaterMarkUtils() {

	}

	/**
	 * @Description: 为文件添加水印效果
	 * @param: waterMarkContent
	 *             : 向图片中添加的内容 srcFile : 源文件 targerPath : 保存图片的目的路径 degree :
	 *             水印文字的旋转角度
	 * @return：无 @throws：无
	 * @author: 展昭
	 * @date: 2018年4月12日 下午3:13:11
	
	public static void addWaterMark(String waterMarkContent, File srcFile, String targerPath, Integer degree) {
		OutputStream os = null;
		try {
			// 第一步: 根据srcImgPath图片路径得到文件,并将此文件转化为图片
			Image srcImg = ImageIO.read(srcFile);

			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);

			BufferedImage buffImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

			// 第二步: 创建一支画笔
			Graphics2D g = buffImg.createGraphics();

			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			g.drawImage(srcImg.getScaledInstance(srcImgWidth, srcImgHeight, Image.SCALE_SMOOTH), 0, 0, null);

			g.setFont(new Font(FONT_FAMILY, FONT_STYLE, FONT_SIZE));

			// 第三步: 设置水印
			// 3.1 设置坐标
			int x = srcImgWidth - 4 * getWatermarkLength(waterMarkContent, g);
			int y = srcImgHeight - 4 * getWatermarkLength(waterMarkContent, g);

			// 3.2 设置水印旋转
			if (degree != null) {
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			// 3.3 画出水印
			g.drawString(waterMarkContent == null ? "" : waterMarkContent, x, y);
			g.dispose();

			// 第四步: 输出图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "png", os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	 */
	
	
	public static void addWaterMark(String waterMarkContent, File srcFile, String targerPath, Integer degree) {
		OutputStream os = null;
		try {
			// 第一步: 根据srcImgPath图片路径得到文件,并将此文件转化为图片
			Image srcImg = ImageIO.read(srcFile);

			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);

			BufferedImage buffImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

			// 第二步: 创建一支画笔
			Graphics2D g = buffImg.createGraphics();

			// 设置对线段的锯齿状边缘处理(当设置的字体过大的时候,会出现锯齿)
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(Color.RED);
			
			Font font = new Font(FONT_FAMILY, FONT_STYLE, FONT_SIZE);
			g.setFont(font);

			// 第三步: 设置水印
			// 3.1 设置坐标
			FontMetrics metrics = new FontMetrics(font){};
	        Rectangle2D bounds = metrics.getStringBounds(waterMarkContent, null);
	        int textHeight = (int) bounds.getHeight();
	        int left = 0;
	        int top = textHeight;

			// 3.2 设置水印旋转
			if (degree != null) {
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}
			// 3.3 画出水印
			g.drawString(waterMarkContent == null ? "" : waterMarkContent, left, top);
			g.dispose();

			// 第四步: 输出图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "png", os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
}
