package kh.study.shop.cart.service;

import java.util.List;

import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.vo.ItemVO;

public interface CartService {

	//장바구니에 담기
	void regCart(CartVO cartVO);
	
	//장바구니 리스트
	List<CartVO> cartList(String memberId);
	
	//장바구니 수량변경
	void changeCnt(CartVO cartVO);
	
	//장바구니 선택삭제
	void deleteCart(CartVO cartVO);
	
	//buyInfo로 이동
	List<CartVO> buyInfo(CartVO cartVO);
	
	//상세페이지 구매하기 버튼
	ItemVO detailBuy(String itemCode);
	
	
}
