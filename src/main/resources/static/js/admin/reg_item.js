
//사용, 미사용 라디오 버튼 클릭 시 진행
function changeStatus(cateCode, status){
	
	const result = confirm('상품의 상태를 변경하시겠습니까?');
	
	if(result){
		$.ajax({
		   url: '/admin/updateStatus', //요청경로
		    type: 'post',
		    data:{'cateStatus': status , 'cateCode':cateCode}, //필요한 데이터
		    success: function(result) {
		      alert('상태가 변경되었습니다.');
		      
		      //select박스 목록 재조회(사용 중인 것만)
		      selectUseCategoryListAjax();
		      topMenuAjax();
		    },
		    error: function(){
		       alert('실패');
		    }
		});
	}
}

//카테고리 사용여부 변경 후 실행되는 ajax 함수
//해당 함수가 실행되면 사용중인 카테고리 목록을 다시 조회한다.
function selectUseCategoryListAjax(){

	$.ajax({
	  url: '/admin/selectUseCategoryListAjax', //요청경로
	  type: 'post',
	  data:{}, //필요한 데이터
	  success: function(result) {
		  
		  const selectBox = document.querySelector('#cateCode');
		  //내용 빈값으로 바꾼다
		  selectBox.innerHTML = '';
		  
		  let str = '';
		  
		  str += '<option selected>전체</option>';
		  
		  for(const cateInfo of result){
			
		  str += `<option value="${cateInfo.cateCode}">${cateInfo.cateName}</option>`;
		  
		  }
		  selectBox.insertAdjacentHTML('afterbegin',str);
	  
	  },
	  error: function(){
		   alert('실패');
	  }
	});
}	
