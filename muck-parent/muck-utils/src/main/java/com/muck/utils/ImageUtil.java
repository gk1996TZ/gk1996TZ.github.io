package com.muck.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;


public class ImageUtil {

	private static final int WIDTH = 350;
	private static final int HEIGHT = 100;

	private static Random random = new Random();

	public static int r(int min, int max) {
		int num = 0;
		num = random.nextInt(max - min) + min;
		return num;
	}

	public static void write2Pic(String sourceByte,String filePath) {

		FileOutputStream output;
		try{
			File file = new File(filePath);
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				if(!parentFile.mkdirs()){
					return;
				}
			}
			if(!file.exists()){
				if(!file.createNewFile()){
					return ;
				}
			}
			if(!file.isFile()){
				return;
			}
			output = new FileOutputStream(file);
			output.write(Base64.decodeBase64(sourceByte));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出指定文本图片流
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public static String outputImage(String[] verifyCodes) throws IOException {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color[] colors = new Color[5];
		Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
				Color.ORANGE, Color.PINK, Color.YELLOW };
		float[] fractions = new float[colors.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
			fractions[i] = rand.nextFloat();
		}
		Arrays.sort(fractions);

		g2.setColor(Color.GRAY);// 设置边框色
		g2.fillRect(0, 0, WIDTH, HEIGHT);

		Color c = getRandColor(200, 255);
		g2.setColor(c);// 设置背景色
		g2.fillRect(0, 2, WIDTH, HEIGHT - 4);

		// 绘制干扰线
		/*Random random = new Random();
		g2.setColor(getRandColor(160, 200));// 设置线条的颜色
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(WIDTH - 1);
			int y = random.nextInt(HEIGHT - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g2.drawLine(x, y, x + xl + 40, y + yl + 20);
		}*/

		// 添加噪点
		/*float yawpRate = 0.05f;// 噪声率
		int area = (int) (yawpRate * WIDTH * HEIGHT);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}*/

		shear(g2, WIDTH, HEIGHT, c);// 使图片扭曲

		g2.setColor(getRandColor(100, 160));
		int fontSize = HEIGHT;
		Font font = new Font("宋体", Font.ITALIC, 20);
		g2.setFont(font);

		FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);

		int y = fm.getHeight();
		int h = fm.getHeight();
		for (int i = 0; i < verifyCodes.length; i++) {
			g2.drawString(verifyCodes[i], (int) (h * (0.8)), (int) (i * y * (0.8)) + 24);
		}
		g2.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", out);
		String bytePic = Base64.encodeBase64String(out.toByteArray());// Base64.encodeBase64String(out.toByteArray());
		System.out.println(bytePic);
		return bytePic;
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	private static void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private static void shearX(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private static void shearY(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(40) + 50; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}
}
