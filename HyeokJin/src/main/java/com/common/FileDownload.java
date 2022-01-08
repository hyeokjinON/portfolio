/*
 * KT-MEG Center Platform version 3.0
 * 
 *  Copyright ⓒ 2017 kt corp. All rights reserved.
 * 
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kt.meg.base.exception.ServiceException;




@Controller
@RequestMapping("fileDownload")
public class FileDownload {
	
	private static Logger log = LoggerFactory.getLogger("portal");
	
	@Autowired
	FileDownloadUtil fileDownloadUtil;
	
	/** 
	 * 파일다운로드 
	 * @param request 
	 * @return 
	 * @throws IOException 
	 */ 
	@RequestMapping("download")
	public void downloadFileController(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String name = req.getParameter("fileName").replaceAll("[/%]", "").replaceAll("\r", "").replaceAll("\n", "");
		if(!CheckParamUtil.checkDownloadParam(name)){ 
			throw new ServiceException(1107, "file name 오류");
		}
		name = URLEncoder.encode(name, "UTF-8");  //한글깨짐 방지
		String fileFullPath = Constant.uploadPath + req.getParameter("path").replaceAll("/", "") + "/" + req.getParameter("fileName").replaceAll("/", "");  // 서버에 맞는 업로드 기본 폴더
		File downFile = new File(fileFullPath);  //파일 객체 생성
		if(downFile.isFile()){  // 파일이 존재하면
			int fSize = (int)downFile.length(); 
			res.setBufferSize(fSize); 
			res.setContentType("application/octet-stream"); 
			res.setHeader("Content-Disposition", "attachment; filename=" + name + ";"); 
			res.setContentLength(fSize);  // 헤더정보 입력
			FileInputStream in  = new FileInputStream(downFile); 
			ServletOutputStream out = res.getOutputStream(); 
			try{ 
				byte[] buf=new byte[8192];  // 8Kbyte 로 쪼개서 보낸다.
				int bytesread = 0, bytesBuffered = 0; 
				while(bytesread > -1 ) { 
					out.write( buf, 0, bytesread ); 
					bytesBuffered += bytesread; 
					if(bytesBuffered > 1024 * 1024){ //아웃풋스트림이 1MB 가 넘어가면 flush 해준다.
			            bytesBuffered = 0; 
			            out.flush(); 
			        }
					
					bytesread = in.read(buf);
			    } 
			}finally{ 
			    if(out != null){ 
			        out.flush(); 
			        out.close(); 
			    } 
			    if(in != null){ 
			    	in.close(); 
			    } 
			    //에러가 나더라도 아웃풋 flush와 close를 실행한다.
			} 
		} 
		
		log.info("[Download SUCCESS] path : " + fileFullPath);
	}
	
}
