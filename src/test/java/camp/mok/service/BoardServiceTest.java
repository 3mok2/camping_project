package camp.mok.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Criteria;
import lombok.extern.log4j.Log4j;


@Log4j
public class BoardServiceTest extends AppTest{

	@Autowired
	private BoardService service;
	
	@Test
	@Ignore
	public void testRegitser() {
		ArticleVO vo = ArticleVO.builder()
					.title("작성글")
					.content("작성 내용")
					.writer("모기목스")
					.build();
		service.register(vo);
		log.info(vo.getBno()+"번 글 생성");
	}
	
	@Test
	@Ignore
	public void testGetList() {
		service.getList(new Criteria(3,10)).forEach(board -> log.info(board));
	}
	
	@Test
	@Ignore
	public void testGet() {
		service.get(6L);
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		ArticleVO boardVO = new ArticleVO().builder()
					.title("수정")
					.content("수정 내용")
					.bno(6L)
					.build();
		service.modify(boardVO);
	}
	
	@Test
	public void testRemove() {
		service.remove(7L);
	}
}
