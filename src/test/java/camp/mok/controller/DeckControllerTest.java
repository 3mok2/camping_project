package camp.mok.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import camp.mok.AppTest;
import camp.mok.service.DeckService;
import lombok.extern.log4j.Log4j;

@Log4j
public class DeckControllerTest extends AppTest{

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Autowired
	private DeckService deckService;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	@Ignore
	public void testInsertDeck() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
					.post("/deck/insertDeck")
						.param("deckName", "오솔길데크 추가111"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		log.info(resultPage);
	}
	
	@Test
	@Ignore
	public void testUpdateDeckName() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
					.post("/deck/updateDeckName")
					.param("dno", "1")
					.param("deckName", "오솔길데크1 수정이름"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		log.info(resultPage);
	}
	
	@Test
	@Ignore
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
					.post("/deck/removeDeck")
					.param("dno", "6"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		log.info(resultPage);
	}

}
