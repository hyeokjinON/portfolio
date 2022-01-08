var paging = {
	/**
	 * Paging 공통 함수
	 * @param className 페이징 그려줄 클래스명
	 * @param pageNum 현재 페이지
	 * @param limit 한 페이지당 보여줄 리스트
	 * @param totalCount 전체 카운트
	 * @param pageSize 한 화면에서 보여줄 페이지 사이즈
	 * @param functionName 숫자 클릭시 호출할 클래스
	 */
	grid : function(className, pageNum, limit, totalCount, pageSize, functionName) {
		var totalPage = Math.floor(totalCount / limit) + (totalCount % limit == 0 ? 0 : 1);
		var pagingStart = ((Math.floor(Number(pageNum - 1) / pageSize)) * pageSize) + 1 ;
		
		var prevPage = (pageNum == 1) ? (pageNum) : (Number(pageNum) - 1);
		var nextPage = (pageNum == totalPage) ? (totalPage) : (Number(pageNum) + 1);
		$('.' + className).append('<a href="javascript:' + functionName + '(' + prevPage + ')"><span><img alt="" src="/images/enerdoc/btn_previous.png"></span></a>');
		for (i = pagingStart ; i < (pagingStart + pageSize) ; i++) {
			var _functionParam = functionName + '(' + i + ')';
			var _onClass = (pageNum == i) ? 'class="on"' : '';
			
			$('.' + className).append('<a href="javascript:' + _functionParam + '" ' + _onClass + '><span>' + i + '</span></a> ');
			if (i == totalPage) {
				break;
			}
		}
		$('.' + className).append('<a href="javascript:' + functionName + '(' + nextPage + ')"><span><img alt="" src="/images/enerdoc/btn_next.png"></span></a>');
	}
};
