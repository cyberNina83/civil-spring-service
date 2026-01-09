package cyber.nina.citizen.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import cyber.nina.civil_registry.model.Citizen;
import cyber.nina.civil_registry.repository.CitizenRepositority;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class DBTest implements TestInterface{

	@Autowired
	CitizenRepositority repo;
	
	/* Checking book insertion positive cases */
	@ParameterizedTest
	@Order(5)
	@CsvFileSource(resources="/positiveSingleCitizens.csv")
	void checkPositiveSingleCitizenWithDB(ArgumentsAccessor accessor) {
		Citizen book = CitizenUtility.createPositiveCitizen(accessor,0);
		repo.save(book);
		book = repo.findById(accessor.getString(0)).orElse(null);
		assertNotNull(book);
		CitizenUtility.checkCitizen(book,accessor);
	}
	
	/* Checking book deletion positive cases */
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"X1234567, Name1, Lastname1, Male, 10-11-1987",
		"Y1234567, Name2, Lastname2, Female, 14-09-1989",
		"W1234567, Name3, Lastname3, M, 08-09-1985",
	})
	void checkBookDeletion(String AT, String firstname, String lastname, String gender, String birthDate) {
		
		Citizen citizen = new Citizen.Builder(AT,firstname,lastname,gender,birthDate).build();
		repo.save(citizen);
		repo.deleteById(AT);
		citizen = repo.findById(AT).orElse(null);
		assertNull(citizen);
	}
	
	//Checking citizen update on obligatory fields
	@ParameterizedTest
	@Order(4)
	@CsvSource({
		"X1234567, Name1, Lastname1, Male, 10-11-1987",
		"Y1234567, Name2, Lastname2, Female, 14-09-1989",
		"W1234567, Name3, Lastname3, M, 08-09-1985",
	})
	void checkCitizenUpdate(String AT, String firstname, String lastname, String gender, String birthDate) {
		
		Citizen citizen = new Citizen.Builder(AT,firstname,lastname,gender,birthDate).build();
		repo.save(citizen);
		Random r = new Random();
		int choice = r.nextInt(3);
		switch(choice) {
			case 0: citizen.setLastName("LASTNAME"); break;
			case 1: citizen.setFirstName("FIRSTNAME"); break;
			case 2: citizen.setGender("GENDER"); break;
			case 3: citizen.setBirthDate(LocalDate.parse("dd-mm-yyyy"));
		}
		repo.save(citizen);
		citizen = repo.findById(AT).orElse(null);
		assertNotNull(citizen);
		if (choice == 0) {
			assertEquals(citizen.getFirstName(),firstname);
			assertEquals(citizen.getLastName(),lastname);
			assertEquals(citizen.getGender(),gender);
			assertEquals(citizen.getBirthDate(),LocalDate.parse(birthDate));
		}
		else if (choice == 1) {
			assertEquals(citizen.getFirstName(),firstname);
			assertEquals(citizen.getLastName(),lastname);
			assertEquals(citizen.getGender(),gender);
			assertEquals(citizen.getBirthDate(),LocalDate.parse(birthDate));
		}
		else if (choice == 2) {
			assertEquals(citizen.getFirstName(),firstname);
			assertEquals(citizen.getLastName(),lastname);
			assertEquals(citizen.getGender(),gender);
			assertEquals(citizen.getBirthDate(),LocalDate.parse(birthDate));
		}
		else {
			assertEquals(citizen.getFirstName(),firstname);
			assertEquals(citizen.getLastName(),lastname);
			assertEquals(citizen.getGender(),gender);
			assertEquals(citizen.getBirthDate(),LocalDate.parse(birthDate));
		}
	}
	
	//Checking citizens retrieval (all citizens or some)
	@Test
	@Order(3)
	void checkBookRetrieval() {
		
		Citizen citizen1 = new Citizen.Builder("AT123456", "Giannis", "Apostolou", "M", "10-02-1980").build();
		Citizen citizen2 = new Citizen.Builder("AT123450", "Katerina", "Moustaka", "F", "29-09-1990").build();
		Citizen citizen3 = new Citizen.Builder("AT999999", "Katerina", "Anagnostopoulou", "F", "29-09-2000").build();
		repo.save(citizen1);
		repo.save(citizen2);
		repo.save(citizen3);
		List<Citizen> citizens = repo.findAll();
		
		
		//Checking if we have 3 Citizens and these are citizen1, citizen2 & citizen3
		assertEquals(citizens.size(),3);
		int matches = 0;
		for (Citizen citizen: citizens) {
			if (citizen.equals(citizen1) || citizen.equals(citizen2) || citizen.equals(citizen3)) {
				matches++;
			}
		}
		assertEquals(matches,3);
		
		//Checking that with 1 Lastname, we get only one citizen
		citizens = repo.findByLastName("Anagnostopoulou");
		assertEquals(citizens.size(),1);
		assertEquals(citizens.get(0),citizen2);
		citizens = repo.findByLastName("Apostolou");
		assertEquals(citizens.size(),1);
		assertEquals(citizens.get(0),citizen1);
		
		//Checking that with one firstname, we get two citizens
		citizens = repo.findByFirstName("Katerina");
		assertEquals(citizens.size(),2);
		matches = 0;
		for (Citizen citizen: citizens) {
			if (citizen.equals(citizen2) || citizen.equals(citizen3)) {
				matches++;
			}
		}
		assertEquals(matches,2);
	}
}
