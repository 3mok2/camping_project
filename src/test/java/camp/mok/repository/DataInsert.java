package camp.mok.repository;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardVO;
import lombok.extern.log4j.Log4j;

@Log4j
public class DataInsert extends AppTest{

	@Autowired
	BoardRepository boardRepository;
	
	@Test
	@Ignore
	public void dataInsert() {
		for(int i=1; i<=408; i++) {
			ArticleVO boardVO = new ArticleVO().builder()
						.title("제목 : 페이징 처리 "+ i)
						.content("내용 : 페이징 처리 " + i)
						.writer("작성자" + (i%5))
						.build();
			boardRepository.insert(boardVO);
		}
	}
	
	@Test
	public void test() {
		
		for(int i=1;i<=212;i++) {
			ArticleVO vo = ArticleVO.builder()
					.title("제목 : 스프링 정보처리기사 " + i)
					.content("내용 : 자바 오라클 " + i)
					.writer("작성자" + (i%5))
					.build();
			boardRepository.insert(vo);			
		}
		
		for(int i=1;i<=212;i++) {
			ArticleVO vo = ArticleVO.builder()
					.title("제목 : 오라클 " + i)
					.content("내용 : 정보처리기사 " + i)
					.writer("글쓴이" + (i%5))
					.build();
			boardRepository.insert(vo);			
		}
		
		for(int i=1;i<=212;i++) {
			ArticleVO vo = ArticleVO.builder()
					.title("제목 : 자바 " + i)
					.content("내용 : 스프링 정보처리기사 " + i)
					.writer("관리자" + (i%5))
					.build();
			boardRepository.insert(vo);			
		}
		
		for(int i=1;i<=212;i++) {
			ArticleVO vo = ArticleVO.builder()
					.title("제목 : 테스트 데이터 " + i)
					.content("내용 : 스프링부트 " + i)
					.writer("스프링" + (i%5))
					.build();
			boardRepository.insert(vo);			
		}
	}
}
