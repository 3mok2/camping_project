package camp.mok.service;

import java.util.List;

import camp.mok.domain.Criteria;
import camp.mok.domain.ReplyPageDTO;
import camp.mok.domain.ReplyVO;

public interface ReplyService {

	int register(ReplyVO vo);
	
	ReplyVO get(Long bno);
	
	int modify(ReplyVO vo);
	
	int remove(Long rno);
	
	ReplyPageDTO getList(Criteria criteria, Long bno);
}
