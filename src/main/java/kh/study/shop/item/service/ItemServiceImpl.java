package kh.study.shop.item.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	//상품 조회
	@Override
	public List<ItemVO> selectItem(Map<String, String> paramMap) {
		
		return sqlSession.selectList("itemMapper.selectItem", paramMap);
	}

	//상품 상태변경
	@Override
	public void changeItemStatus(ItemVO itemVO) {
		sqlSession.update("itemMapper.changeItemStatus", itemVO);
		
	}
	//재고수량 변경
	@Override
	public void changeStock(ItemVO itemVO) {
		sqlSession.update("itemMapper.changeStock", itemVO);
		
	}
	
	//상품 상세페이지
	@Override
	public ItemVO itemDetail(String itemCode) {
		return sqlSession.selectOne("itemMapper.itemDetail", itemCode);
	}
	
	//상품 메인화면
	@Override
	public List<ItemVO> mainItem() {
		
		return sqlSession.selectList("itemMapper.mainItem");
	}
	
	
}
