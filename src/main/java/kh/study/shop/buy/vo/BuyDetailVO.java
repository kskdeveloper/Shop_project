package kh.study.shop.buy.vo;

import kh.study.shop.item.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyDetailVO {

	private String buyDetailCode;
	private String itemCode;
	private String buyCode;
	private int buyAmount;
	
	//여러개 들어가야 하니까 DETAIL이 List이기 때문에 
	private ItemVO itemInfo;
	
	private int buyPrice;
}
