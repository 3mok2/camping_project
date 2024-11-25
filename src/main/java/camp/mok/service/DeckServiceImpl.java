package camp.mok.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camp.mok.domain.DeckVO;
import camp.mok.repository.DeckRepository;

@Service
public class DeckServiceImpl implements DeckService {

	@Autowired
	private DeckRepository deckRepository;
	
	@Override
	public void insertDeck(DeckVO vo) {
		deckRepository.insert(vo);
	}

	@Override
	public List<DeckVO> getList() {
		return deckRepository.getList();
	}

	@Override
	public boolean modify(DeckVO vo) {
		return deckRepository.update(vo)==1;
	}

	@Override
	public boolean remove(Long dno) {
		return deckRepository.delete(dno)==1;
	}

	@Override
	public DeckVO get(Long dno) {
		return deckRepository.readDeck(dno);
	}

}
