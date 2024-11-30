package com.anantweb.restspringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RestSpringDemoApplication {

	public static void main(String[] args) {
		System.out.println("<<<<<-------- Inside Main() Before Calling run() -------->>>>>");

		//ConfigurableApplicationContext context =

		ApplicationContext context = SpringApplication.run(RestSpringDemoApplication.class, args);

		//context.close();
		System.out.println("\n<<<<<-------- " + context.getId() + " Started -------->>>>>");
	}

}
