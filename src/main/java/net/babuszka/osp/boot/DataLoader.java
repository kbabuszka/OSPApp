package net.babuszka.osp.boot;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import net.babuszka.osp.repository.FirefighterRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private FirefighterRepository firefighterRepository;
	
	@Autowired
	public void firefighterRepository(FirefighterRepository firefighterRepository) {
		this.firefighterRepository = firefighterRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*
		Firefighter firefighter1 = new Firefighter();
		firefighter1.setId(16);
		firefighter1.setFirstName("Kamil");
		firefighter1.setLastName("Babuszka");
		firefighter1.setIsInJOT(false);
		firefighterRepository.save(firefighter1);
		
		
		LocalDate birthDate = LocalDate.now();
		Firefighter firefighter2 = new Firefighter();
		firefighter2.setFirstName("Marcin");
		firefighter2.setLastName("Kajda");
		firefighter2.setBirthDate(birthDate);
		firefighterRepository.save(firefighter2);
		
		/*
		Firefighter firefighter3 = new Firefighter();
		firefighter3.setFirstName("Łukasz");
		firefighter3.setLastName("Dudziak");
		firefighterRepository.save(firefighter3);
		*/
	}
}
