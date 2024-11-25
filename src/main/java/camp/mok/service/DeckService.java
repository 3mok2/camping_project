package camp.mok.service;

import java.util.List;

import org.springframework.stereotype.Service;

import camp.mok.domain.DeckVO;

public interface DeckService {

	void insertDeck(DeckVO vo);
	
	List<DeckVO> getList();
	
	boolean modify(DeckVO vo);
	
	boolean remove(Long dno);
	
	DeckVO get(Long dno);
}
