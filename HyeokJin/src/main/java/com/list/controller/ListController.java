package com.list.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.StringUtil;
import com.list.service.ListService;
import com.list.vo.ListVo;


@Controller
public class ListController {
 
	@Autowired(required=true)
	private ListService listService;
	
	@RequestMapping(value="/mainList")
	public String mainList(Model model) throws Exception {

		return "mainList";
	}

	@RequestMapping(value="/listDetail")
	public String listDetail(Model model, @RequestParam Map<String, String> param) throws Exception {
		model.addAttribute("listseq", param.get("listseq"));
		return "listDetail";
	}

	@RequestMapping(value="/listRegister")
	public String noticeList(@RequestParam Map<String, String> param){
		
		return "listRegister";
	}


	@ResponseBody
	@RequestMapping(value = "/selectList")
	public Map<String, Object> list(@RequestBody Map<String, String> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String pageSize = param.get("pageSize");
		String searchKeyword = param.get("searchKeyword");

		if((StringUtil.isNumeric(pageSize) == true) && (searchKeyword.length() < 100)) {
			try {
				List<ListVo> data = listService.selectNoticeList(param);
				resultMap.put("data", data);
			} catch (Exception e) {
				e.getMessage();
			}

			return resultMap;
		} else {
			return null;
		}

	}

	@ResponseBody
	@RequestMapping("/list/selectDetail")
	public Map<String, Object> selectDetail(@RequestBody Map<String, String> param,  HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ListVo> data = listService.selectDetail(param);
		resultMap.put("data", data);
		return  resultMap;
	}

	@ResponseBody
	@RequestMapping("/list/insert")
	public Map<String, Object> insertNotice(@RequestBody Map<String, String> param,  HttpServletRequest request) throws Exception{
		return listService.insertList(param);
	}
	
	@ResponseBody
	@RequestMapping("/list/update")
	public Map<String, Object> updateNotice(@RequestBody Map<String, String> param,  HttpServletRequest request) throws Exception{
		return listService.updateNotice(param);
	}

	@ResponseBody
	@RequestMapping("/list/delete")
	public Map<String, Object> deleteNotice(@RequestBody Map<String, String> param,  HttpServletRequest request) throws Exception{
		return listService.deleteNotice(param);
	}
}