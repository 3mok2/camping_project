package camp.mok.repository;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.BoardAttachVO;
import lombok.extern.log4j.Log4j;

@Log4j
public class BoardAttachRepositoryTest extends AppTest{

	@Autowired
	BoardAttachRepository boardAttachRepository;
	
	@Test
	@Ignore
	public void testInsert() {
		BoardAttachVO vo = new BoardAttachVO();
		vo.setBno(1L);
		vo.setFileName("test02.txt");
		vo.setFileType(false);
		vo.setUploadPath("c:/upload");
		String uuid = UUID.randomUUID().toString();
		vo.setUuid(uuid);
		boardAttachRepository.insert(vo);
	}
	
	@Test
	@Ignore
	public void testSelectByBno() {
		boardAttachRepository.selectByBno(1L)
			.forEach(file -> log.info(file));
	}
	
	@Test
	@Ignore
	public void testDelete() {
		String uuid = "1976b43b-2bc4-4b14-8106-a01036588001";
		boardAttachRepository.delete(uuid);
	}

}
