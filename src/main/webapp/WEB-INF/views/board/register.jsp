<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<div class="row">	
		<div class="col-12">
			<div class="card border-success mb-3" style="width:100%;">
				<div class="card-body">
					<form action="${ctxPath }/board/register" method="post"> 
						<input type="hidden" id="cateId" name="cateId" value="free">
						<div class="input-group mb-3">
						  <span class="input-group-text" style="width:15%;" id="basic-addon">제목</span>
						  <input type="text" class="form-control" name="title" placeholder="제목을 입력하세요.">
						</div>
						
						<table class="mb-3">
							<tr>
								<td>
									<span class="input-group-text">분류</span>
								</td>
								<td>
									<div class="dropdown dropend" style="display:inline-block">
										<select class="cate form-control mx-2" id="cateSelect">
											<c:forEach items="${cate }" var="c">
												<option value="${c.cateId }">${c.cateName }</option>
								          	</c:forEach>
										</select>
									</div>
								</td>
							</tr>
						</table>
						
						<div class="input-group mb-3">
						  <span class="input-group-text" style="width:15%;">내용</span>
						  <textarea class="form-control" name="content" placeholder="내용을 입력하세요." rows="15"></textarea>
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
						
						<div class="input-group mb-3">
						  <span class="input-group-text" id="basic-addon1" style="width:15%;">작성자</span>
						  <input type="text" class="form-control" name="nickName" readonly="readonly" value="${authInfo.memberNickName}"/>
						  <input type="hidden" class="form-control" name="writer" value="${authInfo.memberId }"/>
						</div>
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
						<button type="button" class="btn btn-outline-primary register">등록하기</button>
						<button type="button" class="btn btn-outline-info list">목록으로</button>
					</form>
				</div>	<!-- card-body 끝 -->
			</div>
		</div>
	</div>
</div>


<input type="hidden" name="pageNum" value="${param.pageNum }" >
<input type="hidden" name="amount" value="${param.amount }" >
<input type="hidden" name="type" value="${param.type }" >
<input type="hidden" name="keyword" value="${param.keyword }" >

<script src="${ctxPath}/resources/js/register.js"></script>

<%@ include file="../includes/footer.jsp"%>	

<script>
$(function(){
	$('.list').click(function(){
		let form = $('<form/>');
		let type = $('[name="type"]');
		let keyword = $('[name="keyword"]');
		if(type.val()&&keyword.val()){
			form.append(type).append(keyword);
		}
		form.attr('action', '${ctxPath}/board/list')
			.append($('[name="pageNum"]'))
			.append($('[name="amount"]'))
			.appendTo('body')
			.submit();
	})
	
	// 셀렉트 박스 엘리먼트와 hidden input 엘리먼트 가져오기
	var cateSelect = document.getElementById("cateSelect");
	var selectedCateIdInput = document.getElementById("cateId");

	// 셀렉트 박스 값이 변경될 때 실행되는 이벤트 리스너 추가
	cateSelect.addEventListener("change", function() {
	    // 선택한 cateId 값을 hidden input에 저장
	    var cateId = cateSelect.value;
	    selectedCateIdInput.value = cateId;
	    console.log(cateId);
	});
	
})
</script>

