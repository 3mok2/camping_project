console.log('register.js')
$(function() {
	
	let uploadResultList = [];
	let showUploadResult = function(attachList){
		console.log('실행');
		let fileList = '';
		$.each(attachList,function(i,e){
			fileList += `
			<li class="list-group-item" data-uuid="${e.uuid}">
				<div class="float-left">`
			if(e.fileType){
				let filePath = e.uploadPath+"/s_"+e.uuid+"_"+e.fileName;
				let encodingFilePath = encodeURIComponent(filePath);
				fileList+=`
				<div class="thumnail d-inline-block mr-3" style="width:40px">
					<img alt="" src="${ctxPath}/files/display?fileName=${encodingFilePath}" style="width: 100%">
				</div>`
			} else {
				fileList +=`
				<div class="thumnail d-inline-block mr-3" style="width:40px">
					<img alt="" src="${ctxPath}/resources/images/attach.png" style="width: 100%">
				</div>
				`
			}
				fileList +=`
				<div class="d-inline-block">
					<a href="#">${e.fileName}</a>
				</div>
					
					
				</div>
				<div class="float-right">
					<a href="#" class="delete">삭제</a>
				</div>
			</li>`
		});
		$('.uploadResultDiv ul').append(fileList);
	}

	$('input[type="file"]').change(function(){
		let formData = new FormData();
		let files = this.files;
		
		for(let f of files) {
			if(!checkExtension(f.name, f.size)) {
				$(this).val('');
				return;			
			}
			formData.append('uploadFile', f);
		}
		
		$.ajax({
			url : `${ctxPath}/files/upload`,
			type : 'post',
			processData : false,
			contentType : false,
			data : formData,
			dataType : 'json',
			success : function(attachList){
				$.each(attachList,function(i,e){
					uploadResultList.push(e);
				})
				showUploadResult(attachList);
				console.log(uploadResultList);
			}
		});
	});
	
	$('.register').click(function(){
		let form = $('form');
		console.log(uploadResultList);
		if(uploadResultList){
			$.each(uploadResultList, function(i,e){
				let uuid = $('<input/>', {type : 'hidden', name:`attachList[${i}].uuid`, value : `${e.uuid}`});
				let fileName = $('<input/>', {type : 'hidden', name:`attachList[${i}].fileName`, value : `${e.fileName}`});
				let fileType = $('<input/>', {type : 'hidden', name:`attachList[${i}].fileType`, value : `${e.fileType}`});
				let uploadPath = $('<input/>', {type : 'hidden', name:`attachList[${i}].uploadPath`, value : `${e.uploadPath}`});
				form.append(uuid)
					.append(fileName)
					.append(fileType)
					.append(uploadPath);
			})
		}
		form.submit();
	});
	
	$('.uploadResultDiv ul').on('click', '.delete', function(e){
		e.preventDefault();
		let uuid = $(this).closest('li').data('uuid');
		let targetFileIdx = -1;
		let targetFile = null;
		
		$.each(uploadResultList, function(i,e){
			if(e.uuid == uuid){
				targetFileIdx = i;
				targetFile = e;
				return;
			}
		})
		
		if(targetFileIdx!=-1) uploadResultList.splice(targetFileIdx,1);
		console.log(uploadResultList);
		
		console.log('삭제 대상 파일 객체'+targetFile);
		
		$.ajax({
			type : 'post',
			url : `${ctxPath}/files/deleteFile`,
			data : targetFile,
			success : function(result) {
				console.log(result);
			}
		});
		$(this).closest('li').remove();
		console.log(uploadResultList);
	})
})
