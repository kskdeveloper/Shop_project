package kh.study.shop.buy.controller;


import java.util.ArrayList;
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

import kh.study.shop.buy.service.BuyService;
import kh.study.shop.buy.vo.BuyDetailVO;
import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

@Controller
@RequestMapping("/buy")
public class BuyController {
	@Resource(name ="buyService")
	private BuyService buyService;
	
	
	//buyInfo에서 구매하기 클릭 
	//(totalPrice, itemCode, cartAmount 데이터 필요해-> cartCode만 가져오면 해결)
	@PostMapping("/buyCart")
	public String buyCart(CartVO cartVO, BuyVO buyVO, String[] cartCodes
						, Authentication authentication
						, ArrayList<BuyDetailVO> buyDetailList) {
		
		//insert 되어야 하는 buyCode 조회
		String buyCode = buyService.buyCode();
		
		//totalPrice, itemCode들, cartAmount들
		List<String> cartCodeList = Arrays.asList(cartCodes);
		cartVO.setCartCodeList(cartCodeList);
		
		List<CartVO> cartList = buyService.cartBuyInfo(cartVO);
		
		//memberId
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
		
		//totalPrice
		int totalPrice = 0;
		for(CartVO e : cartList) {
			 totalPrice = totalPrice +e.getTotalPrice();
			 
			 //새로운 통 만들어 줘야 함
			 BuyDetailVO vo = new BuyDetailVO();
			 vo.setItemCode(e.getItemCode());
			 vo.setBuyAmount(e.getCartAmount());
			
			 buyDetailList.add(vo);
			 
		}
		
		//buyVO (buyVODetail도 같이 사용)
		buyVO.setBuyCode(buyCode);
		buyVO.setMemberId(memberId);
		buyVO.setTotalPrice(totalPrice);
		buyVO.setBuyDetailList(buyDetailList);	//buyDetailVO -> itemCode, cartAmount
		
		//쿼리 실행
		buyService.buyCart(buyVO,cartVO);
	
		return "redirect:/item/list";
	
	}
	
	//단일메뉴 구매확정 버튼
	@PostMapping("/directBuy")
	public String directBuy(String itemCode, int buyAmount, int totalPrice
							, BuyVO buyVO, Authentication authentication
							, BuyDetailVO buyDetailVO, ArrayList<BuyDetailVO> buyDetailList) {
		//memberId
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
		
		//insert 되어야 하는 buyCode 조회
		String buyCode = buyService.buyCode();
		
		//buyVO
		buyVO.setBuyCode(buyCode);
		buyVO.setTotalPrice(totalPrice);
		buyVO.setMemberId(memberId);
		
		//buyDetailVO
		buyDetailVO.setBuyCode(buyCode);
		buyDetailVO.setItemCode(itemCode);
		buyDetailVO.setBuyAmount(buyAmount);
		buyDetailList.add(buyDetailVO);
		
		//buyVO의 detailList에 집어 넣기
		buyVO.setBuyDetailList(buyDetailList);
		
		//buyService.buyDirect(buyVO);
		buyService.buyCart(buyVO, null);
		
		return "redirect:/item/list";
	}
	
	//구매 내역 조회
	@GetMapping("/buyHistory")
	public String buyHistory(Model model, Authentication authentication) {
		
		//memberId
		User user = (User)authentication.getPrincipal();
		String memberId = user.getUsername();
		
		//뚜껑
		model.addAttribute("buyList", buyService.buyList(memberId));
		
		//내용(구매상세)
		model.addAttribute("buyHistory", buyService.buyHistory(memberId));
		
		
		return "content/buy/buyHistory";
	}

}
