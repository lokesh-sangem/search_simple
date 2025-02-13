package com.tac.search_simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class SearchSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchSimpleApplication.class, args);
	}

}
