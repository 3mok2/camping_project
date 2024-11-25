package camp.mok.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.MemberVO;
import camp.mok.service.MemberService;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class MyPageController {

	@Autowired
	private MemberService memberService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@GetMapping("/myPage/myWrite")
	public String myWrite(Model model, Principal principal) {
		String memberId = principal.getName();
		List<ArticleVO> listById = memberService.getListById(memberId);
		model.addAttribute("listById", listById);
		log.info(listById);
		return "myPage/myWrite";
	}
	
	@GetMapping("/myPage/myLike")
	public String myLike() {
		return "myPage/myLike"; 
	}
	
	@GetMapping("/myPage/myReplies")
	public String myReplies() {
		return "myPage/myReplies"; 
	}
}
