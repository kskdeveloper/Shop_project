package kh.study.shop.item.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.admin.service.AdminService;
import kh.study.shop.config.ShopDateUtil;
import kh.study.shop.item.service.ItemService;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;


@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource(name = "itemService")
	private ItemService itemService;
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
		@ModelAttribute	//해당 컨트롤러 실행되기 전 무조건 호출된다.
		public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
			
			model.addAttribute("menu", menu);
			
			//status 카테고리 리스트 (사용 중인 것)
			//model.addAttribute("statusList", adminService.statusCateList());
		}
	
	
	//상품 목록 페이지
	@GetMapping("/list")
	//boolean isLoginFail 로그인 실패할 때만 데이터를 true로 받아온다
	public String itemList(boolean isLoginFail, Model model
						   ,@CookieValue(required = false) String imgName) { //a.jpg , b.jpg 이런식으로 넘어 올 것
		//쿠키 가져올 때 annotation 넣어 준다 쿠키명을 똑같이 작성하기
		//(required = false) 쿠키가 있으면 넘기고 없으면 안넘겨도 돼!
		// name = "cookie" 상세페이지에서 지정한 쿠키 이름이 String itemName과 이름이 달라도 name에서
		//상세페이지 쿠키 이름을 작성해 준다면 쿠키 데이터를 받을 수 있다.
		
		//로그인 성공, 실패 여부 데이터를 html에 전달
		model.addAttribute("isLoginFail", isLoginFail);
		
		//아이템리스트
		model.addAttribute("itemList", itemService.mainItem());
		
		
		List<String> cookieList = new ArrayList<>();
		//쿠키 데이터 보내기(//a.jpg만 넘어오거나 a.jpg, b.jpg 이런식으로 넘어 올수도 있다.
		if(imgName != null) {
			String[] cookieArr = imgName.split(",");
			cookieList = Arrays.asList(cookieArr);
		}
		model.addAttribute("cookie_imgName", cookieList);
		
		return "content/item/item_list";	//templates 기준
		
	}
	
	//상품목록조회 페이지
	@RequestMapping("/itemList")//요청된 것 map에 다 넣어달라 key: name value:입력한 값
	public String selectItem (@RequestParam Map<String, String> paramMap, Model model){
		
		//카테고리 리스트 
		model.addAttribute("cateList", adminService.cateList());
		
		//상품목록조회
		model.addAttribute("itemList", itemService.selectItem(paramMap));
		
		//현재 날짜 (우리가 static 붙여놨기에 객체 만들지 x 가능)
		String nowDate = ShopDateUtil.getNowDateToString("-"); //2022-10-05
		
		//한달 전 날짜
		String aMonthAgo = ShopDateUtil.getAmonthAgoToString();
		
		//넘어오는 fromDate가 없다면 한달 전 날짜로 set 하겠다
		if(paramMap.get("fromDate")==null) {
			
			paramMap.put("fromDate", aMonthAgo);
		}
		
		if(paramMap.get("toDate")==null) {
			paramMap.put("toDate", nowDate);
		}
		
		model.addAttribute("paramMap", paramMap);
		
		
		//side active
		//model.addAttribute("menu", "2");
		
		return "content/admin/item_manage"; 
	}
	
	
	//상품목록 상태변경(ajax)
	@PostMapping("/changeItemStatus")
	@ResponseBody
	public void changeItemStatus(ItemVO itemVO) {
		itemService.changeItemStatus(itemVO);
		
	}
	
	//상품목록 재고수량변경(ajax)
	@PostMapping("/updateStock")
	@ResponseBody
	public void updateStock(ItemVO itemVO) {
		itemService.changeStock(itemVO);
	}

	
	//아이템 상세페이지
	@GetMapping("/itemDetail")
	public String itemDetail(String itemCode, Model model
			 				, HttpServletResponse response // imgName 데이터를 cookieImgName라고 받겠다.
			 				, @CookieValue(required = false, name = "imgName") String cookieImgName) {
		
		ItemVO item = itemService.itemDetail(itemCode);
		
		model.addAttribute("item", item);
		
		//상세보기한 상품의 이름을 쿠키에 저장 javax.servlet.cookie
		//쿠키를 만들다
		Cookie cookie = new Cookie("name","java");
		
		//매개변수: 쿠키 데이터 유지시간(초) 60*60*24 -> 하루 / 60*60 -> 1시간
		//cookie.setMaxAge(0); //브라우저가 종료 시 쿠키 데이터 소멸
		cookie.setMaxAge(60); 
		
		//생성된 쿠키 데이터를 클라이언트에게 전달 (페이지 이동할 때 내가 만든 쿠키를 가져가겠다) 
		response.addCookie(cookie);
		
		//쿠키 하나 더 생성
		Cookie cookie2 = new Cookie("age", "22");
		response.addCookie(cookie2);
		
		//쿠키 유지시간 작성하지 않는다면 브라우저 종료 시 데이터 소멸
		
	    //상세보기 한 상품의 대표 이미지를 쿠키로 저장
		String imgName = "";
	    for(ImgVO img : item.getImgList()) {
	    	if(img.getIsMain().equals("Y")) {
	    		imgName = img.getAttachedName();
	    	}
	    }
	    
	    //java.net  (imgName을 어떤방식으로 인코딩 할 것인지)
	    //인코딩만 진행하면 공백이 + 문자로 바뀐다. ex) "안 녕" -> "안+녕"
	    //replaceAll첫번째 매개변수를 두번째 매개변수로 바꾸겠다 (+를 공백으로 바꾸겠다)
	    String encodeImgName = null;
	    	//최근 본 상품 여러장 나오도록 할 때
	    	if(cookieImgName != null) {  //쿠키 이미지 + 새롭게 들어오는 이미지
	    		encodeImgName = getEncodeStr(cookieImgName + "," + imgName);
	    	}
	    	else {
	    		//없다면 상세보기한 이미지 하나만 뜨도록
	    		encodeImgName = getEncodeStr(imgName);
	    	}
	    
	    
	    Cookie cookie_imgName = new Cookie("imgName", encodeImgName);
		response.addCookie(cookie_imgName);
		
		return "content/item/item_detail";
	}
	
	//길게 작성하면 복잡하니까 따로 메소드로 빼준다.(인코딩 tryCatch)
	public String getEncodeStr(String str) {
	    //java.net  (imgName을 어떤방식으로 인코딩 할 것인지)
	    //인코딩만 진행하면 공백이 + 문자로 바뀐다. ex) "안 녕" -> "안+녕"
	    //replaceAll첫번째 매개변수를 두번째 매개변수로 바꾸겠다 (+를 공백으로 바꾸겠다)
	    String result = "";
	    try {				//매개 변수로 들어오 것을 인코딩 시키겠다
	    	result = URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	    return result;
	}
	
	
}
