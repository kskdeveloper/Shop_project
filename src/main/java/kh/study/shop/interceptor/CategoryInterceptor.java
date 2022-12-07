package kh.study.shop.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kh.study.shop.admin.service.AdminService;

//카테고리 목록을 조회하는 공통기능 정의
public class CategoryInterceptor implements HandlerInterceptor {
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//예전 방법이라 구현할 때마다 오류남 
		//modelAndView.addObject("statusList", adminService.statusCateList());
		
		//실제로 데이터 보낼 때는 request 사용하여 보내기.
		request.setAttribute("statusList", adminService.statusCateList());
	}

}
