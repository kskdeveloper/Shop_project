package kh.study.shop.admin.service;

import java.util.List;

import kh.study.shop.item.vo.CateVO;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;

public interface AdminService {
	
	//카테코드 등록
	void regCate(CateVO cateVO);
	
	//카테고리 리스트
	List<CateVO> cateList ();
	
	//status 카테고리 리스트
	List<CateVO> statusCateList ();
	
	
	//status 변경
	void updateStatus(CateVO cateVO);
	
	
	//상품등록
	void regItem(ItemVO itemVO);
	
	//아이템코드 조회
	String getItemCode();
	
	//이미지등록 (상품 등록시 함께 진행할 것이기 때문에)
	//void insertImg(ImgVO imgVO);
	
}