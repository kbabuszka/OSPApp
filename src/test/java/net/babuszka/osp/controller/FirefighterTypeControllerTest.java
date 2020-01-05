package net.babuszka.osp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

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

import net.babuszka.osp.model.FirefighterType;
import net.babuszka.osp.service.FirefighterTypeService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
public class FirefighterTypeControllerTest {

		@Autowired
		private FirefighterTypeService firefighterTypeService;

		@Autowired
		private MockMvc mockMvc;
		
		@Test
		public void contextLoads() {
		}
		
		@Test
		@WithMockUser("junit")
		public void testInitFirefighterTypesList() throws Exception {	
			mockMvc.perform(get("/manage/firefighter-types"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("wrapper"))
				.andExpect(model().attributeExists("firefighter_type"))
				.andExpect(view().name("manage_firefighter_types"));
		}
		
		@Test
		@WithMockUser("junit")
		public void testSubmitAddFirefighterTypeForm() throws Exception {
			int numberOfFirefighterTypes = firefighterTypeService.getAllFirefighterTypes().size();
			mockMvc.perform(post("/manage/firefighter-types")
				.param("name", "Example firefighter type created by JUnit")
			)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/firefighter-types"));
			assertEquals(numberOfFirefighterTypes+1, firefighterTypeService.getAllFirefighterTypes().size());
		}
		
		@Test
		@WithMockUser("junit")
		public void testSubmitAddFirefighterTypeFormWithErrors() throws Exception {
			int numberOfFirefighterTypes = firefighterTypeService.getAllFirefighterTypes().size();
			mockMvc.perform(post("/manage/firefighter-types")
				.param("name", "Ex")
			)
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("wrapper"))
			.andExpect(model().attributeExists("firefighter_type"))
			.andExpect(view().name("manage_firefighter_types"));
			assertEquals(numberOfFirefighterTypes, firefighterTypeService.getAllFirefighterTypes().size());
		}
		
		@Test
		@WithMockUser(username = "junit")
		public void testSubmitEditFirefighterTypeForm() throws Exception {
			ArrayList<FirefighterType> types = (ArrayList<FirefighterType>) firefighterTypeService.getAllFirefighterTypes();
			FirefighterType type = types.get(types.size()-1);
			Integer id = type.getId();
			String oldName = type.getName();
			String newName = new RandomString().make(8);
			
			mockMvc.perform(post("/manage/firefighter-types/edit")
				.param("types[0].name", newName)
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/firefighter-types"));				
			assertNotEquals(oldName, newName);
		}	
		
		@Test
		@WithMockUser(username = "junit")
		public void testSubmitEditFirefighterTypeFormWithErrors() throws Exception {
			ArrayList<FirefighterType> types = (ArrayList<FirefighterType>) firefighterTypeService.getAllFirefighterTypes();
			FirefighterType type = types.get(types.size()-1);
			Integer id = type.getId();
			String oldName = type.getName();
			String newName = new RandomString().make(2);
			
			mockMvc.perform(post("/manage/firefighter-types/edit")
					.param("types[0].name", newName)
				)
				.andExpect(status().isOk())
				.andExpect(view().name("manage_firefighter_types"));		
			
			types = (ArrayList<FirefighterType>) firefighterTypeService.getAllFirefighterTypes();
			FirefighterType typeCheck = types.get(types.size()-1);
			String nameCheckString = typeCheck.getName();
			assertEquals(oldName,nameCheckString);
		}	
		
		@Test 
		@WithMockUser("junit")
		public void testSubmitDeleteFirefighterType() throws Exception {
			ArrayList<FirefighterType> types = (ArrayList<FirefighterType>) firefighterTypeService.getAllFirefighterTypes();
			int numberOfTypes = types.size();
			FirefighterType type = types.get(types.size()-1);
			Integer id = type.getId();
			mockMvc.perform(get("/manage/firefighter-types/delete/{id}", id))
		        .andExpect(status().is3xxRedirection())
		        .andExpect(view().name("redirect:/manage/firefighter-types"))
				.andExpect(flash().attribute("alertClass", "alert-success"))
				.andExpect(flash().attributeExists("message"));
			assertEquals(numberOfTypes-1, firefighterTypeService.getAllFirefighterTypes().size());
		}
		
		@Test 
		@WithMockUser("junit")
		public void testSubmitDeleteFirefighterTypeThatNotExists() throws Exception {
			int numberOfTypes = firefighterTypeService.getAllFirefighterTypes().size();
			mockMvc.perform(get("/manage/firefighter-types/delete/0"))
		        .andExpect(status().is3xxRedirection())
		        .andExpect(view().name("redirect:/manage/firefighter-types"))
				.andExpect(flash().attribute("alertClass", "alert-danger"))
				.andExpect(flash().attributeExists("message"));
			assertEquals(numberOfTypes, firefighterTypeService.getAllFirefighterTypes().size());
		}
}