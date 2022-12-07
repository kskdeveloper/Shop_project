
//회원 가입에서 주소검색 클릭 시 진행
//-------------------함수 정의----------------//
	function searchAddr() {
		new daum.Postcode({
			oncomplete : function(data) {
			//도로명 주소 입력
			const roadAddr = data.roadAddress;
			document.querySelector('#memberAddr').value = roadAddr;			
			
			}
		}).open();
	}
	
//내용 작성하고 화면창 닫았을 때 기존 내용 삭제
	
//----------페이지 열림과 동시에 실행되는 코드-----------//
isLoginFail();


//-------------------변수--------------------//
	const join_modal = document.querySelector('#join_modal');
	const login_modal = document.querySelector('#login_modal');
	
	
	
//------로그인 실패 여부로 모달창을 띄워주는 함수------//	
function isLoginFail(){
	//로그인 모달창 실행 (true/false)
	const isLoginFail = document.querySelector('#isLoginFail').value;
	
	//true일 때 모달창 실행
	if(isLoginFail == 'true'){
		const myModalAlternative = new bootstrap.Modal('#login_modal');
		myModalAlternative.show();
	
	}
	//강제로 닫기 myModalAlternative.hide();
}


	
//----------------이벤트 정의 ----------------//	
	//회원가입 모달이 닫힐 때 실행되는 이벤트
	join_modal.addEventListener('hidden.bs.modal', function(event){	//function 매개변수 이름 자유
	
	//첫번째 방법
	//document - 현재 열려있는 html 문서에서 querySelector('#id')를 선택하겠다.
/*	 const inputs = join_modal.querySelectorAll('input:not([type="submit"])');
	 
	 for(const inputTag of inputs){
	  //inputTag.value = null; 
		inputTag.value = ''; 
	}*/
	
	//두번째 방법
	join_modal.querySelector('form').reset();
	
	//input 태그의 기능 중에 reset이 있다. 그래서 여기서 reset을 실행시키도록 구현한 것이다.
	
	
	});
	
	//최신 스타일 문법
	/*join_modal.addEventListener('click', event =>{		
		
	});*/
		
		
	//로그인 모달이 닫힐 때 실행되는 이벤트
	login_modal.addEventListener('hidden.bs.modal', function(event){
		
		//첫번째 방법 
		/*const logInputs = login_modal.querySelectorAll('input:not([type="submit"])');
		
		for(const logInput of logInputs){
			logInput.value = '';
		}	*/
	
		//두번째 방법
		login_modal.querySelector('form').reset();
	
	});
	
	
	
//로그인 버튼 클릭 시 함수 실행
function login() {
	//id 겹치는것 해소하기 위해서 특정 모달을 지정하여 모달안에서 id 선택 
	const member_id = login_modal.querySelector('#memberId').value;
	const member_pw = login_modal.querySelector('#memberPw').value;

	$.ajax({
		url: '/member/loginAjax', //요청경로
		type: 'post',
		data: {'memberId': member_id, 'memberPw': member_pw}, //필요한 데이터
		success: function(result) {		
		
		if(result){ 	//true
			alert('환영합니다.');
			
			location.href='/item/list';		
		
		}
		else{	//false
			alert('아이디 또는 비밀번호를 다시 확인해주세요.');
		}
				
		},
		error: function() {	
			alert('실패');
		}
	});

}