package com.common;

import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

public class CheckParamUtil {
	
	public static boolean checkDownloadParam(String chkString) {
		if ((chkString.indexOf("..")) != -1 || (chkString.indexOf("./")) != -1 || (chkString.indexOf(".\\")) != -1 || (chkString.indexOf(":")) != -1) {
			return false;
		}
		return true;
	}
	
	public static boolean checkFile(File file) throws IOException{
		//String fileType = Files.probeContentType(file.toPath());
		String fileType = new MimetypesFileTypeMap().getContentType(file);
		
		if(fileType.contains("image/")) {
			return true;
		}else {
			return false;
		}
	}
}