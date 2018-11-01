package com.muck.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

/**
 * @Description: 操作压缩文件的工具类
 * @Version: v1.0.0
 * @Author: 朱俊亮
 * @Date: 2018年8月10日 下午12:10:50
 */
public class CompressedFilesUtils {

	private static Logger logger = LoggerFactory.getLogger(CompressedFilesUtils.class);

	/**
	 * @throws IOException
	 * @Description: 执行解压操作
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午3:29:28
	 * @Param: srcFile 传入一个压缩文件的目录
	 * @Return: String 返回解压后文件存放的父目录
	 */
	public static String extract(String srcFile) throws IOException {
		if (srcFile == null) {
			logger.error("传入的压缩文件为空");
			throw new RuntimeException("传入的压缩文件为空");
		}
		if (!new File(srcFile).isFile()) {
			logger.error("传入的不是一个压缩文件");
			throw new RuntimeException("传入的不是一个文件");
		}
		String descDir = new File(srcFile).getParentFile().getAbsolutePath();
		if (srcFile.endsWith(".zip")) {
			if (extractZIP(new File(srcFile), descDir)) {
				return descDir + srcFile.substring(srcFile.lastIndexOf("\\"), srcFile.lastIndexOf("."));
			}
		} else if (srcFile.endsWith(".rar")) {
			if (extractRAR(new File(srcFile), descDir)) {
				return descDir + srcFile.substring(srcFile.lastIndexOf("\\"), srcFile.lastIndexOf("."));
			}
		} else {
			throw new RuntimeException("无法识别的压缩文件");
		}
		return null;
	}

	/**
	 * @Description: 执行压缩的操作
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午12:16:17
	 * @Param: zipFile
	 * @Param: srcFiles 传入一个文件目录列表
	 * @Return: boolean 返回是否压缩成功
	 */
	public static boolean reduce(String zipFile, String... srcFiles) {

		if (zipFile == null | "".equals(zipFile)) {
			logger.error("压缩文件时传入的压缩文件目录为空");
			throw new RuntimeException("压缩文件时传入的压缩文件目录为空");
		}
		if (srcFiles == null | srcFiles.length == 0) {
			logger.error("压缩文件时传入的需要被压缩的文件列表为空");
			throw new RuntimeException("压缩文件时传入的需要被压缩的文件列表为空");
		}
		ZipOutputStream zipOutputStream = null;
		FileInputStream fis = null;
		try {
			File destFile = new File(zipFile);
			File parentFile = destFile.getParentFile();
			if (!parentFile.exists()) {
				if (!parentFile.mkdirs()) {
					throw new RuntimeException("压缩文件时父目录创建失败");
				}
			}
			if (!destFile.exists()) {
				if (!destFile.createNewFile()) {
					throw new RuntimeException("创建压缩文件失败");
				}
			}
			// 根据传入的压缩包目标文件路径创建一个zip输出流
			zipOutputStream = new ZipOutputStream(new FileOutputStream(destFile));

			// 遍历传入的文件路径列表
			for (String srcFile : srcFiles) {
				if (srcFile != null && !"".equals(srcFile)) {
					// 获取文件输入流
					File file = new File(srcFile);
					// 创建一个文件输入流，将传入的文件列表分别放到内存中
					fis = new FileInputStream(file);
					// 将文件放到输出流中
					zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) != -1) {
						zipOutputStream.write(buffer, 0, len);
					}
					// 将内存中的数据流刷新到文件中
					zipOutputStream.flush();
					// 关闭需要被压缩的文件实体
					zipOutputStream.closeEntry();
					// 关闭当前输入流
					fis.close();
				}
			}
			// 关闭总的zip文件输出流
			zipOutputStream.close();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("压缩文件时传入的压缩文件目录不存在");
		} catch (IOException e) {
			throw new RuntimeException("压缩文件失败");
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (zipOutputStream != null) {
					zipOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @throws IOException
	 * @Description: 执行解压zip文件的操作
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午12:18:12
	 * @Param: file 传入一个zip格式的压缩文件
	 * @Param: descDir 传入一个解压文件存放的目标目录
	 * @Return: boolean 返回是否解压成功
	 */
	public static boolean extractZIP(File file, String descDir) {
		boolean flag = false;
		ZipFile zipFile = null;
		InputStream is = null;
		FileOutputStream out = null;
		try { 
			// 创建一个zip文件
			zipFile = new ZipFile(file, "GBK");
			Enumeration<ZipEntry> zipEntrys = zipFile.getEntries();
			while (zipEntrys.hasMoreElements()) {
				ZipEntry zipEntry = zipEntrys.nextElement(); 
				// 获取压缩包中文件的名称,格式:压缩包文件名名称+文件名称
				String zipEntryName = zipEntry.getName(); 
				// 声明文件存放的目录
				String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\\\", "/");
				File outFile = new File(outPath); 
				// 判断是父目录是否存在，如果不存在则创建父目录
				File parentFile = outFile.getParentFile();
				if (!parentFile.exists()) {
					if (!parentFile.mkdirs()) {
						return flag;
					}
				}
				// 如果是文件夹，则跳过解压缩步骤
				if (outFile.isDirectory()) {
					continue;
				}
				// 获取压缩包文件中单个文件的文件输入流 
				is = zipFile.getInputStream(zipEntry); 
				// 判断是否是文件，如果是文件再进行解压操作 
				out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = is.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				is.close();
				out.close();
			}
			zipFile.close();
			flag = true;
		} catch (IOException e) {
			logger.error("解压zip文件时出现异常");
			try {
				if (is != null) {
					is.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				logger.error("解压zip文件出现异常时关闭文件流异常");
				throw new RuntimeException("解压zip文件出现异常时关闭文件流异常");
			}
			throw new RuntimeException("解压zip文件时出现异常");
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
				if (is != null) {
					is.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/*
	 * @SuppressWarnings("resource") public static boolean extractZIP(File file,
	 * String descDir) throws IOException { boolean flag = true; // 创建一个zip文件
	 * ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new
	 * FileInputStream(file)));
	 * 
	 * File fout = null; String parent = file.getParent();
	 * java.util.zip.ZipEntry entry = null; while ((entry = zis.getNextEntry())
	 * != null && !entry.isDirectory()) { fout = new File(parent,
	 * entry.getName()); if (!fout.exists()) { (new
	 * File(fout.getParent())).mkdirs(); }
	 * 
	 * BufferedOutputStream bos = new BufferedOutputStream(new
	 * FileOutputStream(fout));
	 * 
	 * int b = -1; byte[] buffer = new byte[1024]; while ((b = zis.read(buffer))
	 * != -1) { bos.write(buffer, 0, b); } bos.close(); }
	 * 
	 * zis.close(); return flag; }
	 */

	/**
	 * @Description: 执行解压rar文件的操作
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午3:39:30
	 * @Param: file 传入一个rar格式的压缩文件
	 * @Param: descDir 传入一个解压文件存放的目标目录
	 * @Return: boolean 返回是否解压成功
	 */
	@SuppressWarnings("resource")
	public static boolean extractRAR(File file, String descDir) {
		boolean flag = false;
		try {
			Archive archive = new Archive(file);
			FileHeader fh = archive.nextFileHeader();
			while (fh != null) {
				String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
				if (fh.isDirectory()) {
					// 如果是文件夹，则创建该目录
					File fol = new File(descDir + File.separator + fileName);
					fol.mkdirs();
				} else {
					// 如果是文件，则创建文件
					File out = new File(descDir + File.separator + fileName.trim());
					try {
						if (!out.exists()) {
							// 如果父目录不存在，则创建父目录
							if (!out.getParentFile().exists()) {
								out.getParentFile().mkdirs();
							}
						}
						// 输出文件流
						FileOutputStream os = new FileOutputStream(out);
						// 通过archive生成文件
						archive.extractFile(fh, os);
						os.close();
					} catch (Exception ex) {
						logger.error("解压rar文件出现异常");
						throw new RuntimeException("解压rar文件出现异常");
					}
				}
				// 获取下一个文件
				fh = archive.nextFileHeader();
			}
			archive.close();
			flag = true;
		} catch (RarException e) {
			logger.error("解析rar文件出现异常");
		} catch (IOException e) {
			logger.error("解压rar文件IO异常");
		}
		return flag;
	}
}
