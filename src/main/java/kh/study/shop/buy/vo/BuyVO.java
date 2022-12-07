package kh.study.shop.buy.vo;

import java.util.List;

import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyVO {

	private String buyCode;
	private String memberId;
	private String buyDate;
	private int totalPrice;
	
	//필요에 의하여 추가한 것
	private int buyCnt;


	private List<BuyDetailVO> buyDetailList;
	
	
}

