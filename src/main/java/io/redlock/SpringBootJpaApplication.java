package io.redlock;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.stream.Stream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootJpaApplication.class, args);
  }

  @Bean ApplicationRunner init(CoverageRepository repository) {

    return args -> {
      CoverageXMLReader reader = new CoverageXMLReader(repository);
      reader.readFromXML(new FileInputStream("src/test/resources/sample.xml"));
      repository.findAll().forEach(System.out::println);
    };
  }
}
