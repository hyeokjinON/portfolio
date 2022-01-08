/*
 * KT-MEG Center Platform version 3.0 
 * 
 *   Copyright ⓒ 2017 kt corp. All rights reserved.
 * 
 *   This is a proprietary software of kt corp, and you may not use this file except in
 *   compliance with license agreement with kt corp. Any redistribution or use of this
 *   software, with or without modification shall be strictly prohibited without prior written
 *   approval of kt corp, and the copyright notice above does not evidence any actual or
 *   intended publication of such software.
 */
package com.common; 

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileDownloadUtil {
	
	private static Logger log = LoggerFactory.getLogger("portal");
	
	//브라우저 체크
	public String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		
		if(header.indexOf("MSIE") > -1){
			return "MSIE";
		}else if(header.indexOf("Chrome") > -1){
			return "Chrome";
		}else if(header.indexOf("Opera") > -1){
			return "Opera";
		}else if(header.indexOf("Trident/7.0") > -1){ //IE 11이상
			// IE 버전 별 체크 >> Trident/6.0(IE 10), Trident/5.0(IE 9), Trident/4.0(IE 8)
			return "MSIE";
		}
		return "Firefox";
	}
	
	//파일명 특수문자 처리
	public String getDisposition(String fileName, String browser) {
		String encodedFileName = null;
		try {
			if(browser.equals("MSIE")){
				encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			}else if(browser.equals("Firefox")){
				encodedFileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
			}else if(browser.equals("Opera")){
				encodedFileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
			}else if(browser.equals("Chrome")){
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < fileName.length(); i++) {
					char c = fileName.charAt(i);
					if(c>'~'){
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					}else{
						sb.append(c);
					}
				}
				encodedFileName = sb.toString();
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			// TODO: handle exception
		}
		return encodedFileName;
	}
}
