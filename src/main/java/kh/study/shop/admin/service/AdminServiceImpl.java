package kh.study.shop.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;
import kh.study.shop.item.vo.CateVO;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	//카테코드 등록
	@Override
	public void regCate(CateVO cateVO) {
		sqlSession.insert("adminMapper.regCate", cateVO);
	}
	
	//카테고리 리스트
	@Override
	public List<CateVO> cateList() {
		
		return sqlSession.selectList("adminMapper.cateList");
	}
	
	//status 카테고리 리스트
	@Override
	public List<CateVO> statusCateList() {
			
		return sqlSession.selectList("adminMapper.statusCateList");
	}

	//status 변경 
	@Override
	public void updateStatus(CateVO cateVO) {
		sqlSession.update("adminMapper.updateStatus", cateVO);
		
	}

	//상품등록(트렌젝션처리)
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void regItem(ItemVO itemVO) {
		
		sqlSession.insert("adminMapper.regItem", itemVO);
		sqlSession.insert("adminMapper.insertImgs", itemVO);
		
	}
	
	//아이템 코드조회
	@Override
	public String getItemCode() {
		return sqlSession.selectOne("adminMapper.getItemCode");
	}

}
