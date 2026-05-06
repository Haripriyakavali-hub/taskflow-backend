package com.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.taskflow")
public class Taskflow1Application {

	public static void main(String[] args) {
		SpringApplication.run(Taskflow1Application.class, args);
	}

}
