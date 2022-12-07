package kh.study.shop.test.fragment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frag")
public class FragmentController {
	
	@GetMapping("/test1")
	public String test1() {
		//return "test/frag/content/test1";
		return "test/frag/content/test2";
		
	}
	
	
	
	
}
