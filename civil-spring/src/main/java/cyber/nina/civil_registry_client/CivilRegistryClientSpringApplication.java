package cyber.nina.civil_registry_client;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;

import cyber.nina.civil_registry_client.client.MyRestClient;
import cyber.nina.civil_registry.configuration.ImmutableApiConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ImmutableApiConfiguration.class)
public class CivilRegistryClientSpringApplication  implements CommandLineRunner{
	
	@Autowired
	private MyRestClient mrc;

	public static void main(String[] args) {
		SpringApplication.run(CivilRegistryClientSpringApplication.class, args);
	}
	
	@Override
	public void run(String... args) {
		//MediaType xml = MediaType.APPLICATION_XML;
    	MediaType json = MediaType.APPLICATION_JSON;
    	String AT1 = "12345688";
    	String AT2 = "87654388";
    	String firstname1= "aliki";
    	String lastname1="anagnostoy";
    	String firstname2="maria";
    	String lastname2="makri";
    	//String gender1 = "male";
    	String gender2 = "female";
    	LocalDate datebirth1 = LocalDate.parse("2008-12-13");
    	LocalDate datebirth2 = LocalDate.parse("2008-10-13");
    	//System.out.println(" all citizens in different media types");
    	//Adding two citizens/
    	mrc.addCitizen(AT1,firstname1, lastname1,gender2, datebirth1,null, null, json);
    	
    	mrc.addCitizen(AT2,firstname2, lastname2,gender2, datebirth2,null, null, json);
    	//mrc.addCitizen(AT1,firstname1, lastname1,gender1, datebirth1,null, null, json);
    
    	
    	System.out.println(" all citizens in different media types");
    //	mrc.getCitizens(xml);
    //	mrc.getCitizens(json);
    	
    	
    	System.out.println("Getting one citizen in different media types");
  //  	mrc.getCitizen(AT1,xml);
  //  	mrc.getCitizen(AT1,json);
    	
    	System.out.println("Updating one book & checking the update");
   // 	mrc.updateCitizen(AT1,json);
 //   	mrc.getCitizen(AT1,json);
    	
    	System.out.println("Deleting first citizen & checking the deletion");
    	
    	mrc.deleteCitizen(AT1);
    	mrc.deleteCitizen(AT1);
    //	mrc.getCitizens(json);
	}

}
