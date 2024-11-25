package camp.mok.repository;

import java.util.List;

import camp.mok.domain.DeckVO;

public interface DeckRepository {

	void insert(DeckVO vo);
	
	List<DeckVO> getList();
	
	int update(DeckVO vo);
	
	int delete(Long dno);
	
	DeckVO readDeck(Long dno);
}
