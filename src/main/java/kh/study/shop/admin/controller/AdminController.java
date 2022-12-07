 package kh.study.shop.admin.controller;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.admin.service.AdminService;
import kh.study.shop.config.CateStatus;
import kh.study.shop.config.ItemStatus;
import kh.study.shop.config.UploadFileUtil;
import kh.study.shop.item.vo.CateVO;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.service.MemberService;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
	@ModelAttribute	//해당 컨트롤러 실행되기 전 무조건 호출된다.
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		//관리자로 로그인 시 디폴트 값으로 1을 준다
		model.addAttribute("menu", menu);
		
		//status 카테고리 리스트 (사용 중인 것)
		//model.addAttribute("statusList", adminService.statusCateList());
		
	}
	
	
	//관리자 페이지 이동
	@GetMapping("/main")
	public String main(Model model) {
		//전체 리스트
		model.addAttribute("cateList", adminService.cateList());
		
		//status 카테고리 리스트 (사용 중인 것)<-interceptor 사용
		//model.addAttribute("statusList", adminService.statusCateList());
		
		//side html이 데이터 받도록 보냄
		//model.addAttribute("menu", "1");
		
		return "content/admin/reg_item";
	}
	
	//카테고리 등록
	@PostMapping("/regCate")
	public String regCate(CateVO cateVO) {
		
		cateVO.setCateStatus(CateStatus.USE.toString());
		
		adminService.regCate(cateVO);
		
		return "redirect:/admin/main";
	}
	
	
	//status 변경(사용/미사용) (ajax 사용) return없으니까 void
	@PostMapping("/updateStatus")
	@ResponseBody
	public void updateStatus (CateVO cateVO) {
		
		adminService.updateStatus(cateVO);
	
	}
	
	//상품등록 페이지
	//일반적인 데이터는 커맨드 객체로 전달 받음
	//첨부파일 데이터는 multipartFile 객체로 전달받아야 한다
	@PostMapping("/regItem")
	public String regItem(ItemVO itemVO, MultipartFile mainImg
										, List<MultipartFile> subImgs) {
		
		//이미지 파일 첨부(메인이미지 첨부파일 하나)
		//원본파일명, 첨부파일명, IS_MAIN
		ImgVO uploadInfo = UploadFileUtil.uploadFile(mainImg);	
		
		//다중이미지파일 첨부 (서브이미지)
		//원본파일명, 첨부파일명, IS_MAIN
		List<ImgVO> uploadList = UploadFileUtil.multiUploadFile(subImgs);
		
		//메인과 서브 이미지의 (원본파일명, 첨부파일명, ismain 값 집어 넣음
		uploadList.add(uploadInfo);
		
		//item_code 상품과 이미지 둘다 아이템코드가 동일해야 한다.
		//다음에 들어갈 item_code 조회하고(별도로 분리) 조회한 아이템코드로 insert 진행
		String getItemCode = adminService.getItemCode();
		itemVO.setItemCode(getItemCode); 	//selectkey 지워서 없어진 아이템코드 추가
		
		
		//이미지 정보를 INSERT하기 위한 데이터를 가진 UPLOADLIST에 
		//조회한 ITEM_CODE값도 추가해 준다.
		for(ImgVO vo :uploadList) {
			vo.setItemCode(getItemCode);
		}
		//itemVO에서 List<ImgVO) imgList에 전체 이미지 데이터 다 넣겠다
		itemVO.setImgList(uploadList);
		
		
		//상품 정보 insert + 이미지 정보 insert
		itemVO.setItemStatus(ItemStatus.ON_SALE.toString());
		adminService.regItem(itemVO);
		
		//상품 이미지 정보 insert
		//IMG_CODE, 원본 파일명, 첨부한 파일명, IS_MAIN, ITEM_CODE
		
		return "redirect:/admin/main";
	}
	
	//회원목록 조회
	@GetMapping("/memberList")
	public String memberList(Model model, MemberVO memberVO) {
		
		model.addAttribute("memberList",memberService.memberList());
		
		//side active
		//model.addAttribute("menu", "3");
		
		return "content/admin/mem_manage";
	}
	
	//회원 상세조회(ajax)
	@PostMapping("/memberDetail")
	@ResponseBody
	public MemberVO memberDetail(String memberId) {
		
		MemberVO detail = memberService.memberDetail(memberId);
	
		return detail;
	}
	
	
	//카테고리 사용여부 변경 후 실행되는 함수 
	@PostMapping("/selectUseCategoryListAjax")
	@ResponseBody
	public List<CateVO> selectUseCategoryListAjax() {
		//사용중인 것만 뜨도록
		List<CateVO> cateList =	adminService.statusCateList();
	
		return cateList;
	}

}
