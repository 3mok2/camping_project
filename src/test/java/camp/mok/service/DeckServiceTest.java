package camp.mok.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import lombok.extern.log4j.Log4j;

@Log4j
public class DeckServiceTest extends AppTest{

	@Autowired
	DeckService deckService;
	
	@Test
	public void test() {
		deckService.getList().forEach(d->log.info(d));
	}

}
