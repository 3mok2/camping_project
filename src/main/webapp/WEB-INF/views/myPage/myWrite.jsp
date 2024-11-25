<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="container">
	<ul class="list-group list-group-horizontal justify-content-center mt-3">
		<li class="list-group-item">
			<a href="${ctxPath}/myPage">회원정보변경</a>
		</li>
		<li class="list-group-item">
			<a href="${ctxPath}/myPage/myWrite">내가 쓴 글</a>
		</li>
		<li class="list-group-item">
			<a href="${ctxPath}/myPage/myReplies">내가 쓴 댓글</a>
		</li>
		<li class="list-group-item">
			<a href="${ctxPath}/myPage/myLike">좋아요 한 글</a>
		</li>
	</ul>
	<div class="d-flex justify-content-center">
		<div class="w-50 my-5">
			<div class="jumbotron">
				<div class="card">
				  <ul class="list-group list-group-flush">
				  	<c:if test="${not empty listById }">
				 		<c:forEach items="${listById }" var="list">
						    <li class="list-group-item">
							    <a class="move" href="${list.bno}" style="text-decoration:none; color:black;">
					      		<b>${list.title } ${list.replyCnt == 0 ? '' : [list.replyCnt]}</b>
					      		</a>
				      		</li>
				    	</c:forEach>
				    </c:if>
				    <c:if test="${empty list}">
				    	<tr><td colspan="5">게시물이 존재하지 않습니다.</td></tr>
				    </c:if>
				  </ul>
				</div>
			</div>
		</div>
	</div>
</div>
<form id="listForm" action="${ctxPath}/board/get">

</form>
<script>
let listForm = $('#listForm');

$(function(){
	
	$('.move').click(function(e){
			e.preventDefault();
			let bnoValue = $(this).attr('href');
			listForm.append($('<input/>', {type : 'hidden', name : 'bno', value : bnoValue}))
					.attr('action','${ctxPath}/board/get')
					.submit();
		});
});
</script>