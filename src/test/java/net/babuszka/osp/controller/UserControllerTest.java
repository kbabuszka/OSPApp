package net.babuszka.osp.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import net.babuszka.osp.model.User;
import net.babuszka.osp.model.UserStatus;
import net.babuszka.osp.model.UserVerificationToken;
import net.babuszka.osp.repository.UserRepository;
import net.babuszka.osp.repository.UserVerificationTokenRepository;
import net.babuszka.osp.service.FirefighterService;
import net.babuszka.osp.service.UserService;
import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=junit")
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "junit")
public class UserControllerTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FirefighterService firefighterService;
	
	@Autowired
	private UserVerificationTokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final Integer FIREFIGHTER_ID = 1000; //Always use 1000 as there is special firefighter with such ID added to test DB
	private static final Integer NOT_EXISTING_USER_ID = 0;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@WithMockUser("junit")
	public void testInitUsersList() throws Exception {	
		mockMvc.perform(get("/manage/users"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("users"))
			.andExpect(model().attributeExists("userForm"))
			.andExpect(model().attributeExists("firefighters"))
			.andExpect(model().attributeExists("roles"))
			.andExpect(view().name("manage_users_list"));
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddUserForm() throws Exception {	
		int numberOfUsers = userService.getAllUsers().size();
		String generated = new RandomString().make(10);
		mockMvc.perform(post("/manage/users/add")
				.param("username", generated)
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/users"))
				.andExpect(flash().attribute("alertClass", "alert-success"));	
		assertEquals(numberOfUsers+1, userService.getAllUsers().size());
	}
	
	
	@Test
	@WithMockUser("junit")
	public void testSubmitAddUserFormIncludingFirefighter() throws Exception {	
		int numberOfUsers = userService.getAllUsers().size();
		int numberOfAvailableFirefighters = firefighterService.getFirefightersWithNoAccount().size();
		String generated = new RandomString().make(10);
		mockMvc.perform(post("/manage/users/add")
				.param("username", generated)
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				.param("firefighter", "1000")
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/users"))
				.andExpect(flash().attribute("alertClass", "alert-success"));	
		assertEquals(numberOfUsers+1, userService.getAllUsers().size());
		assertEquals(numberOfAvailableFirefighters-1, firefighterService.getFirefightersWithNoAccount().size());
		
		User user = userService.findUserByFirefighterId(FIREFIGHTER_ID);
		Integer userId = user.getId();
		assertEquals(user.getUsername(), generated);
		assertEquals(user.getDisplayName(), generated);
		assertEquals(user.getEmail(), generated+"@babuszka.net");
		assertEquals(user.getFirefighter().getId(), FIREFIGHTER_ID);
		assertEquals(user.getStatus(), UserStatus.INACTIVE);
		
		//Unassign firefighter from user
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("id", userId.toString())
				.param("username", generated)
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/users"))
				.andExpect(flash().attribute("alertClass", "alert-success"));

		assertEquals(numberOfAvailableFirefighters, firefighterService.getFirefightersWithNoAccount().size());
		user = userService.findUserByFirefighterId(FIREFIGHTER_ID);
		assertEquals(user, null);
	}
	
	@Test
	@WithMockUser(username = "junit")
	public void testInitUserEditForm() throws Exception {
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		User user = (User) users.get(users.size()-1);
		Integer userId = user.getId();		
		mockMvc.perform(get("/manage/users/edit/{id}", userId))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(model().attribute("userForm", hasProperty("username", is(user.getUsername()))))
			.andExpect(model().attribute("userForm", hasProperty("displayName", is(user.getDisplayName()))))
			.andExpect(model().attribute("userForm", hasProperty("email", is(user.getEmail()))))
			.andExpect(model().attribute("userForm", hasProperty("roleCheckboxes")))
			.andExpect(view().name("manage_user_edit"));		
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitEditUserForm() throws Exception {
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		User user = (User) users.get(users.size()-1);
		Integer userId = user.getId();
		String generated = new RandomString().make(10);
		
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", generated)
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/manage/users"))
				.andExpect(flash().attribute("alertClass", "alert-success"));
		assertNotEquals(user.getUsername(), generated);
		assertNotEquals(user.getDisplayName(), generated);
		assertNotEquals(user.getEmail(), generated+"@babuszka.net");
		assertNotEquals(user.getStatus(), UserStatus.ACTIVE);
	}
	
	@Test
	@WithMockUser("junit")
	public void testSubmitEditUserFormWithErrors() throws Exception {
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		User user = (User) users.get(users.size()-1);
		Integer userId = user.getId();
		String generated = new RandomString().make(10);
		
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", "")
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("manage_user_edit"));
				
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", generated)
				.param("displayName", "")
				.param("email", generated + "@babuszka.net")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("manage_user_edit"));
		
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", generated)
				.param("displayName", generated)
				.param("email", "")
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("manage_user_edit"));
		
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", generated)
				.param("displayName", generated)
				.param("email", generated + "@babuszka.net")
				.param("password", "asdf")
				.param("confirmPassword", "asdfg")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("manage_user_edit"));
		
		User otherUser =  (User) users.get(users.size()-2);
		String duplicatedEmailAddress = otherUser.getEmail();
		
		mockMvc.perform(post("/manage/users/edit/{id}", userId)
				.param("username", generated)
				.param("displayName", generated)
				.param("email", duplicatedEmailAddress)
				.param("password", generated)
				.param("confirmPassword", generated)
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("manage_user_edit"));

	}
	
	@Test 
	@WithMockUser("junit")
	public void testUserEditThatNotExist() throws Exception {
		mockMvc.perform(get("/manage/users/edit/{id}", NOT_EXISTING_USER_ID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(flash().attribute("alertClass", "alert-danger"))
	        .andExpect(view().name("redirect:/manage/users"));
		
		mockMvc.perform(get("/manage/users/edit/{id}", -1))
			.andExpect(status().is4xxClientError());
		
		mockMvc.perform(get("/manage/users/edit/{id}", "a"))
	        .andExpect(status().is4xxClientError());
	}
	
	@Test
	@WithMockUser("junit")
	public void testActivateAndDeactivateUser() throws Exception {
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		User user = (User) users.get(users.size()-1);
		Integer userId = user.getId();
		UserVerificationToken token = tokenRepository.findTokenByUserId(userId);
		
		//Activate account with expired token
		LocalDateTime expirationDate = LocalDateTime.now().minusHours(48);
		token.setExpirationDate(expirationDate);
		tokenRepository.save(token);
		mockMvc.perform(get("/activate-account/{token}", token.getToken()))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("activate_account"));
		users = (ArrayList<User>) userService.getAllUsers();
		user = (User) users.get(users.size()-1);
		assertEquals(user.getStatus(), UserStatus.INACTIVE);
		
		// Activate account
		expirationDate = LocalDateTime.now().plusHours(48);
		token.setExpirationDate(expirationDate);
		tokenRepository.save(token);
		mockMvc.perform(get("/activate-account/{token}", token.getToken()))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("activate_account"));	
		users = (ArrayList<User>) userService.getAllUsers();
		user = (User) users.get(users.size()-1);
		token = tokenRepository.findTokenByUserId(userId);
		assertEquals(user.getStatus(), UserStatus.ACTIVE);
		assertEquals(token, null);
		
		// Resend activation link for active user by admin
		mockMvc.perform(get("/manage/users/resend-link/{id}", userId))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/users"))
			.andExpect(flash().attribute("alertClass", "alert-danger"));
		token = tokenRepository.findTokenByUserId(userId);
		assertEquals(user.getStatus(), UserStatus.ACTIVE);
		assertEquals(token, null);
		
		//Deactivate account by admin
		mockMvc.perform(get("/manage/users/deactivate/{id}", userId))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/users"))
			.andExpect(flash().attribute("alertClass", "alert-success"));
		users = (ArrayList<User>) userService.getAllUsers();
		user = (User) users.get(users.size()-1);
		assertEquals(user.getStatus(), UserStatus.INACTIVE);
		
		//Try to deactivate deactivated account by admin
		mockMvc.perform(get("/manage/users/deactivate/{id}", userId))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/users"))
			.andExpect(flash().attribute("alertClass", "alert-danger"));
		users = (ArrayList<User>) userService.getAllUsers();
		user = (User) users.get(users.size()-1);
		assertEquals(user.getStatus(), UserStatus.INACTIVE);
		
		// Resend activation link by admin
		mockMvc.perform(get("/manage/users/resend-link/{id}", userId))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/manage/users"))
			.andExpect(flash().attribute("alertClass", "alert-success"));
		token = tokenRepository.findTokenByUserId(userId);
		assertEquals(user.getStatus(), UserStatus.INACTIVE);
		assertNotEquals(token, null);
		
		
	}
	
	@Test 
	@WithMockUser("junit")
	public void testResendLinkForUserThatNotExist() throws Exception {
		mockMvc.perform(get("/manage/users/resend-link/{id}", NOT_EXISTING_USER_ID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(flash().attribute("alertClass", "alert-danger"))
	        .andExpect(view().name("redirect:/manage/users"));
		
		mockMvc.perform(get("/manage/users/resend-link/{id}", -1))
			.andExpect(status().is4xxClientError());
		
		mockMvc.perform(get("/manage/users/resend-link/{id}", "a"))
	        .andExpect(status().is4xxClientError());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testDeactivateUserThatNotExist() throws Exception {
		mockMvc.perform(get("/manage/users/deactivate/{id}", NOT_EXISTING_USER_ID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(flash().attribute("alertClass", "alert-danger"))
	        .andExpect(view().name("redirect:/manage/users"));
		
		mockMvc.perform(get("/manage/users/deactivate/{id}", -1))
			.andExpect(status().is4xxClientError());
		
		mockMvc.perform(get("/manage/users/deactivate/{id}", "a"))
	        .andExpect(status().is4xxClientError());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteUser() throws Exception {
		int numberOfUsers = userService.getAllUsers().size();
		ArrayList<User> users = (ArrayList<User>) userService.getAllUsers();
		User user = (User) users.get(users.size()-1);
		Integer userId = user.getId();
		mockMvc.perform(get("/manage/users/delete/{id}", userId))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/manage/users"))
			.andExpect(flash().attribute("alertClass", "alert-success"));
		users = (ArrayList<User>) userService.getAllUsers();
		assertEquals(numberOfUsers-1, users.size());
	}
	
	@Test 
	@WithMockUser("junit")
	public void testSubmitDeleteUserThatNotExists() throws Exception {
		int numberOfUsers = userService.getAllUsers().size();
		
		mockMvc.perform(get("/manage/users/delete/{id}", NOT_EXISTING_USER_ID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/manage/users"))
	        .andExpect(flash().attribute("alertClass", "alert-danger"));
		
		mockMvc.perform(get("/manage/users/delete/{id}", -1))
			.andExpect(status().is4xxClientError());
	
		mockMvc.perform(get("/manage/users/delete/{id}", "a"))
	        .andExpect(status().is4xxClientError());
		
		assertEquals(numberOfUsers, userService.getAllUsers().size());
	}
}
