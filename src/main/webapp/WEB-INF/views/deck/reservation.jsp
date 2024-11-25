<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-12">
		<div class="album py-5 bg-light">
		 <div class="container">
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:if test="${not empty list }"> 
					 	<c:forEach items="${list}" var="deck">
						  <div class="col">
						    <div class="card h-100">   
						      <div class="card-body">
						        <h5 class="card-title my-auto" style="text-align:center;">
					        		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
						        	<form id="deckForm" action="${ctxPath}/deck/decks">
								        <a class="move" href="${deck.dno }" style="text-decoration:none; color:black;">
								        	<b>${deck.deckName }</b>
								        </a>
							       	</form>
						        </h5>
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
	</div>
</div>
<%@ include file="../includes/footer.jsp"%>
<script>
let deckForm = $('#deckForm');

$('.move').click(function(e){
	e.preventDefault();
	let dnoValue = $(this).attr('href');
	deckForm.append($('<input/>', {type : 'hidden', name : 'dno', value : dnoValue}))
			.attr('action', '${ctxPath}/deck/decks')
			.submit();
});
</script>