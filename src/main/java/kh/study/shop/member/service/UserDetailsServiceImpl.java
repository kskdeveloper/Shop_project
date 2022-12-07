package kh.study.shop.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kh.study.shop.member.vo.MemberVO;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		MemberVO loginInfo = sqlSession.selectOne("memberMapper.loginMember", username);
		
		//로그인 시 아이디를 잘못 입력한 경우
		if(loginInfo == null) {
			throw new UsernameNotFoundException("아이디를 찾을 수 없습니다");
		}
		
		UserDetails userDetails = User.withUsername(loginInfo.getMemberId())
									  .password(loginInfo.getMemberPw())
									  .roles(loginInfo.getMemberRole())
									  .build();
		
		return userDetails;
	}

}
