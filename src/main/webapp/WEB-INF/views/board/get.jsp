<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	<div class="row">	
		<div class="col-12">
			<div class="card border-success mb-3" style="width:100%;">
				<div class="card-header">
					<div>
						<h1 class="float-left">글 목록 페이지</h1>
					</div>
					<sec:authorize access="isAuthenticated()">
					<div style="text-align:right;">
						<a class="btn btn-outline-danger like float-right">추천</a>
					</div>
					</sec:authorize>
				</div>
				<div class="card-body">
					<div class="form-group input-group mb-3">
					  <span class="input-group-text" style="width:15%;" id="basic-addon">분류</span>
					  <input type="text" class="form-control" readonly="readonly" value="${board.cateName}">
					</div>
					<div class="form-group input-group mb-3">
					  <span class="input-group-text" style="width:15%;" id="basic-addon">제목</span>
					  <input type="text" class="form-control" readonly="readonly" value="${board.title }">
					</div>
					<div class="form-group input-group mb-3">
					  <span class="input-group-text" style="width:15%;">내용</span>
					  <textarea class="form-control" readonly="readonly" aria-label="With textarea" rows="15">${board.content }</textarea>
					</div>
				
					<div class="form-group input-group mb-3">
					  <span class="input-group-text" id="basic-addon1" style="width:15%;">작성자</span>
					  <input type="text" class="form-control" readonly="readonly" value="${board.nickName }"/>
					</div>
					<div class="getBtns"> <!-- 버튼 전체에 이벤트가 걸려있어 다른버튼 활용 방해됨. -->
					<sec:authorize access="isAuthenticated() and principal.username == #board.writer or hasRole('ROLE_ADMIN')"> <!-- 글 작성자나 관리자만 -->  
						<button type="button" data-oper='modify' class="btn btn-primary">수정하기</button>
					</sec:authorize>
						<button type="button" data-oper='list' class="btn btn-outline-info" style="float:right;">목록으로</button>
					</div>
					
					<div class="row my-5">
						<div class="col-lg-12">
							<div class="card">
								<div class="card-header">
									<h4>첨부 파일</h4>
								</div>
								<div class="card-body">
									<div class="uploadResultDiv mt-3"> <!-- 파일업로드 결과 보여주기  -->
										<ul class="list-group"></ul>
									</div>
								</div> <!-- card-body -->
							</div> <!-- card end -->
						</div> <!-- col end -->
					</div><!-- row end -->
					
					<h3 class="mt-5">댓글</h3>
					<div class="row">
						<div class="col-12">
							<ul class="list-group chat">
								<li class="list-group-item" data-rno="댓글번호" >
									<div class="d-flex justify-content-between">
									  <div class="d-flex">
									    <div class="user_image mr-3" style="width: 75px">
									      <img class="rounded-circle" src="${ctxPath}/resources/images/user_image.png" style="width: 100%">
									    </div>
									    <div class="comment_wrap">
									      <div class="comment_info">
									        <span class="userName badge badge-pill badge-info mr-2">홍길동</span>
									        <span class="badge badge-dark">2023-06-20 09:30</span>
									      </div>
									      <div class="comment_content py-2">댓글 내용입니다. </div>
									    </div>
									  </div>
									  <div class="reply_modify">
									    <button type="button" class="btn btn-light dropdown-toggle" data-bs-toggle="dropdown">변경</button>
									    <div class="dropdown-menu">						
									      <a class="dropdown-item" href="modify">수정</a>
									      <a class="dropdown-item" href="delete">삭제</a>
									    </div>
									  </div>
									 </div>
								</li>
							</ul>		
						</div>
					</div>
					
					<div class="row mt-3 justify-content-center">
						<div class="col-12 pagination_wrap"></div>
					</div>
					
					
					<!-- 댓글작성 -->	
					<div class="my-3 replyWriterForm">
						<sec:authorize access="isAnonymous()">
							<textarea  rows="6" placeholder="로그인한 사용자만 댓글을 쓸 수 있습니다." readonly="readonly" 
								maxlength="400" class="replyContent form-control"></textarea>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<textarea  rows="6" placeholder="댓글을 작성해주세요" 
								maxlength="400" class="replyContent form-control"></textarea>
							<div class="text-right">
								<div class="submit p-2">
									<span class="btn btn-outline-info col-2 replierNickName">${authInfo.memberNickName }</span>
									<input type="hidden" class="replier" name="replier" value="${authInfo.memberId }"/>
									<button class="btn btn-outline-primary col-3">등록</button>
								</div>
							</div>
						</sec:authorize>						
					</div>
				</div>			
			</div>
		</div>
	</div>
</div>

<form>
	<input type="hidden" id="bno" name="bno" value="${board.bno }">
</form>

<!-- 원본 이미지 모달창 -->
<div class="modal fade" id="showImage">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
	        <div class="modal-header">
	            <h4 class="modal-title">원본 이미지 보기</h4>
	            <button type="button" class="close" data-bs-dismiss="modal">&times;</button>
	        </div>
	        <div class="modal-body"></div>
        </div>
    </div>
</div>


<h2>${board.bno}</h2>
<%@ include file="../includes/footer.jsp"%>	

<script>
$(function(){
	// 목록, 수정 페이지로
	let getForm = $('form');
	let category ="${category}";
	
	$('.getBtns button').click(function(){ <!-- 버튼 전체에 이벤트가 걸려있어 다른버튼 활용 방해됨. -->
		let operation = $(this).data('oper');
		let type = '${criteria.type}';
		let keyword = '${criteria.keyword}';
		
		getForm.append($('<input/>', {type : 'hidden', name : 'pageNum', value : '${criteria.pageNum}'}))
				.append($('<input/>', {type : 'hidden', name : 'amount', value : '${criteria.amount}'}))
				.attr('method', 'get');
	
		if(type && keyword) { // 조회에서 목록으로
			getForm.append($('<input/>', {type : 'hidden', name : 'type', value : '${criteria.type}'}))
					.append($('<input/>', {type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
		}
		
		if (operation=='list') {
			getForm.find('#bno').remove();
			getForm.attr('action', '${ctxPath}/board/${criteria.category.cateId}');
		} else if (operation=='modify') {
			getForm.append($('<input/>',{type : 'hidden', name : 'category.cateId', value : '${criteria.category.cateId}'}))
					.attr('action', '${ctxPath}/board/modify');
					
		}
		getForm.submit();
	})
		
	$('.like').click(function(e){
		e.preventDefault();
		let bno = $('[name="bno"]').val();
		
		$.ajax({
			type : 'post',
			url : '${ctxPath}/board/like',
			data : {memberId : memberId, bno : bno},
			success : function(message){
				alert(message);
				isLike();
			}
		})
	});
	
	function isLike(){
		let bno = $('[name="bno"]').val();
		$.ajax({
			type : 'post',
			url : '${ctxPath}/board/isLike',
			data : {memberId : memberId, bno : bno},
			success : function(result){
				if(result) {
				$('.like').html('추천취소');
				} else {
					$('.like').html('추천');
				}
			}
		})
	}
	
	if(memberId!=''){
		isLike();
	}
});

</script>
<script src="${ctxPath}/resources/js/replyService.js"></script>
<script src="${ctxPath}/resources/js/reply.js"></script>
