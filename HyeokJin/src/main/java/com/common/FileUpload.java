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
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("fileUpload")
public class FileUpload {
	
	private static Logger log = LoggerFactory.getLogger("portal");
	
	@RequestMapping("image")
	public void image(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload){
		OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		try{
			File file = new File(upload.getOriginalFilename());
			String fileName = upload.getOriginalFilename();
			
			if(!CheckParamUtil.checkDownloadParam(fileName) || !CheckParamUtil.checkFile(file)){
				response.setCharacterEncoding("utf-8");
		   	 	response.setContentType("text/html; charset=UTF-8");
		        PrintWriter writer = response.getWriter();
		        writer.println("<script type='text/javascript'>");
		        writer.println("alert('파일 검증 실패하였습니다.');");
		        writer.println("</script>");
		        writer.flush();
		        log.error("[Upload FAIL] 파일명 : " + fileName);
			}else {
				byte[] bytes = upload.getBytes();
				
				//파일업로드 경로
				String path = CommonUtil.getCurrentTime().substring(6, 8);
				StringBuffer uploadPath = new StringBuffer();
				uploadPath.append(Constant.uploadPath);
				uploadPath.append(path);
				
				//디렉토리 생성
				File directory = new File(uploadPath.toString());
				if(!directory.isDirectory()){
					directory.mkdirs();
				}
				
				//파일 중복 체크
				boolean isFile = true;
				while(isFile){
					File tempFile = new File(uploadPath + "/" + fileName);
					StringBuffer tempFileName = new StringBuffer();
					
					//파일명 중복시 파일명 변경
					if(tempFile.isFile()){
						tempFileName.append("(1)");
						tempFileName.append(fileName);
						fileName =  tempFileName.toString();
					}else{
						isFile = false;
					}
				}
				
				out = new FileOutputStream(new File(uploadPath + "/" + fileName));
				out.write(bytes);
				
				String callback = request.getParameter("CKEditorFuncNum");
				printWriter = response.getWriter();
				String fileUrl = "/fileDownload/download?fileName=" + fileName + "&path=" + path;
				printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction("
						+ callback
						+ ",'"
						+ fileUrl
						+ "','이미지를 업로드 하였습니다.'"
						+ ");</script>");
				printWriter.flush();
				
				log.info("[Upload SUCCESS] Path :" + uploadPath);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			try{
				if(out != null){
					out.close();
				}
				if(printWriter != null){
					printWriter.close();
				}
			}catch(IOException e){
				log.error("FileUpload image : " + e.getMessage());
			}
		}
	}
	
	@RequestMapping("file")
	public void file(@RequestParam("file") MultipartFile upload, HttpServletRequest request, HttpServletResponse response){
		JSONObject resultJson = new JSONObject();
		OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		try{
			String fileName = upload.getOriginalFilename();
			byte[] bytes = upload.getBytes();
			
			//파일명 검증
			if(!validateImageExtension(getExtension(fileName))){
				resultJson.put("resultCode", "9999");
				
				printWriter = response.getWriter();
				printWriter.println(resultJson.toString());
				printWriter.flush();
				log.info("파일명 검증 실패 : " + fileName);
				return;
			}
			
			//파일업로드 경로
			String path = CommonUtil.getCurrentTime().substring(6, 8);
			StringBuffer uploadPath = new StringBuffer();
			uploadPath.append(Constant.uploadPath);
			uploadPath.append(path);
			
			//디렉토리 생성
			File directory = new File(uploadPath.toString());
			if(!directory.isDirectory()){
				directory.mkdirs();
			}
			
			//파일 중복 체크
			boolean isFile = true;
			while(isFile){
				File tempFile = new File(uploadPath + "/" + fileName);
				StringBuffer tempFileName = new StringBuffer();
				
				//파일명 중복시 파일명 변경
	            if(tempFile.isFile()){
	            	tempFileName.append("(1)");
	            	tempFileName.append(fileName);
	            	fileName =  tempFileName.toString();
	            }else{
	            	isFile = false;
	            }
			}
			
			out = new FileOutputStream(new File(uploadPath + "/" + fileName));
			out.write(bytes);
			
			resultJson.put("resultCode", "0000");
			resultJson.put("file", fileName);
			resultJson.put("path", path);
			
			printWriter = response.getWriter();
			printWriter.println(resultJson.toString());
			printWriter.flush();
			
			log.info("[Upload SUCCESS] Path :" + uploadPath + "/" + fileName);
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			try{
				if(out != null){
					out.close();
				}
				if(printWriter != null){
					printWriter.close();
				}
			}catch(IOException e){
				log.error("FileUpload file : " + e.getMessage());
			}
		}
	}
	
	private String getExtension(String fileName){
		String extension = null;
		
		if(fileName == null || fileName.trim().equals("")) {
			extension = null;
		}
		else {
			String temps[] = fileName.split("\\.");
			extension = temps[temps.length - 1];
		}
		
		return extension;
	}
	
	/**
	 * 이미지 확장자 확인
	 * @param fileExtension
	 * @return
	 */
	private boolean validateImageExtension(String extension) {
		String[] list = Constant.FILE_EXTENSION_LIST;
		
		for(int i = 0, n = list.length; i < n; i++) {
			boolean isIgnore = list[i].equalsIgnoreCase(extension);
			
			if(isIgnore) {
				return true;
			}	
		}

		return false;
	}
}

