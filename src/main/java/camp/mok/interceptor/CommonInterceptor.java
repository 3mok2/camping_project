package camp.mok.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import camp.mok.repository.CategoryRepository;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class CommonInterceptor implements HandlerInterceptor {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if(modelAndView!=null) {
			modelAndView.addObject("cate", categoryRepository.selectAll());
		} 

//		if (modelAndView==null) {
// 			ModelAndViewNullException();
// 		}
//	}
//
//	private void ModelAndViewNullException() {
//		log.info("예외처리.");
//	}
	}
}
