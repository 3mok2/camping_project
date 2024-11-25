console.log('deckList.js');

var today = new Date();

var inputString = reserveArray;

var j = 0; // 배열값 j를 바깥에다 선언하여 월이 넘어가도 j가 지속되도록 함.
var a = 0;

// 문자열을 배열로 분할
var parts = inputString.split('), ');

// reserveComment 값을 추출하여 새로운 배열에 저장
var reserveCommentArray = [];

for (var i = 0; i < parts.length; i++) {
    var match = parts[i].match(/reserveComment=([^,]+)/);
    
    if (match) {
        var reserveComment = match[1];
        reserveCommentArray.push(reserveComment);
    }
}

function buildCalendar() {
	var row = null;
	var cnt = 0;
	
	var calendarTable = document.getElementById("calendar");
	var calendarTableTitle = document.getElementById("calendarTitle");
	calendarTableTitle.innerHTML = today.getFullYear()+"년"+(today.getMonth()+1)+"월";
	
	var firstDate = new Date(today.getFullYear(), today.getMonth(), 1);
	var lastDate = new Date(today.getFullYear(), today.getMonth()+1, 0);
	
	while(calendarTable.rows.length > 2) {
		calendarTable.deleteRow(calendarTable.rows.length -1);
	}
	
	row = calendarTable.insertRow();
	for(i=0; i<firstDate.getDay(); i++) {
		cell = row.insertCell(i);
		cell.innerHTML = "";
		cnt += 1;
	}
	
	for(i=1; i<=lastDate.getDate(); i++){
		cell = row.insertCell();
		cnt += 1;
		
		var month = month >= 10 ? (today.getMonth() + 1) : '0' + (today.getMonth() + 1);
		
		var dateObj;
		if (i < 10) {
		    dateObj = today.getFullYear() + "-" + month + "-0" + i;
		} else {
		    dateObj = today.getFullYear() + "-" + month + "-" + i;
		}
		
		var isMatch = reserveArray.includes(dateObj.toString());
		
		cell.setAttribute('id', i);
		
		if(isMatch) {
			cell.innerHTML = "<font size=1px>예약불가</font>";
			j++;
		} else {
			cell.innerHTML = i; // if문 이용
		}
		
		cell.align="center";
		
		cell.onclick = function() {
			
			if(auth.includes('ROLE_ADMIN')) {
				clickedYear = today.getFullYear();
				clickedMonth = (1 + today.getMonth() );
				clickedDate = this.getAttribute('id');
				
				clickedDate = clickedDate >= 10 ? clickedDate : '0' + clickedDate;    
				clickedMonth = clickedMonth >= 10 ? clickedMonth : '0' + clickedMonth;
				clickedYMD = clickedYear + "-" + clickedMonth + "-" + clickedDate;
				
				var cellId = this.getAttribute('id');
				
				console.log('일: ' + clickedDate);
				console.log('월: ' + clickedMonth);
				console.log('년: ' + clickedYear);
				console.log('클릭 날짜: ' + clickedYMD);
				console.log(cellId);
				
				$("#year").val(clickedYear);
				$("#month").val(clickedMonth);
				$("#day").val(clickedDate);
				$("#ymd").val(clickedYMD);
				$(".clickedYMD").html(clickedYMD);
				$("#calModal").modal('show');
			} 
			
			else {
				alert("권한이 없습니다. ADMIN만 작동 가능합니다.");
			}
		
		}
		
	    if (cnt % 7 == 1) {
	    	if(isMatch) {
	    		j--;
				cell.innerHTML = "<font size=1px color=#F79DC2>예약불가</font>";
				j++;
			} else {
				cell.innerHTML = "<font color=#F79DC2>" + i + "</font>";
			}
	    }

	    if (cnt % 7 == 0){
	    	if(isMatch) {
	    		j--;
				cell.innerHTML = "<font size=1px color=skyblue>예약불가</font>";
				j++;
			} else {
				cell.innerHTML = "<font color=skyblue>" + i + "</font>"; // if문 이용
			}
	    	row = calendarTable.insertRow();
	    }
 	}
 	
	if(cnt % 7 != 0){
	  	for(i = 0; i < 7 - (cnt % 7); i++){
	  		cell = row.insertCell();
	  		cell.innerHTML = "";
		}
	}
};

function nextCalendar() {
	today = new Date(today.getFullYear(), today.getMonth()+1, 01);
	buildCalendar();
};


function prevCalendar() {
	today = new Date(today.getFullYear(), today.getMonth()-1, 01);
	buildCalendar();
};