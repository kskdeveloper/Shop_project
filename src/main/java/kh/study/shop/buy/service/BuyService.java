package kh.study.shop.buy.service;

import java.util.List;

import kh.study.shop.buy.vo.BuyDetailVO;
import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

public interface BuyService {
	
	//buyInfo 구매하기
	void buyCart(BuyVO buyVO, CartVO cartVO);
	
	//다음에 들어갈 buyCode
	String buyCode();	
	
	//장바구니 데이터의 조회
	List<CartVO> cartBuyInfo(CartVO cartVO);
	
	
	//구매 상세내역 조회
	List<BuyDetailVO> buyHistory(String memberId);
	
	//구매내역
	List<BuyVO> buyList(String memberId);

}
