package com.list.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.list.mapper.ListMapper;
import com.list.vo.ListVo;


@Service
public class ListServiceImpl implements ListService{

	@Autowired(required=true)
	private ListMapper listMapper;

	@Override
	public List<ListVo> selectNoticeList(Map<String, String> param) throws Exception {
		return listMapper.selectNoticeList(param);
	}

	@Override
	public Map<String, Object> insertList(Map<String, String> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//공지사항 등록
		int result = listMapper.insertList(param);
		String listseq_mapping = String.valueOf(param.get("listseq"));
		if(result > 0){
			resultMap.put("state", "S");
		}else{
			resultMap.put("state", "F");
		}

		//파일 등록
		String[] fileNm = param.get("fileName").split("\\|");
		String[] filePath = param.get("filePath").split("\\|");
		
		for(int i=0; i<fileNm.length; i++){
			param.put("filesname", fileNm[i]);
			param.put("filePath", filePath[i]);
			param.put("filePath", filePath[i]);
			param.put("listseq", listseq_mapping);	
			listMapper.insertFile(param);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateNotice(Map<String, String> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(param.get("fileName").equals("")){
			param.put("timeslt_cd", "N");
		}else{
			param.put("timeslt_cd", "Y");
		}
		
		if(listMapper.updateNotice(param) > 0){
			resultMap.put("state", "S");
		}else{
			resultMap.put("state", "F");
		}

		//기존 파일 삭제
		listMapper.deleteFile(param);			
		
		//파일 수정(해당 seq에 등록)		
		if(param.get("timeslt_cd").equals("Y")) {
			String[] fileNm = param.get("fileName").split("\\|");
			String[] filePath = param.get("filePath").split("\\|");

			for(int i=0; i<fileNm.length; i++){
				param.put("filesname", fileNm[i]);
				param.put("filePath", filePath[i]);
				listMapper.insertFile(param);
			}
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteNotice(Map<String, String> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(listMapper.deleteNotice(param) > 0){
			resultMap.put("state", "S");
		}else{
			resultMap.put("state", "F");
		}
		
		return resultMap;
	}

	@Override
	public List<ListVo> selectDetail(Map<String, String> param) throws Exception {
		return listMapper.selectDetail(param);
	}
}