/*
 * KT-MEG Guardian Platform version 1.0
 * 
 *   Copyright ⓒ 2017 kt corp. All rights reserved.
 * 
 *   This is a proprietary software of kt corp, and you may not use this file except in
 *   compliance with license agreement with kt corp. Any redistribution or use of this
 *   software, with or without modification shall be strictly prohibited without prior written
 *   approval of kt corp, and the copyright notice above does not evidence any actual or
 *   intended publication of such software.
 */
package com.list.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ListVo {

	@Getter @Setter private String totalCnt;

	@Getter @Setter private String rnum;
	
	
	@Getter @Setter private long listseq;
	@Getter @Setter private String title;
	@Getter @Setter private String content;
	@Getter @Setter private String id;
	@Getter @Setter private String name;
	@Getter @Setter private String cretr_id;
	@Getter @Setter private String cret_dt;
	@Getter @Setter private String amdr_id;
	@Getter @Setter private String amd_dt;
	
	@Getter @Setter private String upld_seq;
	@Getter @Setter private String file_nm;
	@Getter @Setter private String file_path;
	

}