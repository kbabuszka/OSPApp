package net.babuszka.osp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
public class MainControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser("junit")
	public void testInitMainPage() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attribute("page_title", is("Pulpit")));
	}
	
	@Test
	@WithMockUser("junit")
	public void testWrongUrl() throws Exception {
		mockMvc.perform(get("/notexistingpage"))
			.andExpect(status().is4xxClientError());
//		mockMvc.perform(get("//"))
//			.andExpect(status().is5xxServerError());
//		mockMvc.perform(get("/;"))
//			.andExpect(status().is5xxServerError());
	}
}
