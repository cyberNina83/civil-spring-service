package cyber.nina.civil_registry.controller;

import org.springframework.data.jpa.domain.Specification;

import cyber.nina.civil_registry.model.Citizen;

import java.time.LocalDate;

public class CitizenSpecifications {
	public static Specification<Citizen> byLastName(String lastName) {
		return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
		}
		public static Specification<Citizen> byAT(String at) {
		return (root, query, cb) -> cb.equal(root.get("AT"), at);
		}
		public static Specification<Citizen> byFirstName(String firstName) {
		return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
		}
		public static Specification<Citizen> byBirthDate(LocalDate birthDate) {
		return (root, query, cb) -> cb.equal(root.get("birthDate"), birthDate);
		}
		public static Specification<Citizen> byTaxId(String taxId) {
		return (root, query, cb) -> cb.equal(root.get("taxId"), taxId);
		}
		public static Specification<Citizen> byAddress(String address) {
		return (root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
		}
		public static Specification<Citizen> byGender(String gender) {
		return (root, query, cb) -> cb.equal(root.get("gender"), gender);
		}
}
