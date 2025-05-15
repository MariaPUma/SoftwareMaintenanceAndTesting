package com.uma.example.springuma.integration.base;

import java.io.File;
import java.time.Duration;

import com.uma.example.springuma.model.Imagen;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {
    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    private Imagen imagen;

    @PostConstruct
    public void init() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:"+port)
                .responseTimeout(Duration.ofMillis(30000)).build();

        //Medico, Pacientes e Imágenes
    }



}
