package cyber.nina.citizen.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import cyber.nina.civil_registry.model.Citizen;

public class CitizenUtility {
	
	//Creating a citizen from each CSV row
		/**
		 * @param accessor
		 * @param start
		 * @return
		 */
		public static Citizen createPositiveCitizen(ArgumentsAccessor accessor, int start) {
			String at = accessor.getString(start + 0);
			String firstname = accessor.getString(start + 1);
			String lastname = accessor.getString(start + 2);
			String gender = accessor.getString(start + 3);
			String birthDate = accessor.getString(start + 4);
			
			String taxId = accessor.getString(start + 5);
			String address = accessor.getString(start + 6);
			
			
			return new Citizen.Builder(at, firstname, lastname, gender, birthDate).
					setTaxId(taxId).setAddress(address).build();
		}
		
		//Trying to create a citizen from each CVS row - The creation of citizen will deliberately fail
		public static void createNegativeCitizen(ArgumentsAccessor accessor) {
			String at = accessor.getString(0);
			String firstname = accessor.getString(1);
			String lastname = accessor.getString(2);
			String gender = accessor.getString(3);
			String birthDate = accessor.getString(4);
			
			String address = accessor.getString(6);
			
			Citizen citizen =new Citizen.Builder(at, firstname, lastname, gender, birthDate).
					setAddress(address).build();
			System.out.println("Got citizen: " + citizen);
		}
		
		//Checking if a created citizen from a CVS row has correct values
		public static void checkCitizen(Citizen citizen, ArgumentsAccessor accessor) {
			assertEquals(citizen.getAT(),accessor.get(0));
			assertEquals(citizen.getFirstName(),accessor.get(1));
			assertEquals(citizen.getLastName(),accessor.get(2));
			assertEquals(citizen.getGender(),accessor.get(3));
			assertEquals(citizen.getBirthDate(),accessor.get(4));
			assertEquals(citizen.getTaxId(),accessor.get(5));
			assertEquals(citizen.getAddress(),accessor.get(6));
		}

}
