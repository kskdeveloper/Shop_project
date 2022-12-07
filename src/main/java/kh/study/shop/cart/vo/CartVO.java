package kh.study.shop.cart.vo;

import java.util.List;

import kh.study.shop.item.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVO {

	private String cartCode;
	private String itemCode;
	private String memberId;
	private int cartAmount;
	private String regDate;
	private int totalPrice;
	
	//association (1:1 관계)
	private ItemVO itemList;
	
	//cartCode 여러개 담길 리스트
	private List<String> cartCodeList;
	
	
	
}
