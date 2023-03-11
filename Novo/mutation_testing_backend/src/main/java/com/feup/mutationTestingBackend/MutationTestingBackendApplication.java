package com.feup.mutationTestingBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MutationTestingBackendApplication {

	// For the file log
	static{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
	}

	public static void main(String[] args) {
		SpringApplication.run(MutationTestingBackendApplication.class, args);
	}

}
