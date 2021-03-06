package net.babuszka.osp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
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

import net.babuszka.osp.model.User;
import net.babuszka.osp.service.UserService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
public class UserProfileControllerTest {

	@Autowired
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitUserProfile() throws Exception {	
		mockMvc.perform(get("/profile"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("page_title"))
			.andExpect(model().attributeExists("userProfileForm"))
			.andExpect(view().name("user_profile"));
	}
		
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditUserProfileForm() throws Exception {
		User user = userService.getCurrentlyLoggedUser();
		String oldName = user.getDisplayName();
		String newName = new RandomString().make(8);
		
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", newName)
			.param("oldPassword", "")
			.param("newPassword", "")
			.param("confirmNewPassword", "")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/profile"))
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("alertClass", is("alert-success")));
		User userCheck = userService.findUserByUsername("junit");
		assertNotEquals(oldName, userCheck.getDisplayName());
	}	
	
	@Test
	@WithMockUser(username = "junit")
	public void testSubmitEditUserProfileFormWithErrors() throws Exception {
		User user = userService.getCurrentlyLoggedUser();
		String oldName = user.getDisplayName();
		String newName = "ab";
		
		// Too short disaplyName
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", newName)
			.param("oldPassword", "")
			.param("newPassword", "")
			.param("confirmNewPassword", "")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("user_profile"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("alertClass", is("alert-danger")));
		User userCheck = userService.findUserByUsername("junit");
		assertEquals(oldName, userCheck.getDisplayName());
		
		//No email address
		newName = new RandomString().make(8);
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "")
			.param("displayName", newName)
			.param("oldPassword", "")
			.param("newPassword", "")
			.param("confirmNewPassword", "")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("user_profile"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("alertClass", is("alert-danger")));
		userCheck = userService.findUserByUsername("junit");
		assertEquals(oldName, userCheck.getDisplayName());
		
		//Taken email address
		newName = new RandomString().make(8);
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "kamil@osp.slawa.pl")
			.param("displayName", newName)
			.param("oldPassword", "")
			.param("newPassword", "")
			.param("confirmNewPassword", "")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("user_profile"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("alertClass", is("alert-danger")));
		userCheck = userService.findUserByUsername("junit");
		assertEquals(oldName, userCheck.getDisplayName());
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testPasswordChange() throws Exception {
		User user = userService.getCurrentlyLoggedUser();
		String currentEncryptedPassword = user.getPassword();
		String currentPassword = "junitpass";
		String newPassword = new RandomString().make(8);
		
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", user.getDisplayName())
			.param("oldPassword", currentPassword)		
			.param("newPassword", newPassword)
			.param("confirmNewPassword", newPassword)
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/profile"))
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("alertClass", is("alert-success")));
		User userCheck = userService.findUserByUsername("junit");
		assertNotEquals(currentEncryptedPassword, userCheck.getPassword());
		user.setPassword(currentEncryptedPassword);
		userService.saveUser(user);
	}	
	
	@Test
	@WithMockUser(username = "junit")
	public void testPasswordChangeWithErrors() throws Exception {
		User user = userService.getCurrentlyLoggedUser();
		String currentEncryptedPassword = user.getPassword();
		String currentPassword = "junitpass";
		String wrongPassword = "wrongjunitpass";
		String newPassword = new RandomString().make(8);
		
		//Wrong current password
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", user.getDisplayName())
			.param("oldPassword", wrongPassword)		
			.param("newPassword", newPassword)
			.param("confirmNewPassword", newPassword)
			)
			.andExpect(status().isOk())
			.andExpect(view().name("user_profile"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("alertClass", is("alert-danger")));
		User userCheck = userService.findUserByUsername("junit");
		assertEquals(currentEncryptedPassword, userCheck.getPassword());

		//New passwords not match
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", user.getDisplayName())
			.param("oldPassword", currentPassword)		
			.param("newPassword", newPassword)
			.param("confirmNewPassword", wrongPassword)
			)
			.andExpect(status().isOk())
			.andExpect(view().name("user_profile"))
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("alertClass", is("alert-danger")));
		userCheck = userService.findUserByUsername("junit");
		assertEquals(currentEncryptedPassword, userCheck.getPassword());
		
		//New passwords empty
		mockMvc.perform(post("/profile")
			.param("id", "2")
			.param("username", "junit")
			.param("email", "junit@osp.babuszka.net")
			.param("displayName", user.getDisplayName())
			.param("oldPassword", currentPassword)		
			.param("newPassword", "")
			.param("confirmNewPassword", "")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/profile"))
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("alertClass", is("alert-success")));
		userCheck = userService.findUserByUsername("junit");
		assertEquals(currentEncryptedPassword, userCheck.getPassword());	
	}	
}
