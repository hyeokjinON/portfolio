package com.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	public static boolean isNumeric(String s) {
		  try {
		      Double.parseDouble(s);
		      return true;
		  } catch(NumberFormatException e) {
		      return false;
		  }
		}
	
	private static final String[] IP_HEADER_CANDIDATERS = {
			"X-Forwarded-For",
			"X-FORWARDED-FOR",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR",
			"HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR",
			"HTTP_FORWARDED",
			"HTTP_VIA",
			"REMOTE_ADDR",
			"X-Real-IP"
	};
	
	public static String getClientIpAddress(HttpServletRequest request) {
		for(String header : IP_HEADER_CANDIDATERS) {
			String ip = request.getHeader(header);
			if(ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		
		return  request.getRemoteAddr();
	}
	
	public static boolean isEmptyMapValue(Map<String, ?> map, String key) {
		Object value = map.get(key);
		if (value == null) {
			return true;
		}
		
		if ("".equals(value.toString())) {
			return true;
		}
		
		return false;
	}
}
