package camp.mok.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import camp.mok.AppTest;
import lombok.extern.log4j.Log4j;

@Log4j
public class BoardControllerTest extends AppTest{

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	@Ignore
	public void testList() throws Exception{
		ModelMap modelMap = mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap();
		log.info(modelMap);
	}
	
	@Test
	@Ignore
	public void testRegister() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("category", "1")
				.param("title", "테스트 새글 제목")
				.param("content", "내용 테스트")
				.param("writer", "user목"))
			.andReturn()
			.getModelAndView()
			.getViewName();
		log.info(resultPage);
	}
	
	@Test
	@Ignore
	public void testGet() throws Exception {
		ModelMap modelMap = mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "3"))
				.andReturn()
				.getModelAndView()
				.getModelMap();
		log.info(modelMap);
	}
	
	@Test
	@Ignore
	public void testModify() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("category", "1")
				.param("title", "수정합니다")
				.param("content", "수정할게요")
				.param("bno", "1"))
			.andReturn()
			.getModelAndView()
			.getViewName();
		log.info(resultPage);
	}

	@Test
	public void testRemove() throws Exception {
		ModelMap modelMap = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "9"))
				.andReturn()
				.getModelAndView()
				.getModelMap();
		log.info(modelMap);
	}
	
}
