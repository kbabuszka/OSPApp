package net.babuszka.osp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.babuszka.osp.repository.FirefighterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OspApplicationTests {

	@Autowired
	private FirefighterRepository firefighters;
	
	@Test
	public void testFindAll() {
		firefighters.findAll();
		firefighters.findAll();
	}
	
	@Test
	public void contextLoads() {
	}
	
	

}
