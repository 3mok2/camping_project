package camp.mok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camp.mok.domain.Criteria;
import camp.mok.domain.ReplyPageDTO;
import camp.mok.domain.ReplyVO;
import camp.mok.repository.BoardRepository;
import camp.mok.repository.ReplyRepository;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		boardRepository.updateReplyCnt(vo.getBno(), 1); // 등록 시 댓글카운트 +1
		return replyRepository.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		return replyRepository.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return replyRepository.update(vo);
	}

	@Transactional // sql문 2개 동시작동
	@Override
	public int remove(Long rno) {
		ReplyVO vo = replyRepository.read(rno); // rno를 읽어서
		boardRepository.updateReplyCnt(vo.getBno(), -1); // -1
		return replyRepository.delete(rno);
	}

	@Override
	public ReplyPageDTO getList(Criteria criteria, Long bno) {
		return new ReplyPageDTO(
				replyRepository.getReplyCount(bno),
				replyRepository.getList(bno, criteria));
	}
}
