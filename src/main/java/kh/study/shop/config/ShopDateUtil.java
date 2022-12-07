package kh.study.shop.config;

import java.util.Calendar;

public class ShopDateUtil {

	//오늘 날짜 문자열로 리턴
	public static String getNowDateToString() {
		
		//calendar 사용(객체 생성) 싱글톤패턴
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR); //연도 정보 가져옴
		int month = cal.get(Calendar.MONTH)+1; //+1해줘야 현재 월 불러온다
		
		//삼항연산자
		String monthStr = month/10 ==0 ? "0" + month : month+"";
		
		
		int date = cal.get(Calendar.DATE);
		//삼항연산자
		String dateStr = date/10 ==0 ? "0" + date : date+"";
		
		
		String nowDate = year + ""+ monthStr + ""+ dateStr;
	
		return nowDate;
		
	}
	//오늘 날짜 문자열로 리턴 + 포맷지정  getNowDateToString("-");
	public static String getNowDateToString(String format) {
		
		//calendar 사용(객체 생성) 싱글톤패턴
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR); //연도 정보 가져옴
		int month = cal.get(Calendar.MONTH)+1; //+1해줘야 현재 월 불러온다
		
		//삼항연산자
		String monthStr = month/10 ==0 ? "0" + month : month+"";
		
		
		int date = cal.get(Calendar.DATE);
		//삼항연산자
		String dateStr = date/10 ==0 ? "0" + date : date+"";
		
		
		String nowDate = year + format + monthStr + format+ dateStr;
		
		return nowDate;
		
	}
	//한달 전 날짜 문자열 리턴
	public static String getAmonthAgoToString() {
		
		//calendar 사용(객체 생성) 싱글톤패턴
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR); //연도 정보 가져옴
		int month = cal.get(Calendar.MONTH); //+1해줘야 현재 월 불러온다
		
		//삼항연산자
		String monthStr = month/10 ==0 ? "0" + month : month+"";
		
		
		int date = cal.get(Calendar.DATE);
		//삼항연산자
		String dateStr = date/10 ==0 ? "0" + date : date+"";
		
		
		String nowDate = year + "-"+ monthStr + "-"+ dateStr;
	
		return nowDate;
		
	}

}
