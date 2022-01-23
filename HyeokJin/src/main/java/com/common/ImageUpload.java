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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.common.vo.Editor;


@Controller
public class ImageUpload {

	@RequestMapping("/singleImageUploader")
	public String singleImageUploader(HttpServletRequest request, Editor vo){
	    String callback = vo.getCallback();
	    String callback_func = vo.getCallback_func();
	    String file_result = "";
	    try {
	        if(vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null && !vo.getFiledata().getOriginalFilename().equals("")){
	            //파일이 존재하면
	            String original_name = vo.getFiledata().getOriginalFilename();
	            String ext = original_name.substring(original_name.lastIndexOf(".")+1);
	            //파일 기본경로
	            String defaultPath = request.getSession().getServletContext().getRealPath("/");
	            //파일 기본경로 _ 상세경로
	            String path = defaultPath + "resources" + File.separator + "smartEditor" + File.separator + "static" + File.separator + "sample" + File.separator + "multiupload" + File.separator;           
	            File file = new File(path);
	            System.out.println("path:"+path);
	            //디렉토리 존재하지 않을경우 디렉토리 생성
	            if(!file.exists()) {
	                file.mkdirs();
	            }
	            //서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
	            String realname = UUID.randomUUID().toString() + "." + ext;
	            ///////////////// 서버에 파일쓰기 /////////////////
	            vo.getFiledata().transferTo(new File(path+realname));
	            file_result += "&bNewLine=true&sFileName="+original_name+"&sFileURL=static/sample/multiupload/"+realname;
	        } else {
	            file_result += "&errstr=error";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "redirect:" + callback + "?callback_func="+callback_func+file_result;
	}


	 // 멀티이미지
	 @RequestMapping("/multiImageUploader")
	 public void multiImageUploader(HttpServletRequest request, HttpServletResponse response){
		try {
			 //파일정보
			 String sFileInfo = "";
			 //파일명을 받는다 - 일반 원본파일명
			 String filename = request.getHeader("file-name");
			 //파일 확장자
			 String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
			 //확장자를소문자로 변경
			 filename_ext = filename_ext.toLowerCase();
			 	
			 //이미지 검증 배열변수
			 String[] allow_file = {"jpg","png","bmp","gif"};

			 //돌리면서 확장자가 이미지인지 
			 int cnt = 0;
			 for(int i=0; i<allow_file.length; i++) {
			 	if(filename_ext.equals(allow_file[i])){
			 		cnt++;
			 	}
			 }

			 //이미지가 아님
			 if(cnt == 0) {
				 PrintWriter print = response.getWriter();
				 print.print("NOTALLOW_"+filename);
				 print.flush();
				 print.close();
			 } else {
				 //이미지이므로 신규 파일로 디렉토리 설정 및 업로드	
				 //파일 기본경로
				 String dftFilePath = request.getSession().getServletContext().getRealPath("/");
				 //파일 기본경로 _ 상세경로
				 String filePath = dftFilePath + "resources" + File.separator + "smartEditor" + File.separator + "static" + File.separator + "sample" + File.separator + "multiupload" + File.separator;
				 File file = new File(filePath);
				 if(!file.exists()) {
				 	file.mkdirs();
				 }
				 String realFileNm = "";
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				 String today= formatter.format(new java.util.Date());
				 realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
				 String rlFileNm = filePath + realFileNm;
				 ///////////////// 서버에 파일쓰기 ///////////////// 
				 InputStream is = request.getInputStream();
				 OutputStream os=new FileOutputStream(rlFileNm);
				 int numRead;
				 byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
				 while((numRead = is.read(b,0,b.length)) != -1){
				 	os.write(b,0,numRead);
				 }
				 if(is != null) {
				 	is.close();
				 }
				 os.flush();
				 os.close();
				 ///////////////// 서버에 파일쓰기 /////////////////
	
				 // 정보 출력
				 sFileInfo += "&bNewLine=true";
				 // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
				 sFileInfo += "&sFileName="+ filename;
				 sFileInfo += "&sFileURL="+"static/sample/multiupload/"+realFileNm;
				 PrintWriter print = response.getWriter();
				 print.print(sFileInfo);
				 print.flush();
				 print.close();
			 }	
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}

