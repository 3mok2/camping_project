<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
      
      .card-img-top {
		  object-fit: contain; /* 이미지를 늘리거나 줄이지 않고 부모 요소에 맞게 조절합니다. */
		  width: 100%; /* 이미지가 카드 너비를 채우도록 설정합니다. */
		  height: 100%; /* 이미지 높이를 조절하십시오. 원하는 크기로 조절하세요. */
		  object-position: center; /* 이미지를 수직 및 수평 가운데 정렬합니다. */
		}
      
</style>

<div class="container">
<h1 style="font-family: 'Gugi', cursive; text-align:center;"><b>${cateTitle}</b></h1>
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
		<button id="regBtn" class="btn col-1 btn-xs btn-primary my-1">글등록</button>
	</sec:authorize>
	</div>
</div>
<div class="row">
	<div class="col-12">
		<div class="album py-5 bg-light">
		 <div class="container">
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:if test="${not empty list }"> 
					 	<c:forEach items="${list}" var="board">
						  <div class="col">
						    <div class="card h-100">
						    	<c:if test="${not empty board.galleryImg }">
						      		<img src="${ctxPath}/files/display?fileName=${board.galleryImg}" class="card-img-top" alt="..."/>
						      	</c:if>
						      	<c:if test="${empty board.galleryImg}">
						      		<img src="${ctxPath}/resources/images/user_image.png" class="card-img-top" alt="..."/>
						      	</c:if>
						      <div class="card-body">
						        <div class="badge text-bg-light my-1">${board.nickName }</div>
						        <h5 class="card-title">
							        <a class="move" href="${board.bno}" style="text-decoration:none; color:black;">
								        ${board.title} ${board.replyCnt == 0 ? '' : [board.replyCnt]}
							        </a>
						        </h5>
						        <p class="card-text" style="height:20px; text-overflow:ellipsis; overflow:hidden;">${board.content }</p>
						      </div>
						    </div>
						  </div>
				    	</c:forEach>
				    </c:if>
				    <c:if test="${empty list}">
				    	<tr><td colspan="5">게시물이 존재하지 않습니다.</td></tr>
				    </c:if>
				</div>
			</div>
		</div>
		<div style="float:right;" class="mx-10">
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
<div class="row">
	<div class="col-12">		
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
	</div>
</div>

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

<form id="listForm" action="${ctxPath}/board/${criteria.category.cateId}">
	<input type="hidden" name="pageNum" value="${p.criteria.pageNum}">
	<input type="hidden" name="amount" value="${p.criteria.amount}">
	<input type="hidden" name="cateName" value="${criteria.category.cateName}">
</form>

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
