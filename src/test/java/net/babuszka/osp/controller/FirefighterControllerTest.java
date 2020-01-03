package net.babuszka.osp.controller;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.Assert;
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

import net.babuszka.osp.model.DeletedFirefighter;
import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.FirefighterTraining;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.FirefighterTrainingService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
public class FirefighterControllerTest {

	@Autowired
	private FirefighterService firefighterService;
	@Autowired
	private FirefighterTrainingService firefighterTrainingService;

	@Autowired
	private MockMvc mockMvc;
		
	private static final int FIREFIGHTER_ID = 1000; //Always use 1000 as there is special firefighter with such ID added to test DB
	private static final int NOT_EXISTING_FIREFIGHTER_ID = 0;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitFirefightersList() throws Exception {	
		mockMvc.perform(get("/firefighters"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighters"))
			.andExpect(view().name("firefighters_list"));
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitFirefighterDetailsView() throws Exception {		
		mockMvc.perform(get("/firefighters/{id}", FIREFIGHTER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attribute("firefighter", hasProperty("id", is(1000))))
			.andExpect(model().attribute("firefighter", hasProperty("firstName", is("User"))))
			.andExpect(model().attribute("firefighter", hasProperty("secondName", is("User-2nd"))))
			.andExpect(model().attribute("firefighter", hasProperty("lastName", is("Testowy"))))
			.andExpect(model().attribute("firefighter", hasProperty("gender", is("M"))))
			.andExpect(model().attribute("firefighter", hasProperty("birthDate")))
			.andExpect(model().attribute("firefighter", hasProperty("birthPlace", is("Server"))))
			.andExpect(model().attribute("firefighter", hasProperty("pesel", is("00000000000"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressStreet", is("Komputerowa"))))
			.andExpect(model().attribute("firefighter", hasProperty("address_1", is("10"))))
			.andExpect(model().attribute("firefighter", hasProperty("address_2", is("1"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressCity", is("Serwerownia"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressPostalCode", is("00-000"))))
			.andExpect(model().attribute("firefighter", hasProperty("joinDate")))
			.andExpect(model().attribute("firefighter", hasProperty("email", is("junit@osp.babuszka.net"))))
			.andExpect(model().attribute("firefighter", hasProperty("phone", is("123456789"))))
			.andExpect(model().attribute("firefighter", hasProperty("isInJOT", is(false))))
			.andExpect(model().attribute("firefighter", hasProperty("healthExamDate")))
			.andExpect(model().attribute("firefighter", hasProperty("type")))
			.andExpect(model().attributeExists("firefighter_types"))
			.andExpect(model().attributeExists("trainings"))
			.andExpect(view().name("firefighter_details"));
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitAddFirefighterForm() throws Exception {
		mockMvc.perform(get("/firefighters/add"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attributeExists("firefighter_types"))
			.andExpect(view().name("firefighter_add"));
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddFirefighterForm() throws Exception {
		int numberOfFirefighters = firefighterService.getAllFirefighters().size();
		mockMvc.perform(post("/firefighters/add")
			.param("firstName", "Test")
			.param("lastName", "Tes")
			.param("gender", "K")
			.param("type", "1")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters"));	
		assertEquals(numberOfFirefighters+1, firefighterService.getAllFirefighters().size());
		
		mockMvc.perform(post("/firefighters/add")
				.param("firstName", "Test")
				.param("lastName", "Test")
				.param("gender", "K")
			)
				.andExpect(status().isOk())
				.andExpect(view().name("firefighter_add"));
		assertEquals(numberOfFirefighters+1, firefighterService.getAllFirefighters().size());
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddFirefighterFormWithErrors() throws Exception {
		int numberOfFirefighters = firefighterService.getAllFirefighters().size();
		
		//not all required fields provided
		mockMvc.perform(post("/firefighters/add")
			.param("firstName", "Test")
		)
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("firefighter_add"));
		
		//no firefighter type
		mockMvc.perform(post("/firefighters/add")
				.param("firstName", "Test")
				.param("lastName", "Test")
				.param("gender", "K")
			)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("firefighter_add"));
		
		//too short firstName 
		mockMvc.perform(post("/firefighters/add")
				.param("firstName", "Te")
				.param("lastName", "Test")
				.param("gender", "K")
				.param("type", "1")
			)
				.andExpect(status().isOk())
				.andExpect(model().hasErrors())
				.andExpect(view().name("firefighter_add"));
		
		assertEquals(numberOfFirefighters, firefighterService.getAllFirefighters().size());
		
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testInitFirefighterEditForm() throws Exception {
				
		mockMvc.perform(get("/firefighters/edit/{id}", FIREFIGHTER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attribute("firefighter", hasProperty("id", is(1000))))
			.andExpect(model().attribute("firefighter", hasProperty("firstName", is("User"))))
			.andExpect(model().attribute("firefighter", hasProperty("secondName", is("User-2nd"))))
			.andExpect(model().attribute("firefighter", hasProperty("lastName", is("Testowy"))))
			.andExpect(model().attribute("firefighter", hasProperty("gender", is("M"))))
			.andExpect(model().attribute("firefighter", hasProperty("birthDate")))
			.andExpect(model().attribute("firefighter", hasProperty("birthPlace", is("Server"))))
			.andExpect(model().attribute("firefighter", hasProperty("pesel", is("00000000000"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressStreet", is("Komputerowa"))))
			.andExpect(model().attribute("firefighter", hasProperty("address_1", is("10"))))
			.andExpect(model().attribute("firefighter", hasProperty("address_2", is("1"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressCity", is("Serwerownia"))))
			.andExpect(model().attribute("firefighter", hasProperty("addressPostalCode", is("00-000"))))
			.andExpect(model().attribute("firefighter", hasProperty("joinDate")))
			.andExpect(model().attribute("firefighter", hasProperty("email", is("junit@osp.babuszka.net"))))
			.andExpect(model().attribute("firefighter", hasProperty("phone", is("123456789"))))
			.andExpect(model().attribute("firefighter", hasProperty("isInJOT", is(false))))
			.andExpect(model().attribute("firefighter", hasProperty("healthExamDate")))
			.andExpect(model().attribute("firefighter", hasProperty("type")))
			.andExpect(model().attributeExists("firefighter_types"))
			.andExpect(model().attributeExists("training_types"))
			.andExpect(view().name("firefighter_edit"));
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditFirefighterForm() throws Exception {
		String newName = new RandomString().make(8);
		mockMvc.perform(post("/firefighters/edit/1001")
			.param("firstName", newName)
			.param("lastName", "Test")
			.param("gender", "K")
			.param("type", "1")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters/edit/1001"));		
	
		mockMvc.perform(get("/firefighters/1001"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attribute("firefighter", hasProperty("id", is(1001))))
			.andExpect(model().attribute("firefighter", hasProperty("firstName", is(newName)))
		);
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitAddFirefighterTraining() throws Exception {
		int numberOfTrainings = firefighterTrainingService.getAllFirefighterTrainings(1001).size();
		mockMvc.perform(post("/firefighters/edit/1001")
			.param("firstName", "User")
			.param("lastName", "User-2nd")
			.param("gender", "M")
			.param("type", "1")
			.param("trainings[0].firefighterId", "1001")
			.param("trainings[0].training", "1")
			.param("trainings[0].certificateNumber", "example number")
			.param("trainings[0].issuedBy", "example issuer")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters/edit/1001"));		
	
		mockMvc.perform(get("/firefighters/1001"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attribute("firefighter", hasProperty("id", is(1001))))
			.andExpect(model().attribute("trainings", hasProperty("firefighterTrainings"))
		);
		
		assertEquals(numberOfTrainings+1, firefighterTrainingService.getAllFirefighterTrainings(1001).size());
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitDeleteFirefighterTraining() throws Exception {
		
		ArrayList<FirefighterTraining> trainings = (ArrayList<FirefighterTraining>) firefighterTrainingService.getAllFirefighterTrainings(1001);
		int numberOfTrainings = trainings.size();
		FirefighterTraining training = trainings.get(trainings.size()-1);
		Integer id = training.getId();
		
		
		//Should not be deleted - different firefighter ID
		mockMvc.perform(get("/firefighters/edit/1000/deletetraining/" + id))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters/edit/1000"))
			.andExpect(flash().attribute("alertClass", "alert-danger")
		);	
		assertEquals(numberOfTrainings, firefighterTrainingService.getAllFirefighterTrainings(1001).size());
		
		//Should be deleted
		mockMvc.perform(get("/firefighters/edit/1001/deletetraining/" + id))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters/edit/1001"))
			.andExpect(flash().attribute("alertClass", "alert-success")
		);	
		assertEquals(numberOfTrainings-1, firefighterTrainingService.getAllFirefighterTrainings(1001).size());
		
		//Should not be deleted - not existing training
		mockMvc.perform(get("/firefighters/edit/1001/deletetraining/0"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/firefighters/edit/1001"))
			.andExpect(flash().attribute("alertClass", "alert-danger")
		);
		assertEquals(numberOfTrainings-1, firefighterTrainingService.getAllFirefighterTrainings(1001).size());
		
	}	
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditFirefighterFormWithErrors() throws Exception {
		String newName = new RandomString().make(8);
		mockMvc.perform(post("/firefighters/edit/1001")
			.param("firstName", newName)
			.param("lastName", "")
		)
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("firefighter_edit"));		
	
		mockMvc.perform(get("/firefighters/1001"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighter"))
			.andExpect(model().attribute("firefighter", hasProperty("id", is(1001)))
		);
	}
	
	@Test 
	@WithMockUser("junit")
	public void testFirefighterEditFormRedirection() throws Exception {
		mockMvc.perform(get("/firefighters/edit/{id}", NOT_EXISTING_FIREFIGHTER_ID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/firefighters"));
		
		mockMvc.perform(get("/firefighters/edit/{id}", -1))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/firefighters"));
		
		mockMvc.perform(get("/firefighters/edit/{id}", "a"))
	        .andExpect(status().is4xxClientError());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteFirefighter() throws Exception {
		int numberOfFirefighters = firefighterService.getAllFirefighters().size();
		int numberOfDeletedFirefighters = firefighterService.getAllDeletedFirefighters().size();
		ArrayList<Firefighter> firefighters = (ArrayList<Firefighter>) firefighterService.getAllFirefighters();
		Firefighter firefighter = (Firefighter) firefighters.get(firefighters.size()-1);
		Integer id = firefighter.getId();
		mockMvc.perform(get("/firefighters/delete/{id}", id))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/firefighters"))
			.andExpect(flash().attribute("alertClass", "alert-success"));
		assertEquals(numberOfFirefighters-1, firefighterService.getAllFirefighters().size());
		assertEquals(numberOfDeletedFirefighters+1, firefighterService.getAllDeletedFirefighters().size());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteFirefighterThatNotExists() throws Exception {
		int numberOfFirefighters = firefighterService.getAllFirefighters().size();
		mockMvc.perform(get("/firefighters/delete/0"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/firefighters"))
	        .andExpect(flash().attribute("alertClass", "alert-danger"));
		assertEquals(numberOfFirefighters, firefighterService.getAllFirefighters().size());
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitDeletedFirefightersList() throws Exception {	
		mockMvc.perform(get("/firefighters/deleted"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("firefighters"))
			.andExpect(view().name("firefighters_deleted"));
	}
}
