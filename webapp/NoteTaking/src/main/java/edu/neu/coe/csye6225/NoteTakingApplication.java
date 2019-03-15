package edu.neu.coe.csye6225;

import edu.neu.coe.csye6225.entity.Note;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"edu.neu.coe.csye6225.mapper"})
public class NoteTakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteTakingApplication.class, args);
	}

}

