package kh.study.shop.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.config.MemberRole;
import kh.study.shop.config.MemberStatus;
import kh.study.shop.member.service.MemberService;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Resource (name ="memberService")
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	//회원가입
	@PostMapping("/join")
	public String joinMember(MemberVO member) {
		
		//enum 파일명. 변수명. toString() (문자열로 넣기 위해서 toString)
		member.setMemberStatus(MemberStatus.ACTIVE.toString());
		member.setMemberRole(MemberRole.MEMBER.toString());
		
		//비밀번호 인코드
		String pw = encoder.encode(member.getMemberPw());
		member.setMemberPw(pw);
		
		
		memberService.joinMember(member);
		
		return "redirect:/item/list";
	}
	
	//로그인
//	@PostMapping ("/login")
//	public String loginMember(MemberVO member, HttpSession session) {		
//		
//		MemberVO loginInfo = memberService.loginMember(member);
//		
//		//로그인 실패 시 null이 뜬다. 즉, 성공하면 null이 아니다! 
//		if(loginInfo != null ) {
//			session.setAttribute("loginInfo" ,loginInfo );
//		
//		}
//		//페이지 이동
//		return "content/member/login_result";
//	}
	
	//로그인 성공/실패 시
	@GetMapping("/loginResult")
	public String loginResult(){
		
		return "content/member/login_result";
	}
	
	
	//로그인 ajax 방식으로 실행
	@ResponseBody
	@PostMapping ("/loginAjax")
	public boolean loginAjax(MemberVO member, HttpSession session) {		
		
		MemberVO loginInfo = memberService.loginMember(member);
		
		//로그인 실패 시 null이 뜬다. 즉, 성공하면 null이 아니다! 
		if(loginInfo != null ) {
			session.setAttribute("loginInfo" ,loginInfo );
			
		}
		//데이터 보내기 (로그인 성공여부)
		return loginInfo == null ? false : true;
		//false 실패 true 성공 
	}
	
	
	//마이페이지로 이동
	@GetMapping("/myPage")
	public String myPage(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
		
		model.addAttribute("member", memberService.memberDetail(memberId));
	
		return "content/member/myPage";
	}
	
}
