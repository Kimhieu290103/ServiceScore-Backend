package dtn.ServiceScore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "1.0"))
@SpringBootApplication
@EnableScheduling
public class ServiceScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceScoreApplication.class, args);
    }

}
