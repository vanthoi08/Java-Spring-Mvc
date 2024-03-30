package vn.hoidanit.laptopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// include : bao gồm
// exclude : loại bỏ
// exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
@SpringBootApplication()
public class LaptopshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopshopApplication.class, args);

	}

}
