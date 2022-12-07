package kh.study.shop.item.service;

import java.util.List;
import java.util.Map;

import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;

public interface ItemService {
	//상품조회
	List<ItemVO> selectItem(Map<String, String> paramMap);
	
	//상품 상태 변경
	void changeItemStatus(ItemVO itemVO);
	
	//재고수량 변경
	void changeStock(ItemVO itemVO);
	
	//상품 상세페이지 (사진파일 여러개)
	ItemVO itemDetail(String itemCode);
	
	//상품 메인화면
	List<ItemVO> mainItem();
	
}
