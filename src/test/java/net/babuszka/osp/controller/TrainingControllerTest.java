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

import net.babuszka.osp.model.Training;
import net.babuszka.osp.service.TrainingService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
public class TrainingControllerTest {

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitTrainingsList() throws Exception {	
		mockMvc.perform(get("/manage/trainings"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("wrapper"))
			.andExpect(model().attributeExists("training"))
			.andExpect(view().name("manage_trainings"));
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddTrainingForm() throws Exception {
		int numberOfTrainings = trainingService.getAllTrainings().size();
		mockMvc.perform(post("/manage/trainings")
			.param("name", "Example training type created by JUnit")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/trainings"));
		assertEquals(numberOfTrainings+1, trainingService.getAllTrainings().size());
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddTrainingFormWithErrors() throws Exception {
		int numberOfTrainings = trainingService.getAllTrainings().size();
		mockMvc.perform(post("/manage/trainings")
			.param("name", "Ex")
		)
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("wrapper"))
		.andExpect(model().attributeExists("training"))
		.andExpect(view().name("manage_trainings"));
		assertEquals(numberOfTrainings, trainingService.getAllTrainings().size());
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditTrainingForm() throws Exception {
		ArrayList<Training> trainings = (ArrayList<Training>) trainingService.getAllTrainings();
		Training training = trainings.get(trainings.size()-1);
		Integer id = training.getId();
		String oldName = training.getName();
		String newName = new RandomString().make(8);
		
		mockMvc.perform(post("/manage/trainings/edit")
			.param("trainings[0].name", newName)
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/trainings"));				
		assertNotEquals(oldName, newName);
	}	
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditTrainingFormWithErrors() throws Exception {
		ArrayList<Training> trainings = (ArrayList<Training>) trainingService.getAllTrainings();
		Training training = trainings.get(trainings.size()-1);
		Integer id = training.getId();
		String oldName = training.getName();
		String newName = new RandomString().make(2);
		
		mockMvc.perform(post("/manage/trainings/edit")
			.param("trainings[0].name", newName)
		)
			.andExpect(status().isOk())
			.andExpect(view().name("manage_trainings"));		
		
		trainings = (ArrayList<Training>) trainingService.getAllTrainings();
		Training trainingCheck = trainings.get(trainings.size()-1);
		String nameCheckString = trainingCheck.getName();
		assertEquals(oldName,nameCheckString);
	}	
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteTraining() throws Exception {
		ArrayList<Training> trainings = (ArrayList<Training>) trainingService.getAllTrainings();
		int numberOfTrainings = trainings.size();
		Training training = trainings.get(trainings.size()-1);
		Integer id = training.getId();
		mockMvc.perform(get("/manage/trainings/delete/{id}", id))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/manage/trainings"))
			.andExpect(flash().attribute("alertClass", "alert-success"))
			.andExpect(flash().attributeExists("message"));
		assertEquals(numberOfTrainings-1, trainingService.getAllTrainings().size());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteTrainingThatNotExists() throws Exception {
		int numberOfTrainings = trainingService.getAllTrainings().size();
		mockMvc.perform(get("/manage/trainings/delete/0"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/manage/trainings"))
			.andExpect(flash().attribute("alertClass", "alert-danger"))
			.andExpect(flash().attributeExists("message"));
		assertEquals(numberOfTrainings, trainingService.getAllTrainings().size());
	}
}
