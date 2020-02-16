package io.sandark.vehiclecrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApplicationRoot {

	@RequestMapping("/")
	public String home() {
		return "Application root";
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRoot.class, args);
	}

}
