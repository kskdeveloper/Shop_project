package kh.study.shop.test.date;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/date")
public class DateController {
	//현재날짜
	@GetMapping("/test1")
	public String test1() {
		//calendar 사용(객체 생성) 싱글톤패턴
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR); //연도 정보 가져옴
		int month = cal.get(Calendar.MONTH)+1; //+1해줘야 현재 월 불러온다
		
		String monthStr = "";
//		if(month / 10 == 0) {
//			monthStr ="0" + month; //1~9월까지
//		}
//		else {
//			monthStr= month+"";
//		}
		
		//삼항연산자
		monthStr = month/10 ==0 ? "0" + month : month+"";
		
		
		int date = cal.get(Calendar.DATE);
		//삼항연산자
		String dateStr = date/10 ==0 ? "0" + date : date+"";
		
		
		String nowDate = year + "_"+ monthStr + "_"+dateStr;
		System.out.println("오늘 날짜는"+nowDate);
		
		return nowDate;
	}

}
