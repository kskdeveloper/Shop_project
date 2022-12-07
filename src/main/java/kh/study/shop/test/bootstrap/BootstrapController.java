package kh.study.shop.test.bootstrap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boot")
public class BootstrapController {

	@GetMapping("/test1")
	public String test1() {
		
		return "test/bootstrap/test1";
	}
	
	//그리드 사용예시
	@GetMapping("/grid01")
	public String grid01() {
		return "test/bootstrap/grid01";
	}
	
}
