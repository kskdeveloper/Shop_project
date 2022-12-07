package kh.study.shop.cart.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.cart.service.CartService;
import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.service.MemberService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Resource(name="cartService")
	private CartService cartService;
	
	@Resource (name ="memberService")
	private MemberService memberService;
	
	
	//장바구니 버튼 클릭
	@PostMapping("/regCart")
	@ResponseBody
	public void regCart(CartVO cartVO, Authentication authentication) {
		
		//memberId 가져오기
		User user =  (User) authentication.getPrincipal();
		cartVO.setMemberId(user.getUsername());
		
		cartService.regCart(cartVO);
		
	}
	
	
	//장바구니로 이동
	@GetMapping("/cartList")
	public String cartList(Authentication authentication, Model model) {
		
		//memberId 가져오기
		User user =  (User) authentication.getPrincipal();
		String memberId = user.getUsername();
		
		List<CartVO>list = cartService.cartList(memberId);
		model.addAttribute("cartList", list);
		
		 
		//전체 총 가격 데이터
		int finalPrice = 0; 
		
		for(CartVO cart : list) {
			finalPrice = finalPrice + cart.getTotalPrice();
		}
		model.addAttribute("finalPrice", finalPrice);
		
		return "content/cart/cart_list";
	}
	
	//장바구니 수량 변경
	@PostMapping("/changeCnt")
	@ResponseBody
	public void changeCnt(CartVO cartVO) {
		
		cartService.changeCnt(cartVO);
	}
	
	//dataset 연습 메소드
	@GetMapping("/test")
	public String datasetTest() {
		
		
		return "content/cart/test";
	}
	
	//장바구니 선택삭제
	@PostMapping("/deleteCart")
	public String deleteCart(String cartCodes, CartVO cartVO) {

	String[] cartCodesArr = cartCodes.split(",");
	
	List<String>cartCodeList = Arrays.asList(cartCodesArr);
	cartVO.setCartCodeList(cartCodeList);
	
	cartService.deleteCart(cartVO);
	
		return"redirect:/cart/cartList";
	}
	
	//장바구니 구매버튼 클릭 후  buy_info 페이지로
	@RequestMapping("/buyInfo")
	public String buyInfo(CartVO cartVO, Model model, String cartCodes, Authentication authentication) {
		
		//주소창에서 새로고침 했을 때
		if(cartCodes == null || cartCodes.equals("")) {
			return "redirect:/item/list";
		}
				
		String[] cartCodesArr = cartCodes.split(",");
		
		List<String>cartCodeList = Arrays.asList(cartCodesArr);
		cartVO.setCartCodeList(cartCodeList);
		
		List<CartVO> buyInfo = cartService.buyInfo(cartVO);
		
		model.addAttribute("cartInfo", buyInfo);
		
		//총가격
		int finalPrice = 0;
		for(CartVO cart : buyInfo) {
			finalPrice = finalPrice + (cart.getCartAmount()*cart.getItemList().getItemPrice());
		}
		
		model.addAttribute("totalPrice", finalPrice);
		
		//멤버정보
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
		
		
		model.addAttribute("memberInfo", memberService.memberDetail(memberId));
		
		return "content/buy/buy_info";
		
	}
	
	//상세페이지 구매하기 버튼 (itemCode(이미지, 상품명, 가격)와 수량 가져오기)
	@PostMapping("/detailBuy")
	public String detailBuy(Authentication authentication, Model model, int buyAmount, String itemCode) {
		
		//멤버정보
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
						
		model.addAttribute("memberInfo", memberService.memberDetail(memberId));
		

		//itemCode(이미지, 상품명, 가격)
		ItemVO itemVO = cartService.detailBuy(itemCode);
		
		
		model.addAttribute("item", itemVO);
		
		//수량데이터
		model.addAttribute("buyAmount", buyAmount);
		
		int finalPrice = 0;
		finalPrice = finalPrice + (buyAmount * itemVO.getItemPrice());
		model.addAttribute("totalPrice", finalPrice);
		
		
		
		return "content/buy/buy_info";
	
	}
	
	
}
