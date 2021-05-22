package net.mureng.api;

import net.mureng.ApiBasePackage;
import net.mureng.CoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { CoreBasePackage.class, ApiBasePackage.class })
public class MurengApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MurengApiApplication.class, args);
	}

}
