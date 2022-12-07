
//회원 이름 클릭 시 실행되는 함수 
function memberDetail(memberId){
	
	$.ajax({
	   url: '/admin/memberDetail', //요청경로
	    type: 'post',
	    data:{'memberId':memberId}, //필요한 데이터
	    success: function(result) {
	     
	    const memberDetail = document.querySelector('#detail');
		
		memberDetail.innerHTML='';
		
		let str = '';
		
		str += `<tr>`;
		str += `<th>회원ID</th>`;
		str += ` <td>${result.memberId}</td> `;
	    str += `</tr>`;
		str += `<tr>`;
    	str += `<th>회원이름</th> `;
	    str += `<td>${result.memberName}</td>`;
		str += `</tr>`;
		str += `<tr> `;
	    str += `<th>주소</th> `;
	    str += ` <td>${result.memberAddr}</td> `;
		str += `</tr> `;
		str += `<tr>`;
    	str += `<th>Email</th> `;
    	str += `<td>${result.memberEmail}</td>`;
		str += `</tr>`;
		str += `<tr>`;
		str += `<th>권한</th>`;
		str += ` <td>${result.memberRole}</td>`;
		str += `</tr>`;
		str += ` <tr>`;
		str += ` <th>상태</th>`;
		str += `<td>${result.memberStatus}</td>`;
		str += `</tr>`;
        
		memberDetail.insertAdjacentHTML('afterbegin',str);

	    },
	    error: function(){
	       alert('실패');
	    }
	});
	
	
}
