package camp.mok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.CalendarVO;
import camp.mok.domain.Category;
import camp.mok.domain.Criteria;
import camp.mok.domain.DeckVO;
import camp.mok.service.CalendarService;
import camp.mok.service.DeckService;
import lombok.extern.log4j.Log4j;

@RequestMapping("/deck")
@Controller
@Log4j
public class DeckController {
	
	@Autowired
	private DeckService deckService;
	
	@Autowired
	private CalendarService calendarService;
	
	@GetMapping("/reservation")
	public String reservation(DeckVO vo, Model model) {
		List<DeckVO> list = deckService.getList();
		
		model.addAttribute("list", list);
		return "deck/reservation";
	}
	
	@GetMapping("/decks") // 예약 현황 페이지로
	public String decks(@RequestParam Long dno, Model model) {
		DeckVO vo = deckService.get(dno);
		List<DeckVO> list = deckService.getList();
		List<CalendarVO> reserve = calendarService.read(dno); // 달력에 저장된 예약데이터베이스 가져오기
		model.addAttribute("deck", vo);
		model.addAttribute("list", list);
		model.addAttribute("reserve", reserve);
		return "deck/decks";
	}
	
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	@GetMapping("/deckList") // 관리자만 덱 리스트 페이지로.
	public String deckList(Model model) {
		model.addAttribute("deckList", deckService.getList());
		return "deck/deckList";
	}
	
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	@PostMapping("/updateDeckName")
	public String updateDeckName(DeckVO vo, RedirectAttributes rttr) {
		
		if(deckService.modify(vo)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/deck/deckList";
	}
	
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	@PostMapping("/insertDeck")
	public String insertDeck(DeckVO vo, RedirectAttributes rttr) {
		log.info("실행됨.");
		deckService.insertDeck(vo);
		rttr.addFlashAttribute("result", vo.getDno());
		return "redirect:/deck/deckList";
	}
	
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	@PostMapping("/removeDeck")
	public String remove(Long dno, RedirectAttributes rttr) {
		if(deckService.remove(dno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/deck/deckList";
	}
	
	@PostMapping("/insertSchedule")
	public String insertSchedule(@RequestParam Long dno, CalendarVO vo, RedirectAttributes rttr, Model model) {
		String message = null;
		try {
			calendarService.insert(vo);
		} catch (Exception e) {
			message = "이미 등록된 날짜입니다";
		}
		calendarService.getReserve();
		model.addAttribute("error", message);
		return "redirect:/deck/decks?dno=" + dno;
	}
}
