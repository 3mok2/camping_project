<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">	
		<div class="col-12">
			<div class="card border-success mb-3" style="width:100%;">
				<div class="card-header">
					<h1 class="float-left">글 수정 페이지</h1>
				</div>
				<div class="card-body">
					<form class="modifyForm" action="${ctxPath }/board/modify" method="post"> 
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="form-group">
							<input type="hidden" class="form-control" name="bno" value="${board.bno}"/>
						</div>
						<div class="form-group input-group mb-3">
						  <span class="input-group-text" style="width:15%;">제목</span>
						  <input type="text" class="form-control" name="title" value="${board.title }">
						</div>
		
						<div class="form-group input-group mb-3">
						  <span class="input-group-text" style="width:15%;">내용</span>
						  <textarea class="form-control" name="content" rows="15">${board.content}</textarea>
						</div>
						
						<table class="mb-3">
							<tr>
								<td>
									<span class="input-group-text">파일첨부</span>
								</td>
								<td>
									<div class="uploadDiv">
										<input type="file" name="uploadFile" multiple class="form-control">
									</div>
								</td>
							</tr>
						</table>
						<div class="uploadResultDiv mb-3"> <!-- 파일업로드 결과 보여주기  -->
							<ul class="list-group"></ul>
						</div>
						
						<div class="form-group input-group mb-3">
						  <span class="input-group-text" style="width:15%;">작성자</span>
						  <input type="text" class="form-control" name="writer" readonly="readonly" value="${board.writer }">
						</div>
						<button type="button" data-oper='modify' class="btn btn-primary">등록</button>
						<button type="button" data-oper='remove' class="btn btn-danger">삭제</button>
						<button type="button" data-oper='list' class="btn btn-outline-info" style="float:right;">목록으로</button>
					</form>
				</div>	<!-- card-body 끝 -->
			</div>
		</div>
	</div>
</div>
<%@ include file="../includes/footer.jsp"%>

<div class="modal fade" id="showImage">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
	        <div class="modal-header">
	            <h4 class="modal-title">원본 이미지 보기</h4>
	            <button type="button" class="close" data-dismiss="modal">&times;</button>
	        </div>
	        <div class="modal-body"></div>
        </div>
    </div>
</div>

<script>
let modifyForm = $('.modifyForm');	
let type = '${criteria.type}';
let keyword = '${criteria.keyword}';
let category= "${category}";

let addCriteria = function(){
	modifyForm.append($('<input/>',{type : 'hidden', name : 'category.cateId', value : '${criteria.category.cateId}'}))
			  .append($('<input/>',{type : 'hidden', name : 'pageNum', value : '${criteria.pageNum}'}))
		   	  .append($('<input/>',{type : 'hidden', name : 'amount', value : '${criteria.amount}'}))
	if(type&&keyword){
		modifyForm.append($('<input/>',{type : 'hidden', name : 'type', value : '${criteria.type}'}))
	   	  		.append($('<input/>',{type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
	}
}
</script>

<script src="${ctxPath}/resources/js/modify.js"></script>