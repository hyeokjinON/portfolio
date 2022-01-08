<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/common/common.jsp" %>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 관련</title>
</head>
<body>
	<div class="loginpt">
	    <div class="logb_m">
	        <input type="text" placeholder="아이디" id="user_id" />
	        <input type="password" placeholder="비밀번호" id="user_password" />
	        <a class="accbtn" href="#a">로그인</a>
	    </div>
	    <em style="display: block; margin-bottom: 12px; color: #333333;">
	        <input id="idm" type="checkbox" style="display: unset; width: 18px; height: 18px; margin-right: 5px;" />
	        <label for="idm">아이디 저장</label>
	    </em>
	</div>
</body>
<script type="text/javascript">
//초기화 아이디 저장 확인
if (cookie.getCookie("idm") != "") {
    $("#user_id").val(cookie.getCookie("idm"));
    $("#idm").prop("checked", true);
}
//아이디저장
if ($("#idm").prop("checked") == true) {
    cookie.setCookie("idm", $("#user_id").val(), 60 * 24 * 365);
} else {
    cookie.deleteCookie("idm");
}
</script>
</html>