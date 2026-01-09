package cyber.nina.citizen.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import cyber.nina.civil_registry.model.Citizen;
	
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

	
	
public class CitizenValidTest implements TestInterface{
		private static Validator validator = null;
		
		@BeforeAll
		static void constructValidator() {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
		}
		
		private static List<String> getMessages(Set<ConstraintViolation<Citizen>> viols){
			List<String> messages = new ArrayList<String>();
			for (ConstraintViolation<Citizen> viol: viols) {
				messages.add(viol.getMessage());
			}
			return messages;
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "1223", "ATATAT", "CCCTTAU", "4%**&^^" })
		void checkInvalidISBN(String at) {
			Citizen citizen = new Citizen();
			citizen.setAT(at);
			assertEquals(at,citizen.getAT());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("AT is invalid!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "12345678", "87654321", "18364759"})
		void checkValidISBN(String at) {
			Citizen citizen = new Citizen();
			citizen.setAT(at);
			
			assertEquals(at,citizen.getAT());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertFalse(messages.contains("AT is invalid!"));
		}
		
		

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
		void checkInvalidLastName(String lastName) {
			Citizen citizen = new Citizen();
			citizen.setLastName(lastName);
			
			assertEquals(lastName,citizen.getLastName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("Lastname cannot be blank!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "L", "Lastname1", "Lastname2", "Lastname A" })
		void checkValidLastName(String lastName) {
			Citizen citizen = new Citizen();

			citizen.setLastName(lastName);
			
			assertEquals(lastName,citizen.getLastName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertFalse(messages.contains("Lastname cannot be blank!"));
		}
		
		
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
		void checkInvalidFirstName(String firstName) {
			Citizen citizen = new Citizen();
			citizen.setLastName(firstName);
			
			assertEquals(firstName,citizen.getLastName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("firstName cannot be blank!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "L", "Firstname1", "Firstname2", "Firstname A" })
		void checkValidFirstName(String firstName) {
			Citizen citizen = new Citizen();

			citizen.setFirstName(firstName);
			
			assertEquals(firstName,citizen.getFirstName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertFalse(messages.contains("Firstname cannot be blank!"));
		}
		
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
		void checkInvalidGender(String gender) {
			Citizen citizen = new Citizen();
			citizen.setLastName(gender);
			
			assertEquals(gender,citizen.getLastName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("gender cannot be blank!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "M", "Male", "Female", "F" })
		void checkValidGender(String gender) {
			Citizen citizen = new Citizen();

			citizen.setFirstName(gender);
			
			assertEquals(gender,citizen.getFirstName());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertFalse(messages.contains("gender cannot be blank!"));
		}
		
		
		@ParameterizedTest
		@ValueSource(strings = { "12-1-56", "1-2-00", "10/02/88", "1/3/1987" })
		void checkInvalidBirthDate(String dateBirth) {
			Citizen citizen = new Citizen();
			citizen.setBirthDate(LocalDate.parse(dateBirth));
			assertEquals(dateBirth,citizen.getBirthDate());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("bithDate is invalid!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "23-01-2021", "01-04-2019", "17-12-2023"})
		void checkValidBirthDate(String birthDate) {
			Citizen citizen = new Citizen();
			citizen.setBirthDate(LocalDate.parse(birthDate));
			assertEquals(birthDate,citizen.getBirthDate());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertFalse(messages.contains("birthDate is invalid!"));
		}
		
		
		@ParameterizedTest
		@ValueSource(strings = { "   ", "0  0", "taxid12", "99999" })
		void checkInvalidTaxId(String taxId) {
			Citizen citizen = new Citizen();
			citizen.setTaxId(taxId);
			assertEquals(taxId,citizen.getTaxId());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("taxId is invalid!"));
		}
		
		@ParameterizedTest
		@ValueSource(strings = { "123456789", "987654321", "198273645"})
		void checkValidTaxId(String taxId) {
			Citizen citizen = new Citizen();
			citizen.setTaxId(taxId);
			assertEquals(taxId,citizen.getTaxId());
			
			Set<ConstraintViolation<Citizen>> viols = validator.validate(citizen);
			assertNotEquals(viols.size(), 0);
			
			List<String> messages = getMessages(viols);
			assertTrue(messages.contains("taxId is invalid!"));
		}
		
		
	}



