package com.list.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.list.vo.ListVo;

@Mapper
public interface ListMapper {

	public List<ListVo> selectNoticeList(Map<String, String> param) throws SQLException;

	public int insertList(Map<String, String> param) throws SQLException;

	public int insertFile(Map<String, String> param) throws SQLException;

	public int updateNotice(Map<String, String> param) throws SQLException;

	public int deleteFile(Map<String, String> param) throws SQLException;

	public int deleteNotice(Map<String, String> param) throws SQLException;

	public List<ListVo> selectDetail(Map<String, String> param) throws SQLException;

}
