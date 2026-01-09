package cyber.nina.citizen.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import cyber.nina.civil_registry.model.Citizen;


public class CitizenTest implements TestInterface {
	
	//Checking multiple positive cases for citizen creation through its Builder interface
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleCitizen.csv")
	void checkPositiveSingleBook(ArgumentsAccessor accessor) {
		Citizen citizen = CitizenUtility.createPositiveCitizen(accessor,0);
		CitizenUtility.checkCitizen(citizen,accessor);
	}
	
	//Checking multiple negative cases where obligatory citizen fields have null or empty or white space values
	@ParameterizedTest
	@CsvFileSource(resources="/negativeSingleCitizen.csv")
	void checkNegativeSingleBook(ArgumentsAccessor accessor) {
		Exception e = assertThrows(IllegalArgumentException.class, ()-> CitizenUtility.createNegativeCitizen(accessor));
		assertEquals(accessor.getString(6), e.getMessage());
	}
	
	//Checking that all fields of a citizen are null when its empty constructor is used
	@Test
	void checkEmptyConstructor() {
		Citizen citizen = new Citizen();
		assertNull(citizen.getAT());
		assertNull(citizen.getFirstName());
		assertNull(citizen.getLastName());
		assertNull(citizen.getGender());
		assertNull(citizen.getBirthDate());
		assertNull(citizen.getTaxId());
		assertNull(citizen.getAddress());
	}
	
	//Checking multiple negative cases for wrong at values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforAt(String AT) {
		Citizen citizen = new Citizen();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> citizen.setAT(AT));
		assertEquals("AT cannot be null or empty", e.getMessage());
	}
	
	//Checking multiple positive cases for proper AT values being set
	@ParameterizedTest
	@ValueSource(strings = { "ΑΒ345678", "bc111111", "cc654321", "CC909000" })
	void checkProperStringsforAt(String AT) {
		Citizen citizen = new Citizen();
		citizen.setAT(AT);
		assertEquals(citizen.getAT(),citizen);
	}
	
	//Checking multiple negative cases for wrong lastname values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforTitle(String lastName) {
		Citizen citizen = new Citizen();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> citizen.setLastName(lastName));
		assertEquals("Title cannot be null or empty", e.getMessage());
	}
	
	//Checking multiple positive cases for proper lastname values being set
	@ParameterizedTest
	@ValueSource(strings = { "Papadopoulos", "Anagnostopoulos", "Petrou", "Georgopoulos" })
	void checkProperStringsforLastName(String lastName) {
		Citizen citizen = new Citizen();
		citizen.setLastName(lastName);
		assertEquals(citizen.getLastName(),lastName);
	}
	
	//Checking multiple negative cases for wrong fistName values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforFirstName(String firstName) {
		Citizen citizen = new Citizen();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> citizen.setFirstName(firstName));
		assertEquals("FirstName cannot be null or empty", e.getMessage());
	}
	
	//Checking multiple positive cases for proper firstName values being set
	@ParameterizedTest
	@ValueSource(strings = { "Katerina", "Mixalis", "Maria", "Anna maria" })
	void checkProperStringsforPublisher(String firstName) {
		Citizen citizen = new Citizen();
		citizen.setFirstName(firstName);
		assertEquals(citizen.getFirstName(),firstName);
	}
	
	
	//Checking negative values for Gender
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforGender(String gender) {
		Citizen citizen = new Citizen();
		citizen.setGender(gender);
		assertNull(citizen.getGender(),gender);
	}
	
	//Checking positive values for gender
	@ParameterizedTest
	@ValueSource(strings = { "M", "F", "Male", "Female" })
	void checkPositiveValsforGender(String gender) {
		Citizen citizen = new Citizen();
		citizen.setGender(gender);
		assertEquals(citizen.getGender(),gender);
	}
	
	//Checking negative values for birthDate
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforBirthDate(String birthDate) {
		Citizen citizen = new Citizen();
		citizen.setBirthDate(LocalDate.parse(birthDate));
		assertNull(citizen.getBirthDate());
	}
		
	//Checking positive values for birthDate
	@ParameterizedTest
	@ValueSource(strings = { "d", "Date", "12-12-1212", "22-12-2222" })
	void checkPositiveValsforBirthDate(String birthDate) {
		Citizen citizen = new Citizen();
		citizen.setBirthDate(LocalDate.parse(birthDate));
		assertEquals(citizen.getBirthDate(),birthDate);
	}
	
	
			
	//Checking positive values for taxId
	@ParameterizedTest
	@ValueSource(strings = { "123456789", "111111111", "876543219", "000000000" })
	void checkPositiveValsforTaxID(String taxId) {
		Citizen citizen = new Citizen();
		citizen.setTaxId(taxId);
		assertEquals(citizen.getTaxId(),taxId);
	}
	
	//Checking positive values for address
		@ParameterizedTest
		@ValueSource(strings = { "panormou 27", "efesoy 12", "vanarni 72", "patron 124" })
		void checkPositiveValsforAddress(String address) {
			Citizen citizen = new Citizen();
			citizen.setAddress(address);
			assertEquals(citizen.getAddress(),address);
		}
}
