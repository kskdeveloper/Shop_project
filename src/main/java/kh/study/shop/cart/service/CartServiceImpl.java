package kh.study.shop.cart.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.vo.ItemVO;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	//장바구니에 담기
	@Override
	public void regCart(CartVO cartVO) {
		sqlSession.insert("cartMapper.insertCart", cartVO);
	}

	//장바구니 리스트
	@Override
	public List<CartVO> cartList(String memberId) {
		return sqlSession.selectList("cartMapper.cartList", memberId);
	}
	
	//장바구니 상품수량 변경
	@Override
	public void changeCnt(CartVO cartVO) {
		sqlSession.update("cartMapper.changeCnt", cartVO);
	}
	
	//장바구니 선택삭제
	@Override
	public void deleteCart(CartVO cartVO) {
		sqlSession.delete("cartMapper.deleteCart", cartVO);
	}
	
	//buyInfo로 이동
	@Override
	public List<CartVO> buyInfo(CartVO cartVO) {
		
		return sqlSession.selectList("cartMapper.buyInfo", cartVO);
	}
	
	//상세페이지 구매하기 버튼
	@Override
	public ItemVO detailBuy(String itemCode) {
		
		return sqlSession.selectOne("cartMapper.detailBuy", itemCode);
	}
	
}
