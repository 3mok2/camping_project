console.log('list.js');
$(function(){
	
	let searchCondition = function(){
		if(searchForm.find('option:selected').val() && searchForm.find('[name="keyword"]')){ // 검색 조건이 있을 때
			listForm.append($('<input/>',{type : 'hidden', name : 'type', value : '${criteria.type}'}))
					.append($('<input/>',{type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
		}
	} 
	
	$('#searchForm button').click(function(e){D
		e.preventDefault();
		if(!searchForm.find('option:selected').val()){ // 없을 경우
			alert('검색종류를 선택하세요');
			return;
		}
		if(!searchForm.find('[name="keyword"]').val()){
			alert('키워드를 입력하세요.')
			return;
		}
		searchForm.find('[name="pageNum"]').val(1);
		searchForm.submit();
	});
	
	// 페이지 이동
	$('.pagination a').click(function(e){
		e.preventDefault();
		let pageNum = $(this).attr('href');
		listForm.find('input[name="pageNum"]').val(pageNum);
		if(searchForm.find('option:selected').val() && searchForm.find('[name="keyword"]')) {
			listForm.append($('<input/>', {type : 'hidden', name : 'type', value : '${criteria.type}'}))
					.append($('<input/>', {type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
		}
		
		listForm.submit();
	})
	
	// list->get 이동
	$('.move').click(function(e){
		e.preventDefault();
		let bnoValue = $(this).attr('href');
		searchCondition();
		listForm.append($('<input/>', {type : 'hidden', name : 'category.cateId', value : category}))
				.append($('<input/>', {type : 'hidden', name : 'bno', value : bnoValue}))
				.attr('action',`${ctxPath}/board/get`)
				.submit();
	});
	
	$('#regBtn').on('click',function(){
		if(searchForm.find('option:selected').val() && searchForm.find('[name="keyword"]')) {
			listForm.append($('<input/>', {type : 'hidden', name : 'type', value : '${criteria.type}'}))
					.append($('<input/>', {type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
		}
		listForm.append($('<input/>', {type : 'hidden', name : 'cateId', value : category}))
				.attr('action', `${ctxPath}/board/register`)
				.submit();// 글쓰기 페이지 이동
	})
	
	$('.amount').change(function(){
		let amount = $(this).val();
		if(searchForm.find('option:selected').val() && searchForm.find('[name="keyword"]')) {
			listForm.append($('<input/>', {type : 'hidden', name : 'type', value : '${criteria.type}'}))
					.append($('<input/>', {type : 'hidden', name : 'keyword', value : '${criteria.keyword}'}))
		}
		listForm.find('input[name="amount"]').val(amount)
		listForm.submit();
	})
})