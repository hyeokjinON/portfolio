var functionText = "";

var common = {

	/**
	 * Ajax 공통 함수
	 * @param url 요청 url
	 * @param params 요청 파라미터
	 * @param functionName callback함수
	 * @param methodType 요청 타입
	 * @param async async처리 없으면 true
	 */
	ajax : function(url, params, functionName, methodType, async){
		var jsonParams;
		
		if(async == undefined){
			async = true;
		}
		
		if(!$.isEmptyObject(params)){
			jsonParams = JSON.stringify(params);	
		}else{
			jsonParams = "";
		}
		
		//호출 시스템명을 가져오기 위한 객체
		var systemName = url.split("/");
		
		//로딩화면 시작
		loadingbar.start();
		
		$.ajax({
			async: async,
			cache: false,
			type: methodType,
			url: url,
			timeout: 60000,
			contentType: "application/json",
			dataType: "json",
			data: jsonParams,
			
			success: function(data){
				var message = "";
				var responseData = $.extend(true, {}, data);	//deep copy
				responseData.reqParam = undefined;
				
				if(systemName[1] == "portal"){
					message += "요청 API : " + systemName[1] + "\n";
					message += "요청 URI : " + url + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params) + "\n";
				}else{
					message += "요청 API : " + systemName[2] + "\n";
					message += "요청 URI : " + params.callUrl + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params.params) + "\n";
				}
				message += "응답 결과 : \n" + JSON.stringify(responseData) + "\n\n";
				
				if(data.portalResultCode == "9999"){
					alert(data.portalResultMassage);
					
					//console.log("ERROR(9999) :");
					//console.log(message);
				}else{
					if(functionName instanceof Function){
						functionName(data);
					}else{
						if(functionName != ""){
							eval(functionName);
						}
					}
				}
				
				//console.log(message);
			},
			
			error: function(e){
				var message = "";
				message += "#####ERROR#####\n";
				if(systemName[1] == "portal"){
					message += "요청 API : " + systemName[1] + "\n";
					message += "요청 URI : " + url + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params) + "\n";
				}else{
					message += "요청 API : " + systemName[2] + "\n";
					message += "요청 URI : " + params.callUrl + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params.params) + "\n";
				}
				message += "에러 결과 : \n" + e.responseText + "\n\n";
				
				console.log(message);
				
				alert("서버연결에 실패했습니다. 이용에 불편을 드려 죄송합니다.[" + message.URL + "]");
			},
			
			complete: function(){
				//로딩화면 종료
				loadingbar.stop();
			}
		});
		
	},
	
	/**
	 * Ajax 공통 함수
	 * @param url 요청 url
	 * @param params 요청 파라미터
	 * @param functionName callback함수
	 * @param methodType 요청 타입
	 * @param async async처리 없으면 true
	 */
	ajaxXML : function(url, params, functionName, methodType, async){
		var jsonParams;
		
		if(async == undefined){
			async = true;
		}
		
		if(!$.isEmptyObject(params)){
			jsonParams = JSON.stringify(params);	
		}else{
			jsonParams = "";
		}
		
		//console.log(jsonParams);
		
		//호출 시스템명을 가져오기 위한 객체
		var systemName = url.split("/");
		
		//로딩화면 시작
		loadingbar.start();
		
		$.ajax({
			async: async,
			cache: false,
			type: methodType,
			url: url,
			timeout: 60000,
			contentType: "application/json",
			dataType: "xml",
			data: jsonParams,
			
			success: function(data){
				var message = "";
				var responseData = $.extend(true, {}, data);	//deep copy
				responseData.reqParam = undefined;
				
				if(systemName[1] == "portal"){
					message += "요청 API : " + systemName[1] + "\n";
					message += "요청 URI : " + url + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params) + "\n";
				}else{
					message += "요청 API : " + systemName[2] + "\n";
					message += "요청 URI : " + params.callUrl + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params.params) + "\n";
				}
				message += "응답 결과 : \n" + JSON.stringify(responseData) + "\n\n";
				
				if(data.portalResultCode == "9999"){
					alert("서버연결에 실패했습니다. 이용에 불편을 드려 죄송합니다.[" + message.URL + "]");
					
					console.log("ERROR(9999) :");
					console.log(message);
				}else{
					if(functionName instanceof Function){
						functionName(data);
					}else{
						if(functionName != ""){
							eval(functionName);
						}
					}
				}
				
				//console.log(message);
			},
			
			error: function(e){
				var message = "";
				message += "#####ERROR#####\n";
				if(systemName[1] == "portal"){
					message += "요청 API : " + systemName[1] + "\n";
					message += "요청 URI : " + url + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params) + "\n";
				}else{
					message += "요청 API : " + systemName[2] + "\n";
					message += "요청 URI : " + params.callUrl + "\n";
					message += "요청 파라메터 : \n" + JSON.stringify(params.params) + "\n";
				}
				message += "에러 결과 : \n" + e.responseText + "\n\n";
				
				console.log(message);
				
				alert("서버연결에 실패했습니다. 이용에 불편을 드려 죄송합니다.[" + message.URL + "]");
			},
			
			complete: function(){
				//로딩화면 종료
				loadingbar.stop();
			}
		});
		
	},
	
	/**
	 * 공통 파라메터 객체 생성
	 */
	setCommonParam : function(){
		return new Object();
	},
	
	/**
	 * form 전송 함수
	 * @param objectParam Object타입의 parameter값
	 * @param action 전송할 url
	 * @param method 전송방식 GET/POST
	 * */
	submit : function(objectParam, action, method, target) {
		var form = document.createElement("form");
		var theFirstChild = form.firstChild;
		form.action = action;
		form.method = method;
		if(target){
			form.target = target;
		}
		
		$.each(objectParam, function(key, value){
			form.insertBefore(common.createHidden(key, value), theFirstChild);
		});
		document.body.appendChild(form); //ie경우 필수
		form.submit();
		document.body.removeChild(form);	
	},
	
	//hidden input 생성 반환
	createHidden : function(name, value){
		var obj = document.createElement("input");
		obj.type = "hidden";
		obj.name = name;
		//obj.value = encodeURIComponent(value);
		obj.value = value;
		return obj;
	},
	
	//파라미터 파싱해서 Map에 저장
	getParameter : function (params) {
		//console.log("파라메터 정보 : " + params);
		var map = new Map();
		
		try{
			var tmpParam = params.substring(1, params.length-1).split(", ");
			for(var i=0; i<tmpParam.length; i++){
				var paramInfo = tmpParam[i].split("=");
				var paramValue = paramInfo[1];
				map.put(paramInfo[0], decodeURIComponent(paramValue).replace(/[\^§]/gi," "));
			}
		}catch(e){}
	    
	    return map;
	},
	
	// 파라메터 파싱해서 paramMap에 추가
	putParameter : function (paramMap, subParam) {
	    var tmpParam = subParam.substring(1, subParam.length-1).split(", ");
		for(var i=0; i<tmpParam.length; i++){
			var paramInfo = tmpParam[i].split("=");
			var paramValue = paramInfo[1];
			paramMap.put(paramInfo[0], paramValue);
		}
		
	    return paramMap;
	},
	
	//엑셀 다운로드 버튼 클릭시 IE와 Chrome에 따라 처리
	excelDown : function(id){
		if(navigator.userAgent.indexOf('Chrome') != -1){
			$.fn.btechco_excelexport({
		        containerid: id,
		        datatype: $datatype.Table
		    });
		}else{
			var tableId = id
			$.fn.exportBtnClick(id)
		}
	},
	
	//alert창 open
	alert : function(msg, fText){
		$("#comm_alert_msg").html(msg);
		$("#commAlert").show();
		
		if(fText != undefined){
			functionText = fText;
		}
	},
	
	//목록 페이징 처리
	paging : function(count, pageCnt){
		var num = 1;
		$(".paging").paging(count, {
			format : '[< nnncn >]',
			perpage : pageCnt,
			onSelect : function(page){
				//최초에 1페이지 선택되면서 한번더 호출되는것을 방지하기 위한 처리
				if(num != page){
					process.changePage(page);
					num = page;
				}
			},
			
			onFormat : function(type){
				switch (type) {
				case 'block': // n and c
					if (!this.active){
						return '<a href="javascript:void(0)">' + this.value + '</a>';
					}else if (this.value != this.page){
						return '<a href="javascript:void(0)">' + this.value + '</a>';
					}
					return '<a class="on" href="javascript:void(0)">' + this.value + '</a>';
				case 'next': // >
					return '<a class="next" href="javascript:void(0)"><img src="/resources/images/mypage/btn_paging_next.gif" alt="다음" /></a>';
				case 'prev': // <
					return '<a class="pre" href="javascript:void(0)"><img src="/resources/images/mypage/btn_paging_prev.gif" alt="이전" /></a>';
				case 'first': // [
					return '<a class="pre" href="javascript:void(0)"><img src="/resources/images/mypage/btn_paging_bprev.gif" alt="처음" /></a>';
				case 'last': // ]
					return '<a class="next" href="javascript:void(0)"><img src="/resources/images/mypage/btn_paging_bnext.gif" alt="끝" /></a>';
				}
			}
		});
	},
	
/*	//달력
	calendar : function(id){
		$("#" + id).datepicker({
			dateFormat: "yymmdd",
			nextText : "다음달",
			prevText : "이전달",
			dayNamesMin : ["일","월","화","수","목","금","토"],
			monthNames : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			showOn: "button",
			buttonImage: "/images/common/ico_calender.jpg",
			buttonImageOnly: true,
			buttonText: "Select date"
		});
	},
	
	//달력 - 오늘 날짜 이후부터 선택 가능
	calendar1 : function(target){
		$(target).datepicker({
			dateFormat: "yymmdd",
			nextText : "다음달",
			prevText : "이전달",
			dayNamesMin : ["일","월","화","수","목","금","토"],
			monthNames : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			showOn: "button",
			buttonImage: "/images/common/ico_calender.jpg",
			buttonImageOnly: true,
			buttonText: "Select date",
			minDate : 0
		});
	},
	
	//달력 - 년, 월, 일 분리 기능
	calendar2 : function(picker_id, year_id, month_id, day_id){
		$("#" + picker_id).datepicker({
			changeYear: true,
			changeMonth: true,
			yearRange: "-100:+0",
			dateFormat: "yymmdd",
			nextText : "다음달",
			prevText : "이전달",
			dayNamesMin : ["일","월","화","수","목","금","토"],
			monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			showOn: "button",
			buttonImage: "/images/common/ico_calender.jpg",
			buttonImageOnly: true,
			buttonText: "Select date",
			onSelect : function(dateText){
				$('#' + year_id).val(dateText.substr(0, 4));
				$('#' + month_id).val(dateText.substr(4, 2));
				$('#' + day_id).val(dateText.substr(6, 2));
			}
		});
	},
	
	//달력 - 기간 유효성 검사
	calendar3 : function(id){
		$("#" + id).datepicker({
			dateFormat: "yymmdd",
			nextText : "다음달",
			prevText : "이전달",
			dayNamesMin : ["일","월","화","수","목","금","토"],
			monthNames : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			showOn: "button",
			buttonImage: "/images/common/ico_calender.jpg",
			buttonImageOnly: true,
			buttonText: "Select date",
			minDate : 0,
			maxDate : "+3M-1D",
			disabled : true,
			onSelect : function(dateText){
				if($('#pop_s_date').val()!='' && $('#pop_f_date').val()!=''){
					if(id=='pop_s_date' && dateText > parseInt($('#pop_f_date').val())){
						common.alert('시작 날짜는 종료 날짜 이전으로 선택해주세요.');
						$('#pop_s_date').val('');
					}else if(id=='pop_f_date' && dateText < parseInt($('#pop_s_date').val())){
						common.alert('종료 날짜는 시작 날짜 이후로 선택해주세요.');
						$('#pop_f_date').val('');
					}
				}
			}
		});
	},*/
	
	//일자 더하기
	changeCalendarDate : function(startDate, num){
		var resultDate =  new Date(startDate.substring(0, 4), Number(startDate.substring(4, 6)), startDate.substring(6, 8));
		resultDate.setDate(resultDate.getDate() + num);
		
		var mm = (resultDate.getMonth() + "").length < 2 ? "0" + resultDate.getMonth() : resultDate.getMonth();
		var dd = (resultDate.getDate() + "").length < 2 ? "0" + resultDate.getDate() : resultDate.getDate();
		
		return "" + resultDate.getFullYear() + mm + dd;
	},
	
	//월 더하기
	changeCalendarMonth : function(startDate, num){
		var resultDate =  new Date(startDate.substring(0, 4), Number(startDate.substring(4, 6)), startDate.substring(6, 8));
		resultDate.setMonth(resultDate.getMonth() + num);
		
		var mm = (resultDate.getMonth() + "").length < 2 ? "0" + resultDate.getMonth() : resultDate.getMonth();
		var dd = (resultDate.getDate() + "").length < 2 ? "0" + resultDate.getDate() : resultDate.getDate();
		
		return "" + resultDate.getFullYear() + mm + dd;
	},
	
	// 숫자에 콤마를 찍어줌
	numComma: function(str){
		str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	},
	
	// 숫자에 콤마를 지워줌
	unNumComma : function(target){
		return target.replace(/,/g, "");
	},
	
	//기간형식 YYYYMMDDHHmmSS -> yyyy.mm.dd일 로 변경
	getYYYYMMDD_01 : function(data){
		if(string.strNvl(data) == ""){
			return "";
		}
		return data.substring(0, 4) + '.' + data.substring(4, 6) + '.' + data.substring(6, 8);
	},
	
	//기간형식 YYYYMMDDHHmmSS -> yyyy-mm-dd일 로 변경
	getYYYYMMDD_02 : function(data){
		if(string.strNvl(data) == ""){
			return "";
		}
		return data.substring(0, 4) + '-' + data.substring(4, 6) + '-' + data.substring(6, 8);
	},
	
	//유저타입코드로 유저타입명 반환
	getUserTypeNm: function(userType){
		var result = "";
		if(userType == _UT_1003_){
			result = "직원(영업대표)"
		}else if(userType == _UT_1004_){
			result = "직원(현장PM)"
		}else if(userType == _UT_1005_){
			result = "직원(영업 에이전트)"
		}else if(userType == _UT_1006_){
			result = "직원(운용 에이전트)"
		}else if(userType == _UT_1007_){
			result = "직원(콜센터)"
		}else if(userType == _UT_1008_){
			result = "직원(TOC ADMIN)"
		}else if(userType == _UT_1009_){
			result = "직원(홈포털관리자)"
		}else if(userType == _UT_1010_){
			result = "KT국사관리자"
		}else if(userType == _UT_2000_){
			result = "BM(사업팀)"
		}else if(userType == _UT_9999_){
			result = "직원(테스트아이디)"
		}
		
		return result;
	},
	//서비스 타입코드에 해당하는 명칭 반환 
	getSvcAtribCdNm: function(svcAtribCd){
		var result = "";
		if(svcAtribCd == "001"){
			result = "FT1"
		}else if(svcAtribCd == "1"){
			result = "FT1"
		}else if(svcAtribCd == "002"){
			result = "FT2"
		}else if(svcAtribCd == "2"){
			result = "FT2"
		}else if(svcAtribCd == "101"){
			result = "LT1"
		}else if(svcAtribCd == "102"){
			result = "LT2"
		}else if(svcAtribCd == "103"){
			result = "LT3"
		}else if(svcAtribCd == "104"){
			result = "LT4"
		}else if(svcAtribCd == "105"){
			result = "LT5"
		}else if(svcAtribCd == "106"){
			result = "LT6"
		}else if(svcAtribCd == "107"){
			result = "LT7"
		}else if(svcAtribCd == "108"){
			result = "LT8"
		}else if(svcAtribCd == "201"){
			result = "PT1"
		}else if(svcAtribCd == "202"){
			result = "PT2"
		}else if(svcAtribCd == "401"){
			result = "REMS"
		}else if(svcAtribCd == "801"){
			result = "eDR"
		}
		return result;
	},	
	//문자열 정보(num:숫자개수, eng:영문개수, spe:특수문자개수, pwdTotCnt:num+eng+spe)
	getTextInfo : function(text){
		var num = 0;
		var eng = 0;
		var spe = 0;
		var pwdTotCnt = 0;
		for(var i=0; i<text.length; i++){
			var tempPwd = text.substr(i, 1);
			if(tempPwd.search(/[0-9]/g) == 0){
				num++;
			}else if(tempPwd.search(/[a-z]/ig) == 0){
				eng++;
			}else if(tempPwd.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi) == 0){
				spe++;
			}
		}
		
		pwdTotCnt = num + eng + spe;
		
		var result = new Object();
		
		result.num = num;
		result.eng = eng;
		result.spe = spe;
		result.pwdTotCnt = pwdTotCnt;
		
		return result;
	},
	
	//사업장 유형 반환
	getSvcType : function(tabCd){
		if(tabCd == "eManager"){
			return "eM";
		}else if(tabCd == "eDR"){
			return "eD";
		}else if(tabCd == "eGen"){
			return "eG";
		}else if(tabCd == "ESS"){
			return "ESS";
		}else if(tabCd == "EV"){
			return "EV";
		}else if(tabCd == "eTrade"){
			return "eT";
		}else{
			return "";
		}
	},
	
	//모바일 브라우저 유무 true/false
	isMobile : function(){
		var filter = "win16|win32|win64|mac|macintel"; 
		
		if(navigator.platform){
			if(filter.indexOf(navigator.platform.toLowerCase()) < 0){
				return true; 
			}else{ 
				return false;
			}
		}
	},
	
	//알림팝업
	showToast : function(txt){
		$("#btn_toast").text(txt);
		$("#btn_toast").show(); 
		setTimeout(function(){
				$("#btn_toast").hide();
			}
		, 3000);
	},
	
	// 이메일 체크 
	checkMail : function(strMail){ 
	   /** 체크사항 
	     - @가 2개이상일 경우 
	     - .이 붙어서 나오는 경우 
	     -  @.나  .@이 존재하는 경우 
	     - 맨처음이.인 경우 
	     - @이전에 하나이상의 문자가 있어야 함 
	     - @가 하나있어야 함 
	     - Domain명에 .이 하나 이상 있어야 함 
	     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
	 
	    var check1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;  
	 
	    var check2 = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/; 
	      
	    if ( !check1.test(strMail) && check2.test(strMail) ) { 
	        return true; 
	    } else { 
	        return false; 
	    } 
	}
	
	
};

var cookie = {
	//cookie 생성
	setCookie: function(cookieName, value, minute, domain){
		var domainStr = "";
		if(domain != undefined){
			domainStr = "; domain=" + domain;
		}
		
	    var exdate = new Date();
	    //exdate.setDate(exdate.getDate() + exdays);
	    exdate.setMinutes(exdate.getMinutes() + minute);
	    var cookieValue = escape(value) + ((minute==null) ? ";path=/" : (";path=/;expires=" + exdate.toGMTString())) + domainStr;
	    
	    document.cookie = cookieName + "=" + cookieValue;
	},
	
	//cookie 삭제
	deleteCookie: function(cookieName, domain){
		var domainStr = "";
		if(domain != undefined){
			domainStr = "; domain=" + domain;
		}
		
	    var expireDate = new Date();
	    expireDate.setDate(expireDate.getDate() - 1);
	    document.cookie = cookieName + "= " + ";path=/;expires=" + expireDate.toGMTString() + domainStr;
	},
	 
	//cookie 반환
	getCookie: function(cookieName){
	    cookieName = cookieName + '=';
	    var cookieData = document.cookie;
	    var start = cookieData.indexOf(cookieName);
	    var cookieValue = '';
	    if(start != -1){
	        start += cookieName.length;
	        var end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cookieValue = cookieData.substring(start, end);
	    }
	    return unescape(cookieValue);
	}
};

//맵
var Map = function(){
	this.map = new Object();
};  
Map.prototype = {   
    put : function(key, value){
    	//value = string.strNvl(value) == "" ? "" : value.toString();
        this.map[key] = value;
    },   
    get : function(key){   
        return this.map[key];
    },
    containsKey : function(key){    
     return key in this.map;
    },
    containsValue : function(value){    
     for(var prop in this.map){
      if(this.map[prop] == value) return true;
     }
     return false;
    },
    isEmpty : function(key){    
     return (this.size() == 0);
    },
    clear : function(){   
     for(var prop in this.map){
      delete this.map[prop];
     }
    },
    remove : function(key){    
     delete this.map[key];
    },
    keys : function(){   
        var keys = new Array();   
        for(var prop in this.map){   
            keys.push(prop);
        }   
        return keys;
    },
    values : function(){   
     var values = new Array();   
        for(var prop in this.map){   
         values.push(this.map[prop]);
        }   
        return values;
    },
    size : function(){
      var count = 0;
      for (var prop in this.map) {
        count++;
      }
      return count;
    }
};

var string = {
	/**
	 * str이 undefined이거나 null이면 "" return. 아니면 str.toString() return
	 * @param str 체크할 obj
	 */
	strNvl : function(str) {
		if (undefined == str || null == str || str == "undefined" || str == "null") {
			return "";
		} else {
			return str.toString();
		}
	}
};

var loadingbar = {
	// spinner
	spinner : null,
	
	// loading bar options
	opts : {
		lines: 10, // The number of lines to draw
	    length: 7, // The length of each line
	    width: 4, // The line thickness
	    radius: 10, // The radius of the inner circle
	    corners: 1, // Corner roundness (0..1)
	    rotate: 0, // The rotation offset
	    color: '#000', // #rgb or #rrggbb
	    speed: 1, // Rounds per second
	    trail: 60, // Afterglow percentage
	    shadow: false, // Whether to render a shadow
	    hwaccel: false, // Whether to use hardware acceleration
	    className: 'spinner', // The CSS class to assign to the spinner
	    zIndex: 2e9, // The z-index (defaults to 2000000000)
	    position: 'fixed'
	},
	
	// 실행한 횟수
	spinnerCnt : 0,
	
	getSpinner : function (){
		if(!this.spinner){
			this.spinner = new Spinner(this.opts);
		}
		return this.spinner;
	},
	
	getSpinDiv : function(){
		var spinDivId = "spinDiv";
		var spinDiv = document.getElementById(spinDivId);
		if(!spinDiv){
			spinDiv = document.createElement("div");
			spinDiv.id = spinDivId;
			spinDiv.name = spinDivId;
			document.body.appendChild(spinDiv);
		}
		return spinDiv;
	},
	
	showBackground : function(){
		var sb = document.createElement("div");
		sb.id = "spinBack";
		sb.name = "spinBack";
		sb.style.backgroundColor = "black";
		sb.style.position = "fixed";
		sb.style.top = "0px";
		sb.style.bottom = "0px";
		sb.style.left = "0px";
		sb.style.right = "0px";
		sb.style.opacity = 0.2;
		sb.style.zIndex = this.opts.zIndex-1;
		document.body.appendChild(sb);
	},
	
	hideBackground : function(){
		$("#spinBack").remove();
	},
	
	start : function(){
		this.spinnerCnt = this.spinnerCnt +1;
		if(this.spinnerCnt > 1) return;
		this.showBackground();
		var target = this.getSpinDiv();
		
		try{
			this.getSpinner().spin(target);
		}catch(e){
			this.hideBackground();
			//log.debug("loadingbar - not found spinner.");
			console.log("loadingbar - not found spinner.");
		}
	},
	
	stop : function(){
		if(this.spinnerCnt > 0){
			this.spinnerCnt = this.spinnerCnt -1;
		}
 		
		if(this.spinnerCnt > 0) return;
		
 		this.hideBackground();

		try{
			this.getSpinner().stop();
		}catch(e){}
	},
	
};

var Base64 = {

	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

	// public method for encoding
	encode : function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		while (i < input.length) {

		  chr1 = input.charCodeAt(i++);
		  chr2 = input.charCodeAt(i++);
		  chr3 = input.charCodeAt(i++);

		  enc1 = chr1 >> 2;
		  enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		  enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		  enc4 = chr3 & 63;

		  if (isNaN(chr2)) {
			  enc3 = enc4 = 64;
		  } else if (isNaN(chr3)) {
			  enc4 = 64;
		  }

		  output = output +
			  this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
			  this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

		}

		return output;
	},

	// public method for decoding
	decode : function (input)
	{
	    var output = "";
	    var chr1, chr2, chr3;
	    var enc1, enc2, enc3, enc4;
	    var i = 0;

	    input = input.replace(/[^A-Za-z0-9+/=]/g, "");

	    while (i < input.length)
	    {
	        enc1 = this._keyStr.indexOf(input.charAt(i++));
	        enc2 = this._keyStr.indexOf(input.charAt(i++));
	        enc3 = this._keyStr.indexOf(input.charAt(i++));
	        enc4 = this._keyStr.indexOf(input.charAt(i++));

	        chr1 = (enc1 << 2) | (enc2 >> 4);
	        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
	        chr3 = ((enc3 & 3) << 6) | enc4;

	        output = output + String.fromCharCode(chr1);

	        if (enc3 != 64) {
	            output = output + String.fromCharCode(chr2);
	        }
	        if (enc4 != 64) {
	            output = output + String.fromCharCode(chr3);
	        }
	    }

	    return output;
	}
};
