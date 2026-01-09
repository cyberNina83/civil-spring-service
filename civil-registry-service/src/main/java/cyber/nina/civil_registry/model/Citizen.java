package cyber.nina.civil_registry.model;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



@Entity
public class Citizen {

// ΑΤ: 8 ψηφία, κλειδί
@Id
@NotBlank(message = "AT cannot be blank!")
@Pattern(regexp = "^[0-9]{8}$", message = "AT must consist of exactly 8 digits")
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
@Column(name = "birth_date", nullable = false)
private LocalDate birthDate;

// ΑΦΜ (μη υποχρεωτικό, αλλά αν δοθεί 9 ψηφία)
@Column(name = "tax_id", nullable = true, length = 9)
private String taxId;

// Διεύθυνση κατοικίας
@Column(name = "address", nullable = true)
private String address;

public Citizen() {}



private Citizen(Builder builder) {
	this.AT = builder.at;
	this.firstName= builder.firstname;
	this.lastName = builder.lastname;
	this.gender = builder.gender;
	this.birthDate = LocalDate.parse(builder.birthDate);
	
	
	setTaxId(builder.taxId);
	setAddress (builder.address);
	
}
public static class Builder{
	private String at = null;
    private String firstname = null;
    private String lastname = null;
    private String gender = null;
    private String birthDate = null;
    private String taxId = null;
    private String address = null;
    
    private static void checkSingleValue(String value, String message) throws IllegalArgumentException{
    	if (value == null || value.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
    }
    
    public Builder(String at, String firstname,String lastname, String gender, String birthDate) throws IllegalArgumentException{
    	checkSingleValue(at, "AT");
  
    	
    	this.at = at;
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.gender = gender;
    	this.birthDate = birthDate;
    }
    
 
    
    public Builder setTaxId(String value) {
    	this.taxId = value;
    	return this;
    }
    
    public Builder setAddress(String value) {
    	this.address = value;
    	return this;
    }
    
    
    public Citizen build() {
    	return new Citizen(this);
    }
}


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

