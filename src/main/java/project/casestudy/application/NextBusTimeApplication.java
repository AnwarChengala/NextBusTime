package project.casestudy.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "project.casestudy" })
public class NextBusTimeApplication {

    public static void main(String... args)  {
        SpringApplication.run(NextBusTimeApplication.class, args);
    }
}
