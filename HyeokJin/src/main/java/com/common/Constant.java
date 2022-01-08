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

public class Constant {

	/*API호출 파라메터*/
	public final static String HEADER = "header";		//API호출 파라메터 - 헤더
	public final static String PARAMS = "params";		//API호출 파라메터 - 파라메터
	public final static String CALLURL = "callUrl";		//API호출 파라메터 - call URL
	
	public final static String PORTAL_RESULT_CODE = "portalResultCode";				//결과 코드
	public final static String PORTAL_RESULT_MASSAGE = "portalResultMassage";	//결과 메세지
	public final static String PORTAL_RESULT_CODE_SUCCESS = "0000";				//결과 코드 - 성공
	public final static String PORTAL_RESULT_CODE_FAIL = "9999";						//결과 코드 - 실패
	
	/** 파일(이미지) 확장자 */
	public static final String[] FILE_EXTENSION_LIST = {"hwp", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "gif", "jpg", "jpeg", "png", "txt"};
	
	/*WAS 기동시 최초 한번 설정되는 변수*/
	public static String mapUrl = "";			//구글 좌표 URL
	public static String uploadPath = "";		//업로드 경로
	public static String imdgApiUrl = "";		//imdg api call URL


}
