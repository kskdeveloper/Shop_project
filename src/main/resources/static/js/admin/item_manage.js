//상품 판매중/매진 표시
function changeItemStatus(itemCode, status){
	
	const result = confirm('변경하시겠습니까?');
	
	if(result){
		$.ajax({
		   url: '/item/changeItemStatus', //요청경로
		    type: 'post',
		    data:{'itemCode':itemCode, 'itemStatus':status}, //필요한 데이터
		    success: function(result) {
		      alert('상태가 변경되었습니다.');
		   
		    },
		    error: function(){
		       alert('실패');
		    }
	});
		
	}
}


//상품 재고변경
function updateStock(itemCode, selectedTag){
	
	//parentElement : 부모태그 찾아 감.
   	//children : 자식 태그 찾아 감.(자식들 다 데려오는것 복수형이니까 배열형태로 가져옴)
   	//previousElementSibling : 이전 형제 노드를 찾아 감.
   	//nextElementSibling : 다음 형제 노드를 찾아 감.
   	//closest() : 가장 가까운 상위태그를 찾아 감
	
	//div의 상위 div의 input
	//const itemStock = selectedTag.parentElement.previousElementSibling.children[0].value;
	
	//방법2 클래스 부여 (td안에 있는 class가 stockInput인 것은 하나)
	const itemStock = selectedTag.closest('td').querySelector('.stockInput').value;
	
	$.ajax({
	   url: '/item/updateStock', //요청경로
	    type: 'post',
	    data:{'itemCode':itemCode, 'itemStock': itemStock}, //필요한 데이터
	    success: function(result) {
		
			//모달창 소스
			const modal = new bootstrap.Modal('#updateStockModal');
	   		//모달 보여주기
	   		modal.show();
	   
	    },
	    error: function(){
	       alert('실패');
	    }
	});

}