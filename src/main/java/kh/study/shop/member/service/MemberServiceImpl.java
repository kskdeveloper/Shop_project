package kh.study.shop.member.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//회원가입
	@Override
	public void joinMember(MemberVO member) {
		sqlSession.insert("memberMapper.joinMember", member);
	}
	
	//로그인
	@Override
	public MemberVO loginMember(MemberVO member) {
		
		return sqlSession.selectOne("memberMapper.loginMember", member);
	}
	
	//회원리스트
	@Override
	public List<MemberVO> memberList() {
		
		return sqlSession.selectList("memberMapper.memberList");
	}
	
	//회원 상세조회
	@Override
	public MemberVO memberDetail(String memberId) {

		return sqlSession.selectOne("memberMapper.memberDetail", memberId);
	}

	
}
