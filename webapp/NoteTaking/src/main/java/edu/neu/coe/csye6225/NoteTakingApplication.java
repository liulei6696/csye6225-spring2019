package edu.neu.coe.csye6225;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
class NoteTakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteTakingApplication.class, args);
	}

}

