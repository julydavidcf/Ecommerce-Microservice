package com.example.ecomerse;

import com.example.ecomerse.Dao.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EcomerseApplication {

	public static void main(String[] args) {


		SpringApplication.run(EcomerseApplication.class, args);
	}

}
