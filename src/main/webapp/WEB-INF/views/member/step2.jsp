<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

<div class="container">
	
	<div class="row my-5">
		<div class="col-2">
			<ul class="list-group">
				<li class="list-group-item">이용약관</li>
				<li class="list-group-item active">이메일 인증</li>
				<li class="list-group-item">회원가입작성</li>
			</ul>		
		</div>
		<div class="col-10 d-flex justify-content-center">
			<div class="w-50">
				<form action="${ctxPath}/join/step3" method="post" id="authForm">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<h2 class="mb-5">이메일 인증</h2>
					<div class="form-group">
						<input type="email" class="form-control" name="email" id="email" placeholder="이메일">
					</div>
					<div class="form-group">
						<button type="button" class="form-control btn btn-outline-info" id="mailCheckBtn">인증번호요청</button>
					</div>
					<div class="form-group mt-5">
						<span class="resultMessage"></span>
						<input class="form-control checkInput" placeholder="인증번호 6자리 입력" disabled="disabled" maxlength="6">
					</div>
					<div class="form-group">
						<button type="button" class="form-control btn btn-outline-primary nextBtn">다음</button>
					</div>
				</form>
			</div>
		</div> <!-- col-10 end -->
	</div> <!-- row end -->
	
</div> <!-- container end -->

<script>
$(function(){
	
	
	let authForm = $('#authForm');
	let code = null;
	let submitFlag = {
			email : '',
			isAuth : false,
	};
	
	function RRRRRRRRRRightMail(email) {
		   const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		   return emailPattern.test(email);
		}
	
	$('#mailCheckBtn').click(function(){
		const email = $('#email').val();
		const checkInput = $('.checkInput');
		
		if(email=='') { 
			alert('이메일을 입력하세요.');
			return;
		}
		
		if(!RRRRRRRRRRightMail(email)) {
			alert('올바른 이메일을 입력하세요.')
			return;
		}
		
		$.ajax({
			type : 'get',
			url : '${ctxPath}/mailCheck?email='+email,
			success : function(result){
				console.log(result);
				console.log(email)
				
				submitFlag.email = email;
				checkInput.attr('disabled', false);
				code = result;
				alert('인증번호가 전송되었습니다.');
					
			}
		});
	});
	
	// 인증 일치 여부 확인
	$('.checkInput').on('keyup', function(){
		const inputCode = $(this).val();
		const resultMessage = $('.resultMessage');
		
		if(inputCode == code) {
			resultMessage.html('인증되었습니다.');
			submitFlag.isAuth = true;
			resultMessage.css('color','green');
			$(this).removeClass('border-danger')
				.addClass('border border-success')
				.css('box-shadow', '0 0 0 0.2rem rgba(0,128,0,.25)');
		} else {
			resultMessage.html('인증번호가 일치하지 않습니다.');
			submitFlag.isAuth = false;
			resultMessage.css('color','red');
			$(this).addClass('border border-danger')
				.css('box-shadow', '0 0 0 0.2rem rgba(255,0,0,.25)');
		}
	});
	
	$('.nextBtn').click(function(){
		console.log(submitFlag);
		$('#email').val(submitFlag.email);
		if(!submitFlag.isAuth) {
			alert('인증되지 않았습니다.');
			return;
		}
		authForm.submit();
	})
});

</script>