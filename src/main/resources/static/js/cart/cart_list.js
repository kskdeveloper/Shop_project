
//-------------------------함수 정의 영역-------------------------------//
//총 가격을 세팅하는 함수
function setFinalPrice(){
	//체크된 체크박스 선택
	const checkedBoxes = document.querySelectorAll('.chk:checked');

	let finalPrice = 0;
	for(const checkedBox of checkedBoxes){
		const totalPrice = parseInt(checkedBox.closest('tr').querySelector('.totalPriceDiv').dataset.totalPrice);
	
		finalPrice = totalPrice + finalPrice;
	}
	
	document.querySelector('#finalPriceSpan').innerText = '￦' + finalPrice.toLocaleString();
}

//---------------- 스크립트 실행과 동시에 필요한 변수 생성 ---------------------//
//제목줄 체크박스
const checkAll = document.querySelector('#checkAll');

//제목줄 제외한 장바구니 체크박스
const chks = document.querySelectorAll('.chk');
//------------------------------------------------------------------------------//
//----------------------------- 이벤트 정의 영역 -------------------------------//

//전체선택, 전체해제 이벤트
checkAll.addEventListener('click',function(){
	//제목 줄 체크박스 체크여부
	const isChecked = checkAll.checked;	//true, false
	
	//장바구니 목록 모든 체크박스
	const checkBoxes = document.querySelectorAll('.chk');
	
	//제목줄 체크박스가 체크되었다면
	if(isChecked){
		
		//모든 체크박스에 체크 표시
		for(const checkBox of checkBoxes){
			checkBox.checked = true;
		}
	}
	
	else{
		//모든 체크박스 체크 해제
		for(const checkBox of checkBoxes){
			checkBox.checked = false;
		}
	}

	setFinalPrice();

});

//장바구니 목록에 있는 체크박스 클릭 시 진행

for(const chk of chks){
	
	chk.addEventListener('click',function(){
		setFinalPrice();
	});	
}


//장바구니 수량 변경 모달창
function updateCnt(selectedTag){
			
		//모달창 소스
		const modal = new bootstrap.Modal('#changeAmountModal');
	   	//모달 보여주기
	   	modal.show();
	   		
	   //수량 데이터	
	   const cartAmount = selectedTag.closest('td').querySelector('.amountInput').value;	
	   	
	   	//cartCode
	   const cartCode = selectedTag.closest('tr').querySelector('input[type="checkbox"]').value;
	   		
	   	//클릭한 버튼의 data-cart-code, data-cart-amount 속성값을
		// 수량을 변경하고자 하는 데이터로
		
		document.querySelector('#updateAmountBtn').dataset.cartCode = cartCode;
		document.querySelector('#updateAmountBtn').dataset.cartAmount = cartAmount;
		
		//기존 장바구니 수량을 취소버튼으로 전달
		const originAmount = selectedTag.closest('td').querySelector('.amountInput').dataset.originAmount;
		document.querySelector('#cancelBtn').dataset.originAmount = originAmount;
		 
}


// 수량 변경 버튼 클릭 후 나타나는 모달창의 확인버튼 클릭
function changeCnt(){
	
	const cartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
	const cartAmount = document.querySelector('#updateAmountBtn').dataset.cartAmount;
	
	$.ajax({
	   url: '/cart/changeCnt', //요청경로
	    type: 'post',
	    data:{'cartCode':cartCode,'cartAmount':cartAmount}, //필요한 데이터
	    
	    success: function(result) {
	     
			//모달창 소스
			const modal = new bootstrap.Modal('#updateCntModal');
			
	   		//모달 보여주기
	   		modal.show();
	   		
	   		//수량 변경
	   		for(const chk of chks){
				
				if(chk.value == cartCode){
					chk.closest('tr').querySelector('.amountInput').dataset.originAmount = 	cartAmount;
								
					}
			}
	   		
	   		
	   		//다른 방법 모달창 하나로 실행 후 모달창 닫기
	   		//위에서 선언을 한 후(const modal = new bootstrap.Modal('#updateCntModal');)
	   		// modal.hide();
	   		// modal.show(); 모달 사용 시
		
	   		//장바구니 상품에 대한 수량 및 가격 갱신
	   
	   
	   
	    },
	    error: function(){
	       alert('실패');
	    }
	});

}

////////----------수량 변경 팝업에서 취소버튼 클릭시-----------//////////
function rollbackAmount(selectedTag){

		//모든 체크박스를 돌면서 cartCode값을 가져온다.
		//수량을 rollback 시켜야 하는 cartCode랑 비교
		
		const selectedCartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
		
		const originAmount =  document.querySelector('#cancelBtn').dataset.originAmount;
		
		for(const chk of chks){
			if(chk.value == selectedCartCode){
				chk.closest('tr').querySelector('.amountInput').value = originAmount; 
			}
		}
	
}

/////////-------------선택삭제 및 선택구매 버튼 클릭------------///////
function deleteBuy(selectedTag){
	
	const deleteForm = document.querySelector('#cartForm');
	
	//체크한 cartCode 데이터 다 가져오기
	const checkedChks = document.querySelectorAll('.chk:checked');
		
		if(checkedChks.length == 0){
			alert('선택한 상품이 없습니다.')
			return;
		}
	
		let cartCodes = '';
	
		for(const checkedChk of checkedChks){
			cartCodes = cartCodes + checkedChk.value + ',';
			//cart_001cart_002 이런식으로 들어오니까 쉼표로 구분하겠다
		}
		
	deleteForm.querySelector('input').value = cartCodes;
	
	//action값 바꾸기
	if(selectedTag.innerText == '선택삭제'){
		
		deleteForm.action = '/cart/deleteCart'
	}
	else{
		
		deleteForm.action = '/cart/buyInfo'
	}
	
	
	deleteForm.submit();

}

///////////------------선택구매 버튼 클릭-------------///////////
/*function buyCart(){
	
	const buyForm = document.querySelector('#buyCartForm');
	
	const checkedChks = document.querySelectorAll('.chk:checked');
	
		if(checkedChks.length == 0){
			alert('선택한 상품이 없습니다.')
			return;
		}
	
		let itemCodes = '';
		
		for(const checked of checkedChks){
			itemCodes = itemCodes + checked.dataset.itemCode + ',';
		}
		
		buyForm.querySelector('#itemCodeInput').value = itemCodes;
	
		
		buyForm.submit();
}
*/
