package camp.mok.repository;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import camp.mok.config.MvcConfig;
import camp.mok.config.RootConfig;
import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Category;
import camp.mok.domain.Criteria;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, MvcConfig.class} )
@WebAppConfiguration
@Log4j
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	@Ignore
	public void testGetList() {
		Category category = new Category();
		category.setCateId("notice");
		Criteria criteria = new Criteria();
		criteria.setCategory(category);
		criteria.setType("T");
		criteria.setKeyword("자바");
		List<ArticleVO> list = boardRepository.getList(new Criteria());
		list.forEach(board -> log.info(board));
	}
	
	@Test
	@Ignore
	public void totalCountTest() {
		Category category = new Category();
		category.setCateId("notice");
		Criteria criteria = new Criteria();
		criteria.setType("T");
		criteria.setKeyword("오라클");
		criteria.setCategory(category);
		int totalCount = boardRepository.getTotalCount(criteria);
		log.info(totalCount);
	}
	
	@Test
	@Ignore
	public void testInsert() {
		ArticleVO vo = ArticleVO.builder()
				.bno(5L)
				.title("안녕")
				.content("내용")
				.writer("모기")
				.build();
		boardRepository.insert(vo);
	}
	
	@Ignore
	@Test
	public void testRead() {
		ArticleVO boardVO = boardRepository.read(5L);
		log.info(boardVO);
	}
	
	@Ignore
	@Test
	public void testDelete() {
		boardRepository.delete(5L);
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		ArticleVO boardVO = new ArticleVO().builder()
				.title("수정")
				.content("내용 수정")
				.bno(1L)
				.build();
		boardRepository.update(boardVO);
	}
	
	@Test
	@Ignore
	public void testSearch() {
		Criteria criteria = new Criteria();
		criteria.setType("TC");
		criteria.setKeyword("이지");
		List<ArticleVO> list = boardRepository.getList(criteria);
	}
}

