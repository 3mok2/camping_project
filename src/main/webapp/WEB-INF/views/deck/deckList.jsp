<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div>
	<table class="table" style="width:60%; margin-left:auto; margin-right:auto;">
	  <thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">이름</th>
	      <th scope="col" style="text-align:center;">
	      	<button type="button" class="btn btn-outline-success btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">추가+</button>
	      </th>
	    </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${deckList }" var="deck">
	    <tr>
	      <th scope="row">${deck.dno }</th>
	      <td>${deck.deckName }</td>
	      <td style="width:20%;">
      		<div style="text-align:center;">
	      	<button type="button" class="btn btn-outline-primary btn-sm modify" data-bs-toggle="modal" data-bs-target="#modifyModal"
      		data-dno="${deck.dno}" data-name="${deck.deckName }">수정</button>
	      	<button type="button" class="btn btn-outline-danger btn-sm remove" data-bs-toggle="modal" data-bs-target="#removeModal"
	      	data-dno="${deck.dno }">삭제</button>
	      	</div>
	      </td>
	    </tr>
	   </c:forEach>
	  </tbody>
	</table>
</div>
<form>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
</form>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">추가</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
        <form class="insertForm" action="${ctxPath}/deck/insertDeck" method="post">
	      <div class="modal-body">
	          <div class="mb-3">
	          	<div class="form-group">
		            <label for="recipient-name" class="col-form-label">데크 이름</label>
		            <input type="text" class="form-control" id="deckName" name="deckName">
	            </div>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
	        <button type="button" class="btn btn-primary insertDeck">추가</button>
	      </div>
      	</form>
    </div>
  </div>
</div>

<div class="modal fade" id="modifyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">수정</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
        <form class="updateForm" action="${ctxPath}/deck/updateDeckName" method="post">
	      <div class="modal-body">
	          <div class="mb-3">
	          	<div class="form-group">
					<div id="dnoDisplay"></div>
		            <label for="recipient-name" class="col-form-label">데크 이름</label>
		            <input type="hidden" class="form-control" id="modifyDno" name="dno">
		            <input type="text" class="form-control" id="modifyDeckName" name="deckName">
	            </div>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
	        <button type="button" class="btn btn-primary updateDeck">수정</button>
	      </div>
      	</form>
    </div>
  </div>
</div>

<div class="modal" id="removeModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">삭제</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form class="removeForm" action="${ctxPath}/deck/removeDeck" method="post">
      <div class="modal-body">
      	삭제 하시겠습니까?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <input type="hidden" class="form-control" id="removeDno" name="dno">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
        <button type="button" class="btn btn-primary removeDeck">삭제</button>
      </div>
      </form>
    </div>
  </div>
</div>

<script>
$(document).ready(function(){
    // 버튼 클릭 시 모달 열기
    $(".modify").click(function(){
    	var dno = $(this).data('dno');
    	var deckName = $(this).data('name');
    	
    	console.log(dno);
    	console.log(deckName);
    	
    	$("#modifyDeckName").val(deckName);
    	$("#modifyDno").val(dno);
    	
        $("#modifyModal").modal();
    });
    
    $(".remove").click(function(){
    	var dno = $(this).data('dno');
    	
    	$("#removeDno").val(dno);
    	
    	$("#removeModal").modal();
    })
});

let insertForm = $('.insertForm');
let updateForm = $('.updateForm');
let removeForm = $('.removeForm');

$('.insertDeck').click(function(){
	console.log("추가 실행");
	insertForm.submit();
});

$('.updateDeck').click(function(){
	updateForm.submit();
})

$('.removeDeck').click(function(){
	removeForm.submit();
})
</script>
