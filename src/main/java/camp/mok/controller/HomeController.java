package camp.mok.controller;

import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {

	public HomeController() {
		log.info("HomeController 빈 생성");
	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@GetMapping("/menu/come")
	public String come() {
		return "menu/come";
	}
}