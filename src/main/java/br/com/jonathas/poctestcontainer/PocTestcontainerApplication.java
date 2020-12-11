package br.com.jonathas.poctestcontainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PocTestcontainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocTestcontainerApplication.class, args);
    }

}
