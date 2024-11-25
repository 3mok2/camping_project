package camp.mok.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardAttachVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Category;
import camp.mok.domain.Criteria;
import camp.mok.domain.LikeDTO;
import camp.mok.domain.Pagination;
import camp.mok.repository.CategoryRepository;
import camp.mok.service.BoardService;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board")
@Log4j
public class BoardController {
	
	private static final List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
//	@GetMapping("/list")
//	public void list(Model model, Criteria criteria) {
//		log.info("list.jsp");
//		model.addAttribute("list", boardService.getList(criteria));
//		model.addAttribute("p", new Pagination(criteria, boardService.totalCount(criteria)));
//	}
	
	@GetMapping({"", "/", "/{cateId}"})
	public String list(Model model, Criteria criteria,
			@PathVariable(required = false) String cateId) throws NoHandlerFoundException {
		String cateName = null;
		log.info(cateId);
		if(cateId!=null) { // 카테id null이 아닐 때
			Category category = categoryRepository.readByCateId(cateId);
			
			if(cateId.equals("gallery")) { // gallery 일 때
				criteria.setCategory(category);
				cateName = category.getCateName(); // 카테고리 이름 불러오기
				model.addAttribute("cateTitle", cateName);
				criteria.setAmount(6);
				model.addAttribute("list", boardService.getList(criteria));
				model.addAttribute("p", new Pagination(criteria, boardService.totalCount(criteria)));
				return "board/gallery";
			}
			
			if(category==null) { // 카테고리가 존재하지 않으면 예외 발생
				throw new NoHandlerFoundException("get", "/board_ex_cate/board" + cateId, new HttpHeaders());
			}
			criteria.setCategory(category);
			cateName = category.getCateName(); // 카테고리 이름 불러오기
			log.info(cateName);
		}		
		model.addAttribute("cateTitle", cateName);
		model.addAttribute("list", boardService.getList(criteria));
		model.addAttribute("p", new Pagination(criteria, boardService.totalCount(criteria)));
		
		return "board/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/register")
	public void register(Model model) {
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(ArticleVO board, RedirectAttributes rttr, String cateId, Criteria criteria) {
		log.info(board.getAttachList());
		
		List<BoardAttachVO> attachList = board.getAttachList();
        String galleryImg = "";

        if (attachList != null && !attachList.isEmpty()) {
            for (BoardAttachVO attach : attachList) {
                if (isImageFile(attach.getFileName())) {
                    String fileName = attach.getFileName();
                    String uploadPath = attach.getUploadPath();
                    String uuid = attach.getUuid();
                    galleryImg = uploadPath + "/" + uuid + "_" + fileName; // 첫 번째 이미지를 찾았으면 설정하고 반복문 종료
                    break;
                }
            }
        }
        
        board.setGalleryImg(galleryImg);
		
		boardService.register(board);
		rttr.addFlashAttribute("cate", board.getCateId());
		rttr.addFlashAttribute("result", board.getBno());
		rttr.addFlashAttribute("operation", "register");
		
		if(criteria.getCategory()!=null) {
			return "redirect:/board/"+board.getCateId();
		}
		return "redirect:/board/"+board.getCateId();
	}

	private boolean isImageFile(String fileName) {
		String extension = FilenameUtils.getExtension(fileName);
        return imageExtensions.contains(extension.toLowerCase());	}

	@GetMapping("/get")
	public void get(@RequestParam("bno")Long bno, Model model, Criteria criteria, String cateId) {
		ArticleVO vo = boardService.get(bno);
		if(cateId!=null && cateId!="") {
			Category category = new Category();
			category.setCateId(cateId);
			criteria.setCategory(category);
		}
		log.info(vo);
		model.addAttribute("board", vo);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String modify(Long bno, Model model, Criteria criteria, Authentication auth) {
		ArticleVO vo = boardService.get(bno);
		String username = auth.getName(); // 인증된 사용자 계정
		if(!vo.getWriter().equals(username) &&
				!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			throw new AccessDeniedException("Access denied");
		}
		model.addAttribute("board", vo);
		return "board/modify";
	}
	
	@PreAuthorize("isAuthenticated() and principal.username == #board.writer or hasRole('ROLE_ADMIN')")
	@PostMapping("/modify")
	public String modify(ArticleVO board, RedirectAttributes rttr, Criteria criteria) {
		
//		List<BoardAttachVO> attachList = board.getAttachList();
//		
//		List<BoardAttachVO> insertList = attachList.stream()
//				.filter(attach -> attach.getBno()==null).collect(Collectors.toList());
//		log.info("새로 추가 : " + insertList);
//		
//		List<BoardAttachVO> delList = attachList.stream()
//				.filter(attach -> attach.getBno()!=null).collect(Collectors.toList());
//		log.info("삭제 목록" + delList);
		
		
		
		if(boardService.modify(board)) {
			rttr.addFlashAttribute("result", board.getBno());
			rttr.addFlashAttribute("operation", "modify");
		}
		
		if(criteria.getCategory()!=null) {
			return "redirect:/board/"+criteria.getCategory().getCateId()+criteria.getListLink();
		}
//		rttr.addAttribute(criteria.getPageNum());
//		rttr.addAttribute(criteria.getAmount());
//		rttr.addAttribute(criteria.getType());
//		rttr.addAttribute(criteria.getKeyword());
		return "redirect:/board/list" + criteria.getListLink(); // 위에 4줄 주석이 다들어감. criteria에 getListLink 메서드 확인
	}
	
	@PreAuthorize("isAuthenticated() and principal.username == #writer or hasRole('ROLE_ADMIN')")
	@PostMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr, Criteria criteria, String writer) {
		if(boardService.remove(bno)) {
			rttr.addFlashAttribute("result", bno);
			rttr.addFlashAttribute("operation", "remove");
		}
		if(criteria.getCategory()!=null) {
			return "redirect:/board/"+ criteria.getCategory().getCateId()+criteria.getListLink();
		}
		return "redirect:/board/list" + criteria.getListLink();
	}
	
	@GetMapping("/getAttachList")
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		return new ResponseEntity<List<BoardAttachVO>>(boardService.getAttachList(bno), HttpStatus.OK);
	}
	
	@GetMapping("/getAttachFileInfo")
	@ResponseBody
	public ResponseEntity<BoardAttachVO> getAttach(String uuid) {
		return new ResponseEntity<BoardAttachVO>(boardService.getAttach(uuid), HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/like", produces = "plain/text; charset=utf-8")
	public ResponseEntity<String> hitLike(LikeDTO likeDTO) {
		log.info(likeDTO.getMemberId());
		log.info(likeDTO.getBno());
		String message = likeDTO.getBno() + "번";
		if(boardService.hitLike(likeDTO)) {
			message += "게시글을 추천하였습니다.";
		} else {
			message += "게시글 추천을 취소하였습니다.";
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@PostMapping(value = "/isLike")
	@ResponseBody
	public ResponseEntity<Boolean> isLike(LikeDTO likeDTO) {
		return new ResponseEntity<Boolean>(boardService.isLike(likeDTO),HttpStatus.OK);
	}
}
