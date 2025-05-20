package com.uma.example.springuma.integration.base;

import com.uma.example.springuma.model.*;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InformeControllerWebTestClientIT{

    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    private Paciente p;

    private Medico m;

    @Autowired
    private ImagenService imagenService;

    @PostConstruct
    public void init() throws IOException {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofMillis(30000)).build();

        //Medico and Paciente
        // Create an object for the use in tests
        m = new Medico();
        m.setId(1);
        m.setDni("12345678H");
        m.setEspecialidad("Cirugia plastica");
        m.setNombre("Paulino");

        p = new Paciente();
        p.setId(1L);
        p.setMedico(m);
        p.setCita("2023-10-01");
        p.setNombre("Juan");
        p.setEdad(45);
        p.setDni("98765432Z");
    }

    // Solo hemos creado un único test para el camino feliz ya que al ser pruebas de integración
    // , siguen otro tipo de estructa y por lo tanto nos hemos focalizado en cubrir lo que nos
    // pedía el enunciado y verificar el correcto funcionamiento de un conjunto de instrucciones.
    // Hemos seguido la estructura que se nos especifico en las clases prácticas.

    @Test
    @DisplayName("Create and delete an Informe successfully")
    void informeController_createAndDeleteInformeSuccessfully() {
        // CREATE MEDICO
        client.post().uri("/medico")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(m), Medico.class)
                .exchange()
                .expectStatus().isCreated();

        // CREATE PACIENTE WITH MEDICO "m"
        client.post().uri("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(p), Paciente.class)
                .exchange()
                .expectStatus().isCreated();

        // Image Preparation
        FileSystemResource imageFile = new FileSystemResource("src/test/resources/healthy.png");

        // creation MultipartBodyBuilder object
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("image", imageFile, MediaType.IMAGE_PNG);
        bodyBuilder.part("paciente", p, MediaType.APPLICATION_JSON);

        // UPLOAD IMAGE
        client.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isOk();

        // GET IMAGE UPLOADED
        Imagen imagenSubida = client.get().uri("/imagen/paciente/" + p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Imagen.class)
                .returnResult()
                .getResponseBody()
                .get(0);

        // CREATE INFORME
        Informe informe = new Informe("Prediccion positiva", "El paciente muestra signos positivos.", imagenSubida);
        client.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(informe), Informe.class)
                .exchange()
                .expectStatus().isCreated();

        // CHECK INFORME HAS BEEN CREATED
        Informe informeCreado = client.get().uri("informe/imagen/" + imagenSubida.getId()) // URI corregida (sin / al inicio)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Informe.class)
                .returnResult()
                .getResponseBody()
                .get(0);
        assert informeCreado != null;
        assert informeCreado.getContenido().equals("El paciente muestra signos positivos.");
        assert informeCreado.getImagen().getId() == imagenSubida.getId();

        // REMOVE INFORME
        client.delete().uri("/informe/" + informeCreado.getId())
                .exchange()
                .expectStatus().isNoContent();

        // CHECKED THAT HAS BEEN DELETED
        client.get().uri("/informe/" + informeCreado.getId())
                .exchange()
                .expectStatus().isOk() // Espera 200 OK
                .expectBody()
                .consumeWith(response -> assertNull(response.getResponseBody()));
    }
}
