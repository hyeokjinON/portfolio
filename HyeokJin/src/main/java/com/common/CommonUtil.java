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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonUtil {

	/**
	 * 현재 시간을 반환 yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
		
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 랜덤값 반환 
	 * @param length 랜덤값의 길이
	 * @return
	 */
	public static String getRandomString(int length){
		StringBuffer result = new StringBuffer();
		
		for(int i=0; i<length; i++){
			int num = (int)(Math.random() * 10);
			result.append(num);
		}
		
		return result.toString();
	}
	
	/**
	 * null일경우 빈값 반환
	 * @param str
	 * @return
	 */
	public static String nvlStr(String str){
		if(null == str){
			return "";
		}
		
		return str;
	}
}
