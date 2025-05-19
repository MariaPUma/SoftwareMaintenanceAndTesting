package com.uma.example.springuma.integration.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.ImagenService;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {
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
        client = WebTestClient.bindToServer().baseUrl("http://localhost:"+port)
                .responseTimeout(Duration.ofMillis(30000)).build();

        //Medico, Pacientes e Imágenes
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


//        // Cargar archivo desde resources
//        ClassPathResource resource = new ClassPathResource("src/test/resources/healthty.png");
//        MockMultipartFile file = new MockMultipartFile(
//                "imageHealthy",
//                resource.getFilename(),
//                "image/png",
//                resource.getInputStream()
//        );
//
//
//        imagen = new Imagen();
//        imagen.setId(1L);
//        imagen.setNombre(file.getOriginalFilename());
//        imagen.setPaciente(p);
//        imagen.setFecha(Calendar.getInstance());
//        imagen.setFile_content(file.getBytes());
//
//        ClassPathResource resource_2 = new ClassPathResource("src/test/resources/no_healthty.png");
//
//        MockMultipartFile file_2 = new MockMultipartFile(
//                "imageNoHealthty",
//                resource_2.getFilename(),
//                "image/png",
//                resource_2.getInputStream()
//        );
//
//        imagen2 = new Imagen();
//        imagen2.setId(2L);
//        imagen2.setNombre(file_2.getOriginalFilename());
//        imagen2.setPaciente(p);
//        imagen2.setFecha(Calendar.getInstance());
//        imagen2.setFile_content(file_2.getBytes());
    }

//    @Test
//    @DisplayName("upload image from patient correctly")
//    void uploadImage_uploadsCorrectly(){
//        // Crear archivo de prueba desde resources
//        File uploadFile = new File("./src/test/resources/healthy.png");
//
//        // Crear paciente
//        client.post().uri("/paciente")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(p), Paciente.class)
//                .exchange()
//                .expectStatus().isCreated();
//
//        // Construir la solicitud multipart para subir la imagen
//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        builder.part("image", new FileSystemResource(uploadFile));
//        builder.part("paciente", p);
//
//        // Subir la imagen asociada al paciente
//        client.post().uri("/imagen")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(BodyInserters.fromMultipartData(builder.build()))
//                .exchange()
//                .expectStatus().isOk();
//
//        // Verificar que la imagen fue subida y asociada al paciente
//
//    }
@Test
@DisplayName("Subir imagen asociada a un paciente correctamente")
void uploadImage_uploadsCorrectly() {
    // Crear archivo de prueba desde resources
    File uploadFile = new File("./src/test/resources/healthy.png");
    if (!uploadFile.exists()) {
        throw new RuntimeException("El archivo healthy.png no existe en la ruta especificada.");
    }



    // Crear paciente
    client.post().uri("/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(p), Paciente.class)
            .exchange()
            .expectStatus().isCreated();

    // Construir la solicitud multipart para subir la imagen
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("image", new FileSystemResource(uploadFile));
    builder.part("paciente", p);

    // Subir la imagen asociada al paciente
    client.post().uri("/imagen")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.response").isEqualTo("file uploaded successfully : healthy.png");

    // Verificar que la imagen fue subida y asociada al paciente
    client.get().uri("/imagen/paciente/{id}", p.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].nombre").isEqualTo("healthy.png")
            .jsonPath("$[0].paciente.id").isEqualTo(p.getId());
}


}