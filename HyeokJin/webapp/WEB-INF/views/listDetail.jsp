<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/common/common.jsp" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시판 상세화면</title>
</head>

<body>
    <input hidden="hidden" id="listseq" value="<c:out value='${listseq}'/>" />
    <div class="contents">
        <h2 class="doch2">게시판 관리 > 상세</h2>
        <table class="table_style01" border="0" cellpadding="0" cellspacing="0">
            <colgroup class="col_mtype02">
                <!-- 				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%"> -->
            </colgroup>
            <tbody>
                <tr>
                    <th>작성자</th>
                    <td colspan="3">관리자</td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td id="tdTitleNm" colspan="3"><%=request.getParameter("title") %></td>
                    <select class="select_typ02 energyAdmin" id="title_type" style="margin-bottom: 8px;display:none">
                    </select>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3">
                        <textarea id="contents" name="contents" class="textara"></textarea>
                    </td>
                </tr>
                <tr id="file_update_show">
                    <th>첨부파일</th>
                    <td colspan="3">
                        <input type="file" id="file" name="file" />
                        <em class="cumtxt">파일용량 10MB 이하 파일만 등록 가능합니다.</em>
                        <div class="cumfile">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>글 삭제하기</th>
                    <td colspan="3">
                        <em class="mr20">
                            <input id="trm01" type="radio" name="radio_delete" value="Y">
                            <label for="trm01">YES</label>
                        </em>
                        <em>
                            <input id="trm02" type="radio" name="radio_delete" value="N" checked="checked">
                            <label for="trm02">NO</label>
                        </em>
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- 버튼 -->
        <div class="twobtn">
            <a class="adbtn01" href="#a" id="btn_cancle">취소</a>
            <a class="adbtn02 horv" href="#a" id="btn_submit">저장</a>
        </div>
        <!-- // 버튼 -->
    </div>
</body>
<script type="text/javascript" src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.ui.widget.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.fileupload.js'/>"></script>
<script type="text/javascript" src="/resources/smartEditor/static/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
    var listseq; //해당 게시글 일련번호

    var selectFile; //불러온 파일(파일찾기)
    var selectPath; //불러온 파일path
    var fileName;

    $(document).ready(function() {
        //해당 게시물 일련번호
        listseq = $("#listseq").val();

        //게시판 상세보기
        call.ajaxNoticeSelect($("#listseq").val());

        //취소 버튼 클릭 이벤트
        $("#btn_cancle").on("click", function() {
            //목록 페이지 이동
            location.href = "/mainList";
        });

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
                    html += '<em><a href="#" name="fileDown_btn">' + selectFile + '</a><img src="/resources/images/common/btn_x.png" alt="삭제버튼"  name="cancle_file"/><input type="hidden" name="addfileList" value="' + selectFile + '"/><input type="hidden" name="addfilePath" value="' + selectPath + '"/></em>';

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
        $('.cumfile').on("click", "img[name='cancle_file']", function() {
            $(this).parent().remove();
        });

        //첨부파일 다운로드
        $('.cumfile').on("click", "a[name='fileDown_btn']", function() {
            fileName = $(this).find("input[name='fileList']").val();
            filePath = $(this).find("input[name='filePath']").val();
            //파일다운로드 실행
            form.submitDownload();
        });

    });

    var call = {

        //공지사항관리 상세
        ajaxNoticeSelect: function(listseq) {
            var url = "/list/selectDetail";
            var functionName = "callBack.ajaxNoticeSelect(data)";
            var parameter = common.setCommonParam();

            parameter.listseq = listseq;

            common.ajax(url, parameter, functionName, "POST");
        },

        //공지사항관리 수정
        ajaxNoticeUpdate: function(contentsValue) {
            var url = "/list/update";
            var functionName = "callBack.ajaxNoticeUpdate(data)";
            var parameter = common.setCommonParam();

            parameter.userId = ""; //사용자아이디(작성자)
            parameter.listseq = listseq; //게시물 일련번호
            parameter.upldSeq = listseq; //파일 맵핑 일련번호
            parameter.content = contentsValue;

            var fileName = "";
            $("input[name='fileList']").each(function() {
                fileName += $(this).val() + "|";
            });
            $("input[name='addfileList']").each(function() {
                fileName += $(this).val() + "|";
            });

            var file_path = "";
            $("input[name='filePath']").each(function() {
                file_path += $(this).val() + "|";
            });
            $("input[name='addfilePath']").each(function() {
                file_path += $(this).val() + "|";
            });

            parameter.fileName = fileName; //각각의 파일명
            parameter.filePath = file_path; //파일path

            common.ajax(url, parameter, functionName, "POST");
        },

        //공지사항관리 삭제
        ajaxNoticeDelete: function() {
            var url = "/list/delete";
            var functionName = "callBack.ajaxNoticeDelete(data)";
            var parameter = common.setCommonParam();

            parameter.listseq = listseq; //게시물 일련번호

            common.ajax(url, parameter, functionName, "POST");
        }

    };

    /* call back function Object */
    var callBack = {

        //공지사항 관리 상세
        ajaxNoticeSelect: function(data) {
            var list = data.data;
            var html = "";
            var content = "";
            for (var i = 0; i < list.length; i++) {
                content = list[0].content;
                //$("#tdTitleNm").val(list[0].title); // 제목
                if (list[i].file_nm != null && list[i].file_nm != '') {
                    html += '<em><a href="#" name="fileDown_btn">' + list[i].file_nm + '<input type="hidden" name="fileList" value="' + list[i].file_nm + '"><input type="hidden" name="filePath" value="' + list[i].file_path + '"/></a><img src="/resources/images/common/btn_x.png" alt="삭제버튼"  name="cancle_file"/></em>';
                }
            }
            $(".cumfile").html(html);

            editorLoding("", content);

        },

        //공지사항관리 수정
        ajaxNoticeUpdate: function(data) {
            if (data.state == "S") {
                alert("수정 되었습니다.");
                location.href = "/mainList";
            } else {
                alert("수정에 실패했습니다.");
            }

        },

        //공지사항관리 삭제
        ajaxNoticeDelete: function(data) {
            if (data.state == "S") {
                alert("삭제 되었습니다.");
                location.href = "/mainList";
            } else {
                alert("삭제에 실패했습니다.");
            }
        }

    };

    var form = {
        //파일다운로드
        submitDownload: function() {
            var action = "/fileDownload/download";
            var parameter = new Object();

            parameter.fileName = fileName;
            parameter.path = filePath;

            common.submit(parameter, action, "POST");
        }

    };

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
            if ($("input:radio[name=radio_delete]:checked").val() == "Y") {
                if (confirm("확인을 누르면 삭제가 진행됩니다")) {
                    call.ajaxNoticeDelete();
                    return;
                }
            } else {
                //공지사항 수정 실행
                call.ajaxNoticeUpdate(contentsValue);
            }
        });
    }
</script>

</html>