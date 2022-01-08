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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Common {
	
	private static Logger log = LoggerFactory.getLogger("portal");
	
	public static String pwd;
	
	/**
	 * Object객체를 JSON 문자열로 변환
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String ObjectToJson(Object object){
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter prettyPrinter = mapper.writerWithDefaultPrettyPrinter();
		
		try{
			return prettyPrinter.writeValueAsString(object);
		}catch(JsonProcessingException je){
			return object.toString();
		}
		
	}
	
	/**
	 * json문자열을 indent
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	public static String JsonIndet(String jsonStr){
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter prettyPrinter = mapper.writerWithDefaultPrettyPrinter();

		try{
			JsonNode rootNode = mapper.readTree(jsonStr);
			
			return prettyPrinter.writeValueAsString(rootNode);
		}catch(IOException je){
			return jsonStr;
		}
	}
	
	/**
	 * request.getParameterMap()의 배열값을 String값으로 변환하여 반환
	 * @param mapParam
	 * @return
	 */
	public static Map<String, String> getParams(Map<String, String[]> mapParam){
		Map<String, String> mapResult = new HashMap<String, String>();
		try{
			for(String key : mapParam.keySet()){
				if(isXss(key) && !"pwd".equals(key)){
					String[] value = mapParam.get(key);
				    if(value != null){
				    	mapResult.put(key.replaceAll("'", ""), URLEncoder.encode(xssProtect(value[0]), "UTF-8").replaceAll("\\+", "\\ "));
				    }else{
				    	mapResult.put(key.replaceAll("'", ""), "");
				    }
				}
			}
		}catch(UnsupportedEncodingException e){
			log.error("Common.getParams() UnsupportedEncodingException : " + e.getMessage(), e);
		}
		
		return mapResult;
	}
	
	/**
	 * XSS 필터링
	 * @param xssStr
	 * @return
	 */
	private static String xssProtect(String xssStr){
		String result = xssStr;
    	if(result == null){
    		return null;
    	}
    	result = result.trim().replace("\n", "");
		String[] pattern = getXssPattern();

		for( String ptn : pattern){
			Pattern scriptPattern = Pattern.compile(ptn, Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(result).find()){
				result = "";
			}
		}

		return result;
	}
	
	/**
	 * XSS 여부
	 * @param value
	 * @param logString
	 * @return
	 * @throws Exception
	 */
	public static Boolean isXss(String xssStr){
    	if(xssStr == null){
    		return null;
    	}
    	
		String[] pattern = getXssPattern();

		for( String ptn : pattern){
			Pattern scriptPattern = Pattern.compile(ptn, Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(xssStr).find()){
				return false;
			}
		}

		return true;
	}
	
	/**
	 * XSS 문자열 검증 패턴
	 * @return
	 */
	private static String[] getXssPattern(){
		String[] result ={
			"<script>(.*?)</script>",
			"src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
			"src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
			"<(.*?)script(.*?)>",
			"expression\\((.*?)\\)",
			"javascript:",
			"vbscript:",
			"onload(.*?)=",
			"<(.*?)>"
		};
		
		return result;
	}
	
	/**
	 * cookie 값 반환
	 * @param key
	 * @return
	 */
	public static String getCookie(String key, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		
		try{
			for(int i=0; i<cookies.length; i++){
				if(key.equals(cookies[i].getName())){
					return cookies[i].getValue();
				}
			}	
		}catch(NullPointerException npe){
			return "";
		}
		
		return "";
	}
	
	/**
	 * cookie 값 변환
	 * @param key
	 * @return
	 */
	public static void setCookie(String key, String value, String domain, HttpServletRequest request, HttpServletResponse response){
		Cookie cookie = new Cookie(key, value);
//		cookie.setMaxAge(60 * 60 * 24); // 1일
		cookie.setPath("/");
		if(domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setSecure(false);
		response.addCookie(cookie);
	}
	
	/**
	 * cookie 값 변환
	 * @param key
	 * @return
	 */
	public static void deleteCookie(String key, String value, String domain, HttpServletRequest request, HttpServletResponse response){
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		if(domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setSecure(false);
		response.addCookie(cookie);
	}
	
	/**
	 * 파일경로 경로조작 체크
	 * @param key
	 * @return
	 */
	public static boolean checkPath(String fileName){
		if((fileName.indexOf(".."))!=-1 || (fileName.indexOf("./"))!=-1 || (fileName.indexOf(".\\"))!=-1 || (fileName.indexOf(":"))!=-1){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * session 값 반환
	 * @param key
	 * @return
	 */
	public static Object getSesion(String key, HttpServletRequest request){
		
		HttpSession httpSession = request.getSession(true);
		
		//log.info("userId" + httpSession.getAttribute("userId"));
		//log.info("token" + httpSession.getAttribute("token"));
		//log.info("userKey" + httpSession.getAttribute("userKey"));
		//log.info("userType" + httpSession.getAttribute("userType"));
		
		return httpSession.getAttribute(key);
	}
}
