<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<script>
	var reserveArray = '${reserve}';
</script>
<div class="modal fade" id="calModal" tabindex="-1" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">추가</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
        <form class="insertScheduleForm" action="${ctxPath}/deck/insertSchedule" method="post">
	      <div class="modal-body">
	          <div class="mb-3">
	            <div class="badge rounded-pill bg-success clickedYMD" style="font-size:1.0em;"></div>
	          	<div class="form-group">
		            <label for="recipient-name" class="col-form-label">일정 추가</label>
		            <input type="hidden" class="form-control" id="year" name="year">
		            <input type="hidden" class="form-control" id="month" name="month">
		            <input type="hidden" class="form-control" id="day" name="day">
		            <input type="hidden" class="form-control" id="ymd" name="ymd">
		            <input type="hidden" class="form-control" name="dno" value="${deck.dno}"/>
		            <input type="text" class="form-control" id="reserveComment" name="reserveComment">
	            </div>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
	        <button type="button" class="btn btn-primary insertSchedule">추가</button>
	      </div>
      	</form>
    </div>
  </div>
</div>

<script src="${ctxPath}/resources/js/calendar/Calendar.js"></script>

<div style="text-align:center; margin:auto;">
	<h1>캠핑장 약도</h1>
	<img src="${ctxPath}/resources/images/map.jpg" style="width:900px;">
</div>
<br>
<div class="row">
	<div class="col text-center">
		<div class="btn-group" style="width:300px;">
		 	<button class="btn btn-secondary btn-lg dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
		    	${deck.deckName }
		 	</button>
			<ul class="dropdown-menu">
			  <li><span class="dropdown-item-text"><b>💕데크 이름😊</b></span></li>
			  <c:forEach items="${list}" var="deck">
				 <form id="deckForm" action="${ctxPath}/deck/decks">
				 	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>		  
				  	<li><a class="dropdown-item move" href="${deck.dno }">${deck.deckName }</a></li>
				 </form>
			  </c:forEach>
			</ul>
		</div>
	</div>
</div>
<body>
<table id="calendar" align="center" width="800" height="400" style="font-size:15pt; table-layout:fixed;">
		<tr>
			<td align="center"><label onclick="prevCalendar()"> ◀ </label></td>
			<td colspan="5" align="center" id="calendarTitle">yyyy년 m월</td>
			<td align="center"><label onclick="nextCalendar()"> ▶ </label></td>
		</tr>
		<tr>
			<td align="center"><font color ="#F79DC2">일</td>
			<td align="center">월</td>
			<td align="center">화</td>
			<td align="center">수</td>
			<td align="center">목</td>
			<td align="center">금</td>
			<td align="center"><font color ="skyblue">토</td>
		</tr>
		<script type="text/javascript">buildCalendar();</script>
	</table>
<br><br>
</body>
</html>

<%@ include file="../includes/footer.jsp"%>

<script>
let deckForm = $('#deckForm');
let insertSchedule = $('.insertScheduleForm');

$('.move').click(function(e){
	e.preventDefault();
	let dnoValue = $(this).attr('href');
	deckForm.append($('<input/>', {type : 'hidden', name : 'dno', value : dnoValue}))
			.attr('action', '${ctxPath}/deck/decks')
			.submit();
});

$('.insertSchedule').click(function(){
	console.log("추가 실행");
	insertSchedule.submit();
});

</script>