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

    private Imagen imagen;

    private Paciente p;

    private Medico m;

    private Imagen imagen2;

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
    @DisplayName("Create and delete an Informe successfully")
    void informeController_createAndDeleteInformeSuccessfully() {
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

        // 7. Crear un informe asociado a la imagen
        Informe informe = new Informe("Prediccion positiva", "El paciente muestra signos positivos.", imagenSubida);
        client.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(informe), Informe.class)
                .exchange()
                .expectStatus().isCreated();

        // 8. Verificar que el informe se ha creado
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

        // 9. Eliminar el informe
        client.delete().uri("/informe/" + informeCreado.getId())
                .exchange()
                .expectStatus().isNoContent();

        // 10. Verificar que el informe ha sido eliminado
        client.get().uri("/informe/" + informeCreado.getId())
                .exchange()
                .expectStatus().isOk() // Espera 200 OK
                .expectBody()
                .consumeWith(response -> assertNull(response.getResponseBody()));
    }
}
