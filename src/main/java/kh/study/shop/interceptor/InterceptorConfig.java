package kh.study.shop.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//카테고리 인터셉터가 모든 페이지로 이동할 때 적용되도록 구성
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getCategoryInterceptor())
				.addPathPatterns("/**/**")
				.excludePathPatterns("/admin/selectUseCategoryListAjax"
									, "/admin/updateStatus"
									, "/admin/memberList"
									, "/item/changeItemStatus"
									, "/item/updateStock"
									, "/cart/regCart"
									, "/cart/changeCnt"
									, "/admin/memberDetail");
		//또는 .excludePathPatterns("/**/**Ajax"); 이런식으로 ajax 사용하는 것들을
		// 실행 이름을 ajax로 맞추어 적용되도록 만든다. 
	}
	
	@Bean
	public CategoryInterceptor getCategoryInterceptor() {
		
		return new CategoryInterceptor();
	}
	
	//다만 ajax랑 동시에 사용불가! interceptor 제거해줘야 함
	//또는 excludePathPatterns을 사용해야 한다.
	
}
