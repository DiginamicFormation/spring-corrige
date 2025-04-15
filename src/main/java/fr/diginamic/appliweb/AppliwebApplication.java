package fr.diginamic.appliweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application web : API villes et départements
 */
@SpringBootApplication
public class AppliwebApplication {

	/** Exécution de l'application
	 * @param args non utilisés
	 */
	public static void main(String[] args) {

		SpringApplication.run(AppliwebApplication.class, args);
	}
}
