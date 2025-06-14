package com.uma.example.springuma.integration.base;


import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.ImagenService;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {
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

        //Medico y Pacientes
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

    @Test
    @DisplayName("Subir imagen asociada a un paciente correctamente")
    void uploadImage_uploadsCorrectly() {
        // 1. Crear un Médico para asociar al Paciente
        client.post().uri("/medico")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(m), Medico.class)
                .exchange()
                .expectStatus().isCreated();

        //comprobar que el medico se ha creado
        client.get().uri("/medico/" + m.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Medico.class);

        // 2. Crear un Paciente y asociarlo al Médico recién creado
        client.post().uri("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(p), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Paciente.class);

        // 3. Preparar la imagen para la subida
        FileSystemResource imageFile = new FileSystemResource("src/test/resources/healthy.png");

        // 4. Construir el cuerpo multipart
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("image", imageFile, MediaType.IMAGE_PNG);
        bodyBuilder.part("paciente", p, MediaType.APPLICATION_JSON);

        // 5. Realizar la petición POST para subir la imagen
        client.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isOk();

        //comprobar que la imagen se ha subido y que el paciente asociado es correcto
        client.get().uri("/imagen/paciente/" + p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Imagen.class)
                .consumeWith(response -> {
                    List<Imagen> imagenes = response.getResponseBody();
                    assert imagenes != null && !imagenes.isEmpty();
                    Imagen imagenSubida = imagenes.get(0);
                    assert imagenSubida.getPaciente().getId() == p.getId();
                });
    }

    @Test
    @DisplayName("Realizar predicción de una imagen de un paciente correctamente")
    void predictImage_returnsPredictionSuccessfully() {
        // 1. Crear un Médico para asociar al Paciente
        client.post().uri("/medico")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(m), Medico.class)
                .exchange()
                .expectStatus().isCreated();

        // 2. Crear un Paciente y asociarlo al Médico recién creado
        client.post().uri("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(p), Paciente.class)
                .exchange()
                .expectStatus().isCreated();

        // 3. Preparar la imagen para la subida
        FileSystemResource imageFile = new FileSystemResource("src/test/resources/healthy.png");

        // 4. Construir el cuerpo multipart
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("image", imageFile, MediaType.IMAGE_PNG);
        bodyBuilder.part("paciente", p, MediaType.APPLICATION_JSON);

        // 5. Subir la imagen
        client.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().isOk();

        // 6. Obtener la imagen subida
        Imagen imagenSubida = client.get().uri("/imagen/paciente/" + p.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Imagen.class)
                .returnResult()
                .getResponseBody()
                .get(0);

        // 7. Realizar la predicción de la imagen
        client.get().uri("/imagen/predict/" + imagenSubida.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String prediction = response.getResponseBody();
                    assert prediction != null;
                    assert prediction.contains("'status': 'Not cancer'") || prediction.contains("'status': 'Cancer'");                });
    }
}