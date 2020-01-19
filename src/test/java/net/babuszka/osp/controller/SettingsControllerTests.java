package net.babuszka.osp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

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

import net.babuszka.osp.model.Setting;
import net.babuszka.osp.service.SettingsService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=junit")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
public class SettingsControllerTests {

	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitSettingsForm() throws Exception {	
		mockMvc.perform(get("/settings"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("page_title"))
			.andExpect(model().attributeExists("settings"))
			.andExpect(view().name("settings"));
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitSettingsForm() throws Exception {
		List<Setting> settings = settingsService.getAllSettings();	
		Integer port = Integer.parseInt(settings.get(8).getValue());
		port++;
		String portString = port.toString();
		mockMvc.perform(post("/settings")
			.param("settings[0].id", "1")
			.param("settings[0].name", "DEPARTMENT_FULL_NAME")
			.param("settings[0].value", "Ochotnicza Straż Pożarna w " + new RandomString().make(5))
			
			.param("settings[1].id", "2")
			.param("settings[1].name", "DEPARTMENT_NAME")
			.param("settings[1].value", new RandomString().make(5))
			
			.param("settings[2].id", "3")
			.param("settings[2].name", "DEPARTMENT_NAME_PREFIX")
			.param("settings[2].value", new RandomString().make(3))
			
			.param("settings[3].id", "4")
			.param("settings[3].name", "DEPARTMENT_ADDRESS_STREET")
			.param("settings[3].value","ul. " + new RandomString().make(10) + "10/2")
			
			.param("settings[4].id", "5")
			.param("settings[4].name", "DEPARTMENT_ADDRESS_CITY")
			.param("settings[4].value", new RandomString().make(7))
			
			.param("settings[5].id", "6")
			.param("settings[5].name", "DEPARTMENT_ADDRESS_POSTAL_CODE")
			.param("settings[5].value", new RandomString().make(6))
			
			.param("settings[6].id", "7")
			.param("settings[6].name", "DEPARTMENT_APP_URL")
			.param("settings[6].value", new RandomString().make(6))
					
			.param("settings[7].id", "8")
			.param("settings[7].name", "MAIL_HOST")
			.param("settings[7].value", new RandomString().make(6))
			
			.param("settings[8].id", "9")
			.param("settings[8].name", "MAIL_PORT")
			.param("settings[8].value", portString)
			
			.param("settings[9].id", "10")
			.param("settings[9].name", "MAIL_USER")
			.param("settings[9].value", new RandomString().make(6))
			
			.param("settings[10].id", "11")
			.param("settings[10].name", "MAIL_PASSWORD")
			.param("settings[10].value", new RandomString().make(6))
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attribute("alertClass", "alert-success"))
			.andExpect(flash().attributeExists("message"))
			.andExpect(view().name("redirect:/settings"));
		
		List<Setting> newSettings = settingsService.getAllSettings();
		for(Setting setting : settings) {
			Integer id = setting.getId();
			assertNotEquals(setting.getValue(), newSettings.get(id-1).getValue());
		}
	}	
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitSettingsFormWithErrors() throws Exception {
		List<Setting> settings = settingsService.getAllSettings();	
		mockMvc.perform(post("/settings")
			.param("settings[0].id", "1")
			.param("settings[0].name", "DEPARTMENT_FULL_NAME")
			.param("settings[0].value", "Ochotnicza Straż Pożarna w " + new RandomString().make(5))
			
			.param("settings[1].id", "2")
			.param("settings[1].name", "DEPARTMENT_NAME")
			.param("settings[1].value", new RandomString().make(5))
			
			.param("settings[2].id", "3")
			.param("settings[2].name", "DEPARTMENT_NAME_PREFIX")
			.param("settings[2].value", new RandomString().make(3))
			
			.param("settings[3].id", "4")
			.param("settings[3].name", "DEPARTMENT_ADDRESS_STREET")
			.param("settings[3].value","ul. " + new RandomString().make(10) + "10/2")
			
			.param("settings[4].id", "5")
			.param("settings[4].name", "DEPARTMENT_ADDRESS_CITY")
			.param("settings[4].value", new RandomString().make(7))
			
			.param("settings[5].id", "6")
			.param("settings[5].name", "DEPARTMENT_ADDRESS_POSTAL_CODE")
			.param("settings[5].value", new RandomString().make(6))
			
			.param("settings[6].id", "7")
			.param("settings[6].name", "DEPARTMENT_APP_URL")
			.param("settings[6].value", new RandomString().make(6))
					
			.param("settings[7].id", "8")
			.param("settings[7].name", "MAIL_HOST")
			.param("settings[7].value", "")
			
			.param("settings[8].id", "9")
			.param("settings[8].name", "MAIL_PORT")
			.param("settings[8].value", new RandomString().make(6))
			
			.param("settings[9].id", "10")
			.param("settings[9].name", "MAIL_USER")
			.param("settings[9].value", new RandomString().make(6))
			
			.param("settings[10].id", "11")
			.param("settings[10].name", "MAIL_PASSWORD")
			.param("settings[10].value", new RandomString().make(6))
		)
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attributeExists("alertClass"))
			.andExpect(view().name("settings"));
		
		List<Setting> newSettings = settingsService.getAllSettings();
		for(Setting setting : settings) {
			Integer id = setting.getId();
			assertEquals(setting.getValue(), newSettings.get(id-1).getValue());
		}
	}	
}
