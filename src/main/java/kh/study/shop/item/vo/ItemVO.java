package kh.study.shop.item.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVO {

	private String itemCode;
	private String itemName;
	private int itemPrice;
	private String itemComment;
	private String regDate;
	private int itemStock;
	private String cateCode;
	private String itemStatus;
	
	//association (1:1 관계)
	private CateVO cateInfo;
	
	//이미지 정보 여러개 (1 : N 관계)
	private List<ImgVO> imgList;
}
