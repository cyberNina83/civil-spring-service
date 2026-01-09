package cyber.nina.civil_registry.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import cyber.nina.civil_registry.model.Citizen;
import cyber.nina.civil_registry.repository.CitizenRepositority;


public class CitizenSpecificationService {
	private final CitizenRepositority repo;
	public CitizenSpecificationService(CitizenRepositority repo)
	{ this.repo = repo; }


	public List<Citizen> searchCitizens(String lastname, String at, String firstname,
	                                  LocalDate birthDate, String address, String gender, String taxId) {
	    Specification<Citizen> spec = Specification.where((Specification<Citizen>) null);
	    if (lastname != null && !lastname.isBlank()) spec = spec.and(CitizenSpecifications.byLastName(lastname));
	    if (at != null && !at.isBlank()) spec = spec.and(CitizenSpecifications.byAT(at));
	    if (firstname != null && !firstname.isBlank()) spec = spec.and(CitizenSpecifications.byFirstName(firstname));
	    if (birthDate != null) spec = spec.and(CitizenSpecifications.byBirthDate(birthDate));
	    if (address != null && !address.isBlank()) spec = spec.and(CitizenSpecifications.byAddress(address));
	    if (gender != null && !gender.isBlank()) spec = spec.and(CitizenSpecifications.byGender(gender));
	    if (taxId != null && !taxId.isBlank()) spec = spec.and(CitizenSpecifications.byTaxId(taxId));
	    return (spec == null) ? repo.findAll() : repo.findAll(spec);
	}
}


