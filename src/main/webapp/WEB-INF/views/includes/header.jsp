<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.memberVO" var="authInfo"/>
	<sec:authentication property="principal.memberVO.authList" var="authList"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/css2?family=Gugi&display=swap" rel="stylesheet">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://kit.fontawesome.com/2e9900d982.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script>
let ctxPath = '${ctxPath}';
let duplicateLogin = '${duplicateLogin}';

let csrfHeaderName = "${_csrf.headerName}"; 
let csrfTokenValue = "${_csrf.token}";
let memberId = "${authInfo.memberId}";
let auth = "${authList}";

$(document).ajaxSend(function(e, xhr, options){
	xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
})

if(duplicateLogin) {
	alert(duplicateLogin);
}

function checkExtension(fileName, fileSize) {
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$"); // 업로드 불가능한 파일 형식 지정
	let maxSize = 10485760; // 10MB
	if(fileSize > maxSize) {
		alert('파일 크기는 최대 10MB까지 업로드 가능합니다.');
		return false;
	}
	
	if(regex.test(fileName)) {
		alert('해당 종류의 파일은 업로드 할 수 없습니다.');
		return false;
	}
	return true;
}
</script>
</head>
  <body>
	<nav class="navbar navbar-expand bg-body-tertiary justify-content-between">
	  <div class="container-fluid">
        <ul class="navbar-nav">
        	<li class="nav-item">
		    	<a class="navbar-brand" href="${ctxPath}">
		    		<img class="rounded-circle" src="${ctxPath}/resources/images/mok_camp_samll.png">
		    	</a>
		    </li>
	        <li class="nav-item">
	          <a class="nav-link" aria-current="page" href="#"><h4>메인</h4></a>
	        </li>
	        <li class="nav-item dropdown">
	          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="font-size:20px;">
	            게시판
	          </a>
		        <ul class="dropdown-menu">
		            <li><a class="dropdown-item" href="${ctxPath }/board">전체글</a></li>
			        <c:forEach items="${cate }" var="c">
		            <li><a class="dropdown-item" href="${ctxPath }/board/${c.cateId}">${c.cateName }</a></li>
		          	</c:forEach>
	       		</ul>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" aria-current="page" href="${ctxPath }/menu/come"><h4>오시는 길</h4></a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" aria-current="page" href="${ctxPath }/deck/reservation"><h4>예약 현황</h4></a>
	        </li>
      	</ul>
      	<ul class="navbar-nav">
      		<sec:authorize access="hasRole('ADMIN')">
      			<li class="nav-item">
			       <a class="nav-link" href="${ctxPath}/deck/deckList">데크관리</a>
			    </li>
      		</sec:authorize>
      		<sec:authorize access="isAnonymous()">
				<li class="nav-item">
			       <a class="nav-link" href="${ctxPath}/login">로그인</a>
			    </li>
			    <li>
			    <a class="nav-link" href="${ctxPath}/join/step1">회원가입</a>
			    </li>
		    </sec:authorize>
		    <sec:authorize access="isAuthenticated()">
		        <li class="nav-item">
		          <a class="nav-link" href="${ctxPath }/myPage">마이페이지</a>
		        </li>
			    <li>
			    	<a class="nav-link logout" href="${ctxPath}/logout">로그아웃</a>
			    </li>
		    </sec:authorize>
		</ul>
	  </div>
	</nav>
<script>
$(function(){
	$('.logout').click(function(e){
		e.preventDefault();
		let form = $('<form>', {action:$(this).attr('href'), method:'post'});
		form.append($('<input>', {type : 'hidden', name : '${_csrf.parameterName}', value : '${_csrf.token}'}))
			.appendTo('body')
			.submit();
	});
})
</script>
<!-- As a link -->