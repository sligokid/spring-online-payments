package ie.magoo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class Application.
 * 
 * The Spring Boot (stand-alone embedded web container) main method.
 * 
 * ie. Run as -> Java Application
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
