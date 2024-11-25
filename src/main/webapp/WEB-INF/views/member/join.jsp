<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<style>
.join_area {
	height: 50vh
}	
</style>

<div class="container join_area d-flex justify-content-center align-items-center">
	<div class="w-50">
		<h1 class="text-center py-3">회원가입</h1>
		<form:form action="${ctxPath}/member/join" modelAttribute="memberVO">
			<div class="form-group row">
				<div class="col-9">
					<form:input class="form-control" path="memberId" placeholder="아이디"/>
				</div>
				<div class="col-3">
					<button type="button" class="btn btn-outline-info form-control idCheck">ID중복확인</button>
				</div>
			</div>
			<div class="form-group">
				<form:input class="form-control"  path="memberName" placeholder="이름"/>
			</div>
			<div class="form-group">
				<form:input class="form-control"  path="memberNickName" placeholder="닉네임"/>
			</div>
			<div class="form-group">
				<form:input class="form-control"  path="memberPhone" placeholder="휴대폰 -제외하고 입력"/>
			</div>
			<div class="form-group">
				<form:input class="form-control"  path="email" placeholder="이메일"/>
			</div>
			<div class="form-group">
				<form:password class="form-control"  path="memberPwd" placeholder="비밀번호"/>
			</div>
			<button type="button" class="form-control btn btn-outline-primary join" >회원가입</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form:form>
	</div>
</div>

<script>
$(function() {
	let idCheckFlag = false; 
	
	
	
	$('.idCheck').click(function() { // id 체크
		let idInput = $('#memberId')
		let memberId = $('#memberId').val();
	
		if(idInput.attr('readonly')){
			idInput.attr('readonly', false);
			idInput.focus();
			$(this).html('ID중복확인');
			idCheckFalg = false;
			return;
		}
		
		if(memberId=='') {
			alert('아이디를 입력하세요.')
			return;
		}
		
		$.ajax({
			type : 'post',
			url : '${ctxPath}/member/idCheck',
			data : {memberId : memberId},
			async : false,
			success : function(result) {
				if(result){
					alert('사용할 수 있는 아이디 입니다.')
					idCheckFlag = true;
					$('.idCheck').html('변경'); // ID중복확인을 변경으로 바꿈
					idInput.attr('readonly', true); // readonly로 바꿈
				} else {
					alert('사용할 수 없는 아이디입니다.');
					idInput.focus(); // idInput으로 보냄
				}
			}
		});
	});
	
	$('.join').click(function(){
		if(!idCheckFlag){
			alert('ID 중복체크 바람');
			return;
		}
		$('#memberVO').submit();
	});
});

</script>