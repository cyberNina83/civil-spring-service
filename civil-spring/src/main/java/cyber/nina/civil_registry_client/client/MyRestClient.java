package cyber.nina.civil_registry_client.client;

import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import cyber.nina.civil_registry.model.Citizen;
import cyber.nina.civil_registry.configuration.ImmutableApiConfiguration;


@Component
public class MyRestClient {
	private final ImmutableApiConfiguration details;
	
	private RestClient client;
	

	
	public MyRestClient(ImmutableApiConfiguration details){
		this.details = details;
		initClient();
	}
	
	private void initClient() {
		System.out.println("initClient");
		String url = "http://" + details.getHost() + ":" + details.getPort() + "/" + details.getApi();
		System.out.println(url);
		client = RestClient.create(url);
	}
	
	public void getCitizens(MediaType type) {
		client.get()
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The list of citizens is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	public void getBooksWithParams(String title, String publisher, MediaType type) {
		String queryPart = "";
		if (title != null && !title.isBlank()) queryPart += "title=" + title;
		if (publisher != null && !publisher.isBlank()) queryPart += "publisher=" + publisher;
		if (!queryPart.isBlank()) queryPart = "?" + queryPart;
		client.get()
			.uri(queryPart)
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The list of books is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	public void getCitizen(String AT, MediaType type) {
		client.get()
			.uri("/{id}",AT)
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The citizen with AT: " + AT + " is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	private Citizen createCitizen(String AT, String firstname, String lastname, String gender, 
			LocalDate dateOfBirth,String AFM, String address) 
	{
    	Citizen citizen = new Citizen();
    	citizen.setAT(AT);
    	citizen.setFirstName(firstname);
    	citizen.setLastName(lastname);
    	citizen.setGender(gender);
    	citizen.setBirthDate(dateOfBirth);
    	citizen.setTaxId(AFM);
    	citizen.setAddress(address);
    	
    	return citizen;
    }
	
	private Citizen createCitizen(String AT) 
	{
    	Citizen citizen = new Citizen();
    	citizen.setAT(AT);
    	return citizen;
    }
	
	public void addCitizen(String AT, String firstname, String lastname, String gender, 
			LocalDate dateOfBirth,String AFM, String address , MediaType type) {
		Citizen citizen = createCitizen(AT,firstname, lastname, gender,dateOfBirth, AFM, address);
		client.post()
			.contentType(type)
			.body(citizen)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError()) {
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is5xxServerError()) {
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The Citizen with AT: " + AT + " was created successfully");
						System.out.println("The created citizens's URL is: " + response.getHeaders().get("Location"));
					}
					return null;
				}
			);
	}
	
	public void updateCitizen(String AT, MediaType type) {
		Citizen citizen = createCitizen(AT);
		client.put()
			.uri("/{id}",AT)
			.contentType(type)
			.body(citizen)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError()) {
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is5xxServerError()) {
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The citizen with AT: " + AT + " has been successfully updated");
					}
					return null;
				}
			);
	}
	
	public void deleteCitizen(String AT) {
		client.delete()
			.uri("/{id}",AT)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError()) {
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is5xxServerError()) {
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The Citizen with AT: " + AT + " has been successfully deleted");
					}
					return null;
				}
			);
	}
}

