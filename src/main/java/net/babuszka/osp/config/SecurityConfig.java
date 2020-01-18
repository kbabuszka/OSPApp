package net.babuszka.osp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String usersQuery = "SELECT username, password, status FROM users WHERE username=?";
	private String authoritiesQuery = "SELECT u.username, r.name FROM users u INNER JOIN user_roles ur ON(u.id=ur.user_id) INNER JOIN roles r ON(ur.role_id=r.id) WHERE u.username=?";
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.
			jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(authoritiesQuery)
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder);
			
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/manage/**").hasAuthority("Administrator")
				.antMatchers("/settings").hasAuthority("Administrator")
				.antMatchers("/css/**", "/js/**", "/images/**", "/plugins/**").permitAll()
				.antMatchers("/activate-account/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.exceptionHandling().accessDeniedPage("/error")
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/", true)
				.and()
			.httpBasic()
				.and()
			.logout()
				.permitAll()
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID")
				.and()
			.rememberMe().key("logowanie").tokenValiditySeconds(86400);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter() {
		return new SecurityContextHolderAwareRequestFilter();
	}

}
