package com.list.service;

import java.util.List;
import java.util.Map;

import com.list.vo.ListVo;

public interface ListService {

	List<ListVo> selectList(Map<String, String> param) throws Exception;

	Map<String, Object> insertList(Map<String, String> param) throws Exception;

	Map<String, Object> updateNotice(Map<String, String> param) throws Exception;

	Map<String, Object> deleteNotice(Map<String, String> param) throws Exception;

	List<ListVo> selectDetail(Map<String, String> param) throws Exception;
}