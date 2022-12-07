//장바구니에 추가

function addCart(){
	
	//비회원인 경우 로그인 먼저 alert
	
	//innerHTML : 선택한 태그 안에 있는 태그 내용을 그대로 가져옴 (해석하지 않고 가져온다) 원본 그대로 가져옴
	//innerText : 선택한 태그 내용을 해석하여 눈에 보여지는 데이터만 가져온다.
	
	const check_login = document.querySelector('#test').innerText;
	
	//trim() 공백제거
	//alert(check_login.trim());
	
	if(check_login.trim() == ''){
	
		alert('로그인이 필요합니다.');
			return;
	}
	
	//장바구니로 이동 ajax 실행 
	addCartAjax();
	
	
	//장바구니 등록(item_code, cart_amount)
	
	
	//자바스크립트에서 submit 실행 방법
	//form태그 사용하기 
	//1. form 태그 선택 2. 선택한 form태그에서 submit()함수 실행시킨다.	
	
	//document.querySelector('#regCartForm').submit();
	

}

//장바구니 등록 시 실행되는 ajax

function addCartAjax(){
	
	$.ajax({
	   url: '/cart/regCart', //요청경로
	    type: 'post',
	    data:{'itemCode': document.querySelector('#itemCode').value
	    ,'cartAmount': document.querySelector('#cartAmount').value}, //필요한 데이터
	   
	    success: function(result) {
			
			//모달창 소스
			const modal = new bootstrap.Modal('#regCartModal');
	   		//모달 보여주기
	   		modal.show();
		
			//const result = confirm('장바구니에 담겼습니다. \n장바구니로 이동하시겠습니까?');
			
			//if(result){
				
				//페이지 이동
			//	location.href = '/cart/cartList';
			//}
	   
	      
	    },
	    
	    error: function(){
	       alert('실패');
	    }
	});
	
}



////-----------------------총가격 변경------------------------////

	const cartAmountInput = document.querySelector('#cartAmount');
	
	cartAmountInput.addEventListener('change', function(){
		
		const itemPrice = document.querySelector('#itemPriceSpan').dataset.itemPrice;
		
		const cartAmount = document.querySelector('#cartAmount').value;
		
		const result = itemPrice * cartAmount;
		
		document.querySelector('#totalPriceSpan').innerText = '￦' + result.toLocaleString();
		
	})
	 
	 
///////////-----------------상세페이지 구매하기 버튼----------------//////////////
function detailBuy(){
	//수량 데이터 넘기기
	const buyAmount = document.querySelector('#cartAmount').value;
	document.querySelector('#buyAmountInput').value = buyAmount;
	
	document.querySelector('#buyDetailForm').submit();

}












