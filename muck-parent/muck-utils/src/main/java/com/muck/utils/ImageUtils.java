package com.muck.utils;

import java.awt.AlphaComposite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 生成水印图片
 * @author lyt
 * 2017年7月19日
 */
public final class ImageUtils {
	
 public static void main(String[] args) throws IOException {
	 createWaterMark(new String[]{"文字水印效果","文字水印"},"C:\\水印.png");
 }
	/**
	 * @param content
	 * @throws IOException
	 */
	public static void createWaterMark(String [] content,String path) throws IOException{
		Integer width = 620;
		Integer height = 450;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
		String fontType = "宋体";
		Integer fontStyle = Font.PLAIN;
		Integer fontSize = 30;
		Font font = new Font(fontType, fontStyle, fontSize);
		Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 50)); //设置字体颜色和透明度
		g2d.setStroke(new BasicStroke(1)); // 设置字体
		g2d.setFont(font); // 设置字体类型  加粗 大小
		g2d.rotate(Math.toRadians(-10),(double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度
		
		FontRenderContext context = g2d.getFontRenderContext();
		double x = 0;
		double y = 0;
		double ascent = 0;
		double baseY = 0;
		if(content == null){
			System.out.println("向图片上追加水印时，传入的水印为空");
			return;
		}
		for(String con : content){
			Rectangle2D bounds = font.getStringBounds(con, context);
			x = (width - bounds.getWidth()) / 2;
			y += (height - bounds.getHeight()) / 4;
			ascent = -bounds.getY();
			baseY = y + ascent;
			// 写入水印文字原定高度过小，所以累计写水印，增加高度
			g2d.drawString(con, (int)x, (int)baseY);
		}
		// 设置透明度
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		// 释放对象
		g2d.dispose();
		File file = new File (path);
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			if(!parentFile.mkdirs()){
			}
		}
		ImageIO.write(image, "png", file);
	}
}