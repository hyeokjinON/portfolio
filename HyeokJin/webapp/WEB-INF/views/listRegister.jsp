<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/common/common.jsp" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시판 등록</title>
</head>

<body>
    <div class="contents">
        <h2 class="doch2">게시판 관리 > 등록</h2>
        <table class="table_style01" border="0" cellpadding="0" cellspacing="0">
            <colgroup class="col_mtype02">
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
            </colgroup>
            <tbody>
                <tr>
                    <th>작성자</th>
                    <td colspan="3">관리자<%-- <%=request.getParameter("userId")%> --%></td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td colspan="3">
                        <input class="meminp_typ00 energyAdmin" type="text" id="title" maxlength="100" style="margin-bottom: 8px;" /> <em id="text_length">0/100</em>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3">
                        <textarea id=contents name="contents" class="textara"></textarea>
                    </td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                        <input type="file" id="file" name="file" />
                        <em class="cumtxt energyAdmin">파일용량 10MB 이하 파일만 등록 가능합니다.</em>
                        <div class="cumfile">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- 버튼 -->
        <div class="twobtn">
            <a class="adbtn01" href="#a" id="btn_cancle">취소</a>
            <a class="adbtn02 horv" href="#a" id="btn_submit">등록</a>
        </div>
        <!-- // 버튼 -->
    </div>
</body>
<script type="text/javascript" src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.ui.widget.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.fileupload.js'/>"></script>
<script type="text/javascript" src="/resources/smartEditor/static/js/service/HuskyEZCreator.js" charset="utf-8"></script>

<script type="text/javascript">
    $(document).ready(function() {
        editorLoding("", "");

        var selectFile; //불러온 파일명(파일찾기)
        var selectPath; //불러온 파일path
        var fileName;

        //취소 버튼 클릭 이벤트
        $("#btn_cancle").on("click", function() {
            //목록 페이지 이동
            location.href = "/mainList";
        });

        //글자수 체크
        $("#title").keyup(function() {
            var title = $(this).val();
            $("#text_length").html(title.length + '/100');
        });

        // 파일 업로드 
        $('#file').fileupload({
            url: '/fileUpload/file',
            dataType: 'json',
            change: function(e, data) {
                var uploadFile = data.files[0];

                if (uploadFile.size > 10485760) { // 10mb
                    alert('파일 용량은 10MB를 초과할 수 없습니다.');
                    return false;
                }

                if (uploadFile.name.search(/[/%]/) > 0) {
                    alert('파일명에 특수문자[/,%]를 사용할수 없습니다.');
                    return false;
                }

                var strFilePath = uploadFile.name;

                //정규식
                var RegExtFilter = /\.(png|jpg|jpeg|pdf|xlsx|xls|hwp|docx|doc|ppt|pptx|txt)$/i;

                if (strFilePath.match(RegExtFilter) == null) {
                    alert("허용하지 않는 확장자 입니다.");
                }

            },
            success: function(data) {
                selectFile = data.file;
                selectPath = data.path;
                if (data.resultCode == "0000") {
                    var html = "";
                    html += '<em>' + selectFile + '<a href="#a" name="cancle_file"><img src="/resources/images/common/btn_x.png" alt="삭제버튼" /></a><input type="hidden" name="fileList" value="' + selectFile + '"><input type="hidden" name="filePath" value="' + selectPath + '"></em>';

                    $(".cumfile").append(html);
                } else {
                    alert("유효한 확장자명이 아닙니다.");
                }
            },
            error: function(xhr, status, error) {
                alert('Error: ' + error);
            }
        });

        //업로드한 파일 취소(삭제)-x버튼
        $('.cumfile').on("click", "a[name='cancle_file']", function() {
            $(this).parent().remove();
        });
    });


    var call = {

        //공지사항 등록
        ajaxNoticeInsert: function(contentsValue) {
            var url = "/list/insert";
            var functionName = "callBack.ajaxNoticeInsert(data)";
            var parameter = common.setCommonParam();

            parameter.userId = ""; //사용자 아이디(작성자)
            parameter.title = $("#title").val(); //제목
            parameter.content = contentsValue; //내용

            var fileName = "";
            $("input[name='fileList']").each(function() {
                fileName += $(this).val() + "|";
            });
            var selectPath = "";
            $("input[name='filePath']").each(function() {
                selectPath += $(this).val() + "|";
            });

            parameter.fileName = fileName; //각각의 파일명
            parameter.filePath = selectPath; //파일path

            common.ajax(url, parameter, functionName, "POST");
        }
    }


    var callBack = {

        //공지사항 등록
        ajaxNoticeInsert: function(data) {
            if (data.state == "S") {
                alert("등록 되었습니다.");
                //공지사항 목록 보여주기
                location.href = "/mainList";
            } else {
                alert("등록에 실패했습니다.");
            }
        }
    }
    // 스마트 에디터 API
    function editorLoding(title, contents) {
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: 'contents', // html editor가 들어갈 textarea id 입니다.
            sSkinURI: "/resources/smartEditor/static/SmartEditor2Skin.html", // html editor가 skin url 입니다.
            htParams: {
                // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseToolbar: true,
                // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseVerticalResizer: true,
                // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                bUseModeChanger: true,
                fOnBeforeUnload: function() {

                }
            },

            /**
             * 수정 시 에디터에 데이터 저장
             */
            fOnAppLoad: function() {
                //수정모드를 구현할 때 사용할 부분입니다. 로딩이 끝난 후 값이 체워지게 하는 구현을 합니다.
                oEditors.getById["contents"].exec("PASTE_HTML", [contents]); //로딩이 끝나면 contents를 txtContent에 넣습니다.
            },

            fCreator: "createSEditor2",
        });

        //저장 버튼 클릭 이벤트
        $("#btn_submit").on("click", function() {
            oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
            var contentsValue = document.getElementById('contents').value;
            //유효성 체크 및 등록
            if ($("#title").val() == "") {
                alert("제목을 입력해주세요.");
                $("#title").focus();
                return false;
            } else {
                // 등록
                call.ajaxNoticeInsert(contentsValue);
            }
        });
    }
</script>

</html>