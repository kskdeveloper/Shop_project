package kh.study.shop.buy.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.shop.buy.vo.BuyDetailVO;
import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

@Service("buyService")
public class BuyServiceImpl implements BuyService{

	@Autowired
	private SqlSessionTemplate sqlSession;

	//장바구니 구매확정
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void buyCart(BuyVO buyVO, CartVO cartVO) {

		sqlSession.insert("buyMapper.cartBuy", buyVO);
		sqlSession.insert("buyMapper.cartBuyDetail", buyVO);
		
		//단일구매 확정 시 
		if(cartVO != null) {
			sqlSession.delete("cartMapper.deleteCart", cartVO);
		}
		
	}
	
	//buyCode 가져오기
	@Override
	public String buyCode() {

		return sqlSession.selectOne("buyMapper.buyCode");
	}

	//장바구니 데이터의 조회
	@Override
	public List<CartVO> cartBuyInfo(CartVO cartVO) {
		
		return sqlSession.selectList("buyMapper.cartBuyInfo", cartVO);
	}

	//구매 상세내역 조회
	@Override
	public List<BuyDetailVO> buyHistory(String memberId) {
		
		return sqlSession.selectList("buyMapper.buyHistory", memberId);
	}

	//구매내역
	@Override
	public List<BuyVO> buyList(String memberId) {
		return sqlSession.selectList("buyMapper.buyList", memberId);
	}
	
}
