package org.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "org.main",
    "org.model",
    "org.repository",
    "org.view"
})
@EnableJpaRepositories(basePackages = "org.repository")
@EntityScan(basePackages = "org.model")
public class DentalSonrisaPlenaApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // Forzar modo gráfico para Swing
        SpringApplication.run(DentalSonrisaPlenaApplication.class, args);
    }
}