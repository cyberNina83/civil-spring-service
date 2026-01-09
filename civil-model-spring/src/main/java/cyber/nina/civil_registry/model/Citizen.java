package cyber.nina.civil_registry.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Basic;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class Citizen {

// ΑΤ: 8 ψηφία, κλειδί
@Id
@NotBlank(message = "AT cannot be blank!")
@Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "AT must consist of exactly 8 alphanumeric characters")
@Column(name = "at", nullable = false, unique = true, length = 8)
private String AT;

// Όνομα
@Basic(optional = false)
@NotBlank(message = "First name cannot be blank!")
@Column(name = "first_name", nullable = false)
private String firstName;

// Επίθετο
@Basic(optional = false)
@NotBlank(message = "Last name cannot be blank!")
@Column(name = "last_name", nullable = false)
private String lastName;

// Φύλο
@Basic(optional = false)
@NotBlank(message = "Gender cannot be blank!")
@Column(name = "gender", nullable = false)
private String gender;

// Ημερομηνία γέννησης (birthDate)
@Basic(optional = false)
@NotNull(message = "Birth date is required")
@JsonFormat(pattern = "dd-MM-yyyy")
@Column(name = "birth_date", nullable = false)
private LocalDate birthDate;

// ΑΦΜ (μη υποχρεωτικό, αλλά αν δοθεί 9 ψηφία)
@Column(name = "tax_id", nullable = true, length = 9)
private String taxId;

// Διεύθυνση κατοικίας
@Column(name = "address", nullable = true)
private String address;

public Citizen() {}

// Getters and setters

public String getAT() {
    return AT;
}

public void setAT(String AT) {
    this.AT = AT;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

public LocalDate getBirthDate() {
    return birthDate;
}

public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
}

public String getTaxId() {
    return taxId;
}

public void setTaxId(String taxId) {
    this.taxId = taxId;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

// toString για ευκολία
@Override
public String toString() {
    return "Citizen{" +
            "AT='" + AT + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", gender='" + gender + '\'' +
            ", birthDate=" + birthDate +
            ", taxId='" + taxId + '\'' +
            ", address='" + address + '\'' +
            '}';
}

// Optional: equals/hashCode βάσει AT
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Citizen)) return false;
    Citizen other = (Citizen) o;
    return this.AT != null && this.AT.equals(other.AT);
}

@Override
public int hashCode() {
    return AT != null ? AT.hashCode() : 0;
}
}

