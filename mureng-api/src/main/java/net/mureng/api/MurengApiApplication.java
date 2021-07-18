package net.mureng.api;

import net.mureng.ApiBasePackage;
import net.mureng.CoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@ServletComponentScan
@SpringBootApplication(scanBasePackageClasses = { CoreBasePackage.class, ApiBasePackage.class })
public class MurengApiApplication {

	@PostConstruct
	public void initialize() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MurengApiApplication.class, args);
	}

}
