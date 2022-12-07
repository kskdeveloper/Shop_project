package kh.study.shop.member.service;

import java.util.List;

import kh.study.shop.member.vo.MemberVO;

public interface MemberService {
	
	//회원가입
	void joinMember(MemberVO member);

	//로그인
	MemberVO loginMember(MemberVO member);
	
	//회원리스트
	List<MemberVO> memberList();
	
	//회원 상세조회
	MemberVO memberDetail(String memberId);
}
