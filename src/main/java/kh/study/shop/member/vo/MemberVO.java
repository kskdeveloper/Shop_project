package kh.study.shop.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {

	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberAddr;
	private String addrDetail;
	private String memberEmail;
	private String memberRole;
	private String memberStatus;
	
	
}
