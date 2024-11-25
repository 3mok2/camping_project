<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-12">
			<div class="card border-success mb-3" style="width:100%;">
				<div class="card-header">
					<c:if test="${empty cateTitle}">
						<h1 class="float-left" style="font-family: 'Gugi', cursive;"><b>전체글</b></h1>
					</c:if>
					<c:if test="${not empty cateTitle }">
						<h1 class="float-left" style="font-family: 'Gugi', cursive;"><b>${cateTitle}</b></h1>
					</c:if>
			          <div class="dropdown dropend">
						  <button class="btn btn-secondary dropdown-toggle btn-lg" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
						    게시판
						  </button>
						  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
						    <li><a class="dropdown-item" href="${ctxPath }/board">전체글</a></li>
					        <c:forEach items="${cate }" var="c">
				            <li><a class="dropdown-item" href="${ctxPath }/board/${c.cateId}" >${c.cateName }</a></li>
				          	</c:forEach>
						  </ul>
						</div>
						<div style="text-align:right;">
				          <sec:authorize access="isAuthenticated()">
				          	<button id="regBtn" class="btn btn-xs col-2 btn-primary" style="float:right;">글등록</button>
				          </sec:authorize>
				         </div>
				</div>
				<div class="card-body">
					<table class="table table-bordered table-hover">
					  <thead class="table text-center">
					    <tr>
					      <th scope="col">분류</th>
					      <th scope="col">제목</th>
					      <th scope="col">작성자</th>
					      <th scope="col">작성일</th>
<!-- 					      <th scope="col">수정일</th> -->
					      <th scope="col">추천</th>
					    </tr>
					  </thead>
					  	<tbody class="table-group-divider">
					  	<c:if test="${not empty list }"> 
						 	<c:forEach items="${list }" var="board">
							    <tr>
							      <td align="center" style="width:15%">
							      		${board.cateName }
							      </td>
							      <td style="width:40%;">
							      	<a class="move" href="${board.bno}" style="text-decoration:none; color:black;">
							      		<b>${board.title } ${board.replyCnt == 0 ? '' : [board.replyCnt]}</b>
						      		</a>
							      </td>
							      <td align="center">
							      	<span class="badge text-bg-light">${board.nickName }</span>
							      </td>
							      <td align="center">
							      <span class="badge text-bg-success"><tf:formatDateTime value="${board.regDate }" pattern="yyyy-MM-dd HH:mm"/></span>
							      </td>
<!-- 								  <td align="center"> -->
<%-- 								  <span class="badge text-bg-success"><tf:formatDateTime value="${board.updateDate }" pattern="yyyy-MM-dd HH:mm"/></span> --%>
<!-- 								  </td> -->
							      <td align="center">
							      <span class="badge rounded-pill text-bg-danger">${board.likeHit }</span>
							      </td>
							    </tr>
						    </c:forEach>
					    </c:if>
					    <c:if test="${empty list}">
					    	<tr><td colspan="5">게시물이 존재하지 않습니다.</td></tr>
					    </c:if>
					  </tbody>
				  </table>
				  <ul class="pagination justify-content-center">
					<c:if test="${p.prev }">
						<li class="page-item">
							<a class="page-link" href="${p.startPage-p.displayPageNum }">이전</a>
						</li>
					</c:if>
					<c:forEach begin="${p.startPage }" end="${p.endPage }" var="pageLink">
						<li class="page-item ${ pageLink == p.criteria.pageNum ? 'active':''}">
							<a href="${pageLink }" class="page-link">${pageLink}</a>
						</li>
					</c:forEach>
					<c:if test="${p.next }">
						<li class="page-item">
							<a class="page-link" href="${p.endPage+1}">다음</a>
						</li>
					</c:if>
				</ul>
				<div>
					<div style="float:left">
						<select class="amount form-control mx-4">
							<option value="5" ${criteria.amount==5 ? 'selected':''}>5개씩 보기</option>
							<option value="10" ${criteria.amount==10 ? 'selected':''}>10개씩 보기</option>
							<option value="15" ${criteria.amount==15 ? 'selected':''}>15개씩 보기</option>
						</select>
					</div>
						<div style="float:right;">
							<form class="my-3" id="searchForm" action="${ctxPath}/board/${criteria.category.cateId}">
								<div class="d-inline-block">
									<select name="type" class="form-control">
										<option value="" ${p.criteria.type == null ? 'selected' : '' }>------</option>
										<option value="T" ${p.criteria.type eq 'T' ? 'selected' : '' }>제목</option>
										<option value="C" ${p.criteria.type eq 'C' ? 'selected' : '' }>내용</option>
										<option value="W" ${p.criteria.type eq 'W' ? 'selected' : '' }>작성자</option>
										<option value="TC" ${p.criteria.type eq 'TC' ? 'selected' : '' }>제목+내용</option>
										<option value="TW" ${p.criteria.type eq 'TW' ? 'selected' : '' }>제목+작성자</option>
										<option value="TCW" ${p.criteria.type eq 'TCW' ? 'selected' : '' }>제목+내용+작성자</option>
									</select>
								</div>
								<div class="d-inline-block col-4">
									<input type="text" name="keyword" value="${p.criteria.keyword }" class="form-control">
								</div>
								<div class="d-inline-block">
									<button class="btn btn-primary">검색</button>
								</div>
								<div class="d-inline-block">
									<a href="${ctxPath}/board/list" class="btn btn-outline-info">새로고침</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div><!-- col 끝 -->
		</div> <!-- row 끝 -->
	</div> 
</div>
<form id="listForm" action="${ctxPath}/board/${criteria.category.cateId}">
	<input type="hidden" name="pageNum" value="${p.criteria.pageNum}">
	<input type="hidden" name="amount" value="${p.criteria.amount}">
	<input type="hidden" name="cateName" value="${criteria.category.cateName}">
</form>

<!-- Modal -->
<div class="modal fade" id="listModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">처리 결과</h4>
                <button type="button" class="close" data-bs-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                처리가 완료되었습니다.
            </div>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>
${authInfo.memberId}


<%@ include file="../includes/footer.jsp"%>
<script>

let result = "${result}";
let listForm = $('#listForm');
let searchForm = $('#searchForm');
let category = "${cateId}";

$(function(){
	var checkModal = function(result) {
		if(result=='') return;
		let operation = "${operation}";
		if(operation=='register') {
			$('.modal-body').html('게시글 ' + result +'번이 등록되었습니다.')
		} else if(operation=='modify') {
			$('.modal-body').html('게시글 ' + result +'번이 수정되었습니다.')
		} else if(operation=='remove') {
			$('.modal-body').html('게시글 ' + result +'번이 삭제되었습니다.')
		}
		
		$('#listModal').modal('show');
	}
	checkModal(result);
})

</script>

<script src="${ctxPath}/resources/js/list.js"></script>