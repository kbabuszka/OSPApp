package net.babuszka.osp;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OspApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(OspApplication.class, args);
	}

	@PostConstruct
	public void init(){
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
		return app.sources(OspApplication.class);
	}
	
}