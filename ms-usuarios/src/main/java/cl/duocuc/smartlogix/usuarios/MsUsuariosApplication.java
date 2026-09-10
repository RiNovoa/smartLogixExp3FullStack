package cl.duocuc.smartlogix.usuarios;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }
    @Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
}