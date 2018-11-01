package com.muck.front.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("frontSnapshotImageController")
@RequestMapping("/front/snapshotImage")
public class SnapshotImageController {

	/**
	 * 下载服务器上的文件到本地
	 * */
	@RequestMapping(value="downloadFile.action",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
	    File file = new File("D://system//ScreenCapture.exe");
	    InputStream is = new FileInputStream(file);
	    byte[] body = new byte[is.available()];
	    is.read(body);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attchement;filename=" +  file.getName());
	    HttpStatus statusCode = HttpStatus.OK;
	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
	    is.close();
	    
	    return entity;
	}
	
	/**
	 * 下载服务器上的文件到本地
	 * */
	@RequestMapping(value="downloadFile2.action",method=RequestMethod.GET)
	@ResponseBody
	public void download2(HttpServletRequest request) throws IOException {
	    File file = new File("D://system//ScreenCapture.exe");
	    InputStream is = new FileInputStream(file);
	   
	    FileOutputStream fos = new FileOutputStream(new File("d:/muck.exe"));
	    byte[] buffer = new byte[1024];
	    
	    int len = 0;
	    while((len = is.read(buffer)) != -1){
	    	fos.write(buffer, 0, len);
	    }
	    
	    fos.close();
	    is.close();
	}
}
