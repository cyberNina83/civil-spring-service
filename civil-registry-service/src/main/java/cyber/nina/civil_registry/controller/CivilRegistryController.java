package cyber.nina.civil_registry.controller;
import java.net.URI;
import java.time.LocalDate;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cyber.nina.civil_registry.model.Citizen;
import cyber.nina.civil_registry.repository.CitizenRepositority;

import jakarta.validation.Valid;

// REST controller για το μητρώο πολιτών
@RestController
@RequestMapping("/api/citizens")
public class CivilRegistryController {
private final CitizenRepositority repo;
CitizenSpecificationService service;

public CivilRegistryController(CitizenRepositority repo) {
    this.repo = repo;
}


@GetMapping(produces = {"application/json", "application/xml"})
List<Citizen> getCitizens(@RequestParam(required = false) String lastname,
                          @RequestParam(required = false) String at) {
    boolean surnameNotEmpty = (lastname != null && !lastname.isBlank());
    boolean privateNumberNotEmpty = (at != null && !at.isBlank());
    
    if (!surnameNotEmpty && !privateNumberNotEmpty) {
        return repo.findAll();
    } else {
        if (surnameNotEmpty && !privateNumberNotEmpty) return repo.findByAT(at);
        else if (privateNumberNotEmpty && !surnameNotEmpty) return repo.findByLastName(lastname);
        else return repo.findByLastNameAndAT(lastname, at);
    }
}

/*
@GetMapping(produces = {"application/json", "application/xml"})
List<Citizen> searchCitizens(
@RequestParam(required = false) String lastname,
@RequestParam(required = false) String at,
@RequestParam(required = false) String firstname,
@RequestParam(required = false) String gender,
@RequestParam(required = false) LocalDate bithDate,
@RequestParam(required = false) String taxid,
@RequestParam(required = false) String address) {

// validation επιπλέον κενές/μη έγκυρες τιμές (optional)
return service.searchCitizens(lastname, at, firstname, bithDate, address, gender, taxid);
}*/

@GetMapping(value = "{at}", produces = {"application/json", "application/xml"})
Citizen getCitizen(@PathVariable String at) {
    return repo.findById(at).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given at does not exist!"));
}

@PostMapping(consumes = {"application/json", "application/xml"})
ResponseEntity<?> insertCitizen(@Valid @RequestBody Citizen citizen) {
    if (repo.findById(citizen.getAT()).isPresent())
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citizen with given id already exists!");
    else {
        repo.save(citizen);
        try {
            String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/citizens/" + citizen.getAT();
            return ResponseEntity.created(new URI(url)).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while generating the response!");
        }
    }
}

@PutMapping(value = "{id}", consumes = {"application/json", "application/xml"})
ResponseEntity<?> updateCitizen(@PathVariable String id, @Valid @RequestBody Citizen citizen) {
    if (!citizen.getAT().equals(id))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update citizen with wrong id!");
    else return repo.findById(id)
        .map(oldCitizen -> {
            oldCitizen.setFirstName(citizen.getFirstName());
            oldCitizen.setLastName(citizen.getLastName());
            oldCitizen.setBirthDate(citizen.getBirthDate());
            oldCitizen.setAddress(citizen.getAddress());
            oldCitizen.setGender(citizen.getGender());
            oldCitizen.setTaxId(citizen.getTaxId());
            // Διατήρησε το privateNumber
            oldCitizen.setAT(citizen.getAT());
            repo.save(oldCitizen);
            return ResponseEntity.noContent().build();
        })
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given id does not exist!"));
}

@DeleteMapping("{id}")
ResponseEntity<?> deleteCitizen(@PathVariable String id) {
    return repo.findById(id)
            .map(oldCitizen -> {
                repo.deleteById(id);
                return ResponseEntity.noContent().build();
            })
            .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given id does not exist!"));
}

// Διαχείριση σύνθετων σφαλμάτων validation
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
public Map<String, String> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return errors;
}
}

