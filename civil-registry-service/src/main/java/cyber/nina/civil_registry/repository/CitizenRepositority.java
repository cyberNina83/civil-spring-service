

package cyber.nina.civil_registry.repository;

import java.util.List;
//import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cyber.nina.civil_registry.model.Citizen;

public interface CitizenRepositority extends JpaRepository<Citizen, String>, JpaSpecificationExecutor<Citizen> {
//public interface CitizenRepositority extends JpaRepository<Citizen, String> {
// Αναζήτηση βάσει μόνο AT
List<Citizen> findByAT(String at);



// Αναζήτηση βάσει πεδίων
List<Citizen> findByFirstName(String firstName);
List<Citizen> findByLastName(String lastName);
List<Citizen> findByGender(String gender);
List<Citizen> findByBirthDate(String birthDate); // ή LocalDate depending on mapping
List<Citizen> findByTaxId(String taxId);
List<Citizen> findByAddress(String address);

// Συνδυασμοί δύο πεδίων
List<Citizen> findByFirstNameAndLastName(String firstName, String lastName);
List<Citizen> findByFirstNameAndGender(String firstName, String gender);
List<Citizen> findByLastNameAndAT(String lastName, String at);

// Συνδυασμοί περισσότερων πεδίων
List<Citizen> findByFirstNameAndLastNameAndGender(String firstName, String lastName, String gender);
List<Citizen> findByATAndFirstName(String at, String firstName);

}

