<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/common/common.jsp" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시판</title>
</head>

<body>
    <div class="contents">
        <h2 class="doch2">게시판 관리</h2>
        <div class="periodbox">
            <div class="perliright">
                <strong>검색어 입력</strong>
                <select id="sel_search_cd">
                    <option value="01">제목</option>
                    <option value="02">내용</option>
                    <!-- <option value="03">작성자</option> -->
                </select>
                <div class="m_inpbox">
                    <input type="text" id="searchKeyword" />
                </div>
                <a class="inqury" href="#a" id="btn_search">검색</a>
            </div>
        </div>
        <div class="guid_num">
            <div class="gn_left">
                <em class="totalnum"></em>
            </div>
            <div class="gn_right">
                <em>페이지당 개수</em>
                <select id="sel_page_cnt">
                    <option value="10">10개</option>
                    <option value="30">30개</option>
                    <option value="50">50개</option>
                    <option value="100">100개</option>
                </select>
            </div>
        </div>
        <table class="table_style02" border="0" cellspacing="0" cellpadding="0">
            <colgroup class="col_myype01">
                <col width="10%">
                <col width="60%">
                <col width="30%">
            </colgroup>
            <thead>
                <tr>
                    <th class="m_none">No.</th>
                    <th>제목</th>
                    <th>등록일시</th>
                </tr>
            </thead>
            <tbody id="notice_list">

            </tbody>
        </table>
        <div class="right_btn">
            <a class="btnregi" href="#a" id="btn_register">등록하기</a>
        </div>
        <!-- paging -->
        <div class="paging" style="margin-left: 37%;">
        </div>
        <!-- //paging -->
    </div>
</body>
<script type="text/javascript">
    $(document).ready(function() {

        //게시판 목록 조회
        call.ajaxNoticeList("", "", "10", "1", true);

        //검색 버튼 클릭 이벤트
        $("#btn_search").on("click", function() {
            //게시판 검색
            process.searchList();
        });

        //패이지당 개수 change 이벤트
        $("#sel_page_cnt").on("change", function() {
            //게시판 검색
            process.searchList();
        });

        //Enter키로 검색 가능
        $("#searchKeyword").keyup(function(e) {
            if (e.keyCode == 13)
                process.searchList();
        });

        //등록하기 버튼 클릭 이벤트
        $("#btn_register").on("click", function() {
            //등록화면 이동
            form.goRegister($(this).parent().parent());
        });

        //목록 -> 상세 클릭 이벤트
        $("#notice_list").on("click", "a[name='title']", function() {
            //상세화면 이동
            form.goView($(this).parent().parent());
        });
    });

    var process = {
        //검색 조건 리스트
        searchList: function() {
            //공지사항 목록 조회
            call.ajaxNoticeList($("#sel_search_cd").val(), $("#searchKeyword").val(), $("#sel_page_cnt").val(), "1", true);
        },

        changePage: function(page) {
            //공지사항 목록 조회
            call.ajaxNoticeList($("#sel_search_cd").val(), $("#searchKeyword").val(), $("#sel_page_cnt").val(), page, false);
        }
    };

    /* call function Object */
    var call = {

        //게시판 목록,검색
        ajaxNoticeList: function(searchSel, searchKeyword, pageSize, pageIdx, paging) {
            var url = "/selectList";
            var functionName = "";
            var parameter = common.setCommonParam();

            //paging여부에 따라 callback url 지정
            if (paging == true) {
                //공지사항 목록 조회(목록, 페이징 처리)
                functionName = "callBack.ajaxNoticeList1(data)";
            } else {
                //공지사항 목록 조회(목록 처리)
                functionName = "callBack.ajaxNoticeList2(data)";
            }

            parameter.searchSel = searchSel; //검색분류
            parameter.searchKeyword = searchKeyword; //검색키워드
            parameter.pageSize = pageSize; //페이지 출력건수
            parameter.pageIdx = pageIdx; //현재페이지

            common.ajax(url, parameter, functionName, "POST");
        }
    };

    /* call back function Object */
    var callBack = {

        //게시판 목록,검색 (목록, 페이징 처리)
        ajaxNoticeList1: function(data) {
            var html = "";
            var list = data.data;
            var total = 0;
            if (list != null && list != "") {
                $.each(list, function(i, item) {
                    total = list[i].totalCnt; //전체 글 갯수
                    html += '<tr class="nonPix">'
                    html += '<td class="m_none">' + list[i].rnum + '</td>'
                    html += '<td class="tlf">'
                    html += '<a href="#a" name="title">' + list[i].title + '</a>';
                    html += '</td>'
                    html += '<td>' + list[i].cret_dt + '</td>'

                    html += '<input type="hidden" name="listseq" value="' + list[i].listseq + '" />';
                    html += '<input type="hidden" name="title" value="' + list[i].title + '" />';
                    html += '<input type="hidden" name="content" value="' + list[i].content + '" />';
                    html += '<input type="hidden" name="id" value="' + list[i].id + '" />';
                    html += '<input type="hidden" name="name" value="' + list[i].name + '" />';
                    html += '<input type="hidden" name="cretr_id" value="' + list[i].cretr_id + '" />';
                    html += '<input type="hidden" name="cret_dt" value="' + list[i].cret_dt + '" />';
                    html += '<input type="hidden" name="amdr_id" value="' + list[i].amdr_id + '" />';
                    html += '<input type="hidden" name="amd_dt" value="' + list[i].amd_dt + '" />';
                    html += '<input type="hidden" name="authCd" value="' + "${sessionScope.loginVO.userType}" + '" />';
                    html += '</tr>';

                });

            } else {
                total = 0;
            }

            //초기 리스트 항목 삭제
            $(".nonPix > td").remove();

            //검색한 리스트 출력
            $(".table_style02").append(html);

            //전체 건수
            $(".totalnum").text("전체 " + total + " 개 ");

            //페이징
            if (total == 0) {
                common.paging(1, $("#sel_page_cnt").val());
            } else {
                common.paging(Number(total), $("#sel_page_cnt").val());
            }
        },


        //게시판 목록,검색 (목록 처리)
        ajaxNoticeList2: function(data) {

            var html = "";
            var list = data.data;

            $.each(list, function(i, item) {

                html += '<tr class="nonPix">'
                html += '<td class="m_none">' + list[i].rnum + '</td>'
                html += '<td class="tlf">'
                html += '<a href="#a" name="title">' + list[i].title + '</a>';
                html += '</td>'
                html += '<td>' + list[i].cret_dt + '</td>'

                html += '<input type="hidden" name="listseq" value="' + list[i].listseq + '" />';
                html += '<input type="hidden" name="title" value="' + list[i].title + '" />';
                html += '<input type="hidden" name="content" value="' + list[i].content + '" />';
                html += '<input type="hidden" name="id" value="' + list[i].id + '" />';
                html += '<input type="hidden" name="name" value="' + list[i].name + '" />';
                html += '<input type="hidden" name="cretr_id" value="' + list[i].cretr_id + '" />';
                html += '<input type="hidden" name="cret_dt" value="' + list[i].cret_dt + '" />';
                html += '<input type="hidden" name="amdr_id" value="' + list[i].amdr_id + '" />';
                html += '<input type="hidden" name="amd_dt" value="' + list[i].amd_dt + '" />';
                html += '<input type="hidden" name="authCd" value="' + "${sessionScope.loginVO.userType}" + '" />';
                html += '</tr>';



            });

            //초기 리스트 항목 삭제
            $(".nonPix > td").remove();

            //검색한 리스트 출력
            $(".table_style02").append(html);
        }

    };

    /* submit function Object */
    var form = {
        //상세화면 이동
        goView: function($selected_content) {
            var action = "/listDetail";
            var parameter = new Object();

            parameter.listseq = $selected_content.find("input[name='listseq']").val();
            parameter.title = $selected_content.find("input[name='title']").val();
            parameter.content = $selected_content.find("input[name='content']").val();
            parameter.id = $selected_content.find("input[name='id']").val();
            parameter.name = $selected_content.find("input[name='name']").val();
            parameter.cretr_id = $selected_content.find("input[name='cretr_id']").val();
            parameter.cret_dt = $selected_content.find("input[name='cret_dt']").val();
            parameter.amdr_id = $selected_content.find("input[name='amdr_id']").val();
            parameter.amd_dt = $selected_content.find("input[name='amd_dt']").val();

            common.submit(parameter, action, "POST");
        },

        //등록화면 이동(작성자 및 권한)
        goRegister: function($selected_content) {
            var action = "/listRegister";
            var parameter = new Object();

            parameter.userId = "${sessionScope.maskUserId}"; //사용자아이디(작성자)
            parameter.authCd = $selected_content.find("input[name='authCd']").val(); //권한

            common.submit(parameter, action, "POST");
        }
    };
</script>

</html>