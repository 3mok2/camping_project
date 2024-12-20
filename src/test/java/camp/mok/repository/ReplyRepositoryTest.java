package camp.mok.repository;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.Criteria;
import camp.mok.domain.ReplyVO;
import lombok.extern.log4j.Log4j;

@Log4j
public class ReplyRepositoryTest extends AppTest{

	@Autowired
	ReplyRepository replyRepository;
	
	@Test
	@Ignore
	public void InsertTest() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			ReplyVO vo = ReplyVO.builder()
					.bno(1L)
					.reply("댓글 테스트" + i)
					.replier("모기")
					.build();
			replyRepository.insert(vo);
		});
	}
	
	@Test
	@Ignore
	public void readTest() {
		ReplyVO vo = replyRepository.read(1L);
		log.info(vo);
	}
	
	@Test
	@Ignore
	public void updateTest() {
		ReplyVO vo = new ReplyVO();
		vo.setReply("댓글 테스트 -- 수정");
		vo.setRno(2L);
		replyRepository.update(vo);
	}
	
	@Test
	@Ignore
	public void deleteTest() {
		replyRepository.delete(3L);
	}
	
	@Test
	@Ignore
	public void getListTest() {
		replyRepository.getList(1L, new Criteria())
			.forEach(r -> log.info(r));
	}

}
