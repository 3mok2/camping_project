package camp.mok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import camp.mok.domain.Criteria;
import camp.mok.domain.ReplyPageDTO;
import camp.mok.domain.ReplyVO;
import camp.mok.service.ReplyService;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/replies")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/new")
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		int result = replyService.register(vo);
		return result == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/pages/{page}/{bno}")
	public ResponseEntity<ReplyPageDTO> getList(
			@PathVariable Integer page, @PathVariable Long bno){
		Criteria criteria = new Criteria(page, 10);
		ReplyPageDTO list = replyService.getList(criteria, bno);
		log.info(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostAuthorize("isAuthenticated()")
	@GetMapping("/{rno}")
	public ResponseEntity<ReplyVO> get(@PathVariable Long rno) {
		return new ResponseEntity<ReplyVO>(replyService.get(rno), HttpStatus.OK);
	}
	
	@PostAuthorize("isAuthenticated()")
	@DeleteMapping("/{rno}")
	public ResponseEntity<String> remove(@PathVariable Long rno) {
		int result = replyService.remove(rno);
		return result == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable Long rno) {
		vo.setRno(rno);
		int result = replyService.modify(vo);
		return result == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
