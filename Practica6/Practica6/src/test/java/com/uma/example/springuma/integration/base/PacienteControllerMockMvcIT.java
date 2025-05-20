package com.uma.example.springuma.integration.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PacienteControllerMockMvcIT extends AbstractIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private  Medico m;

    @BeforeAll
    void setUp() throws Exception {
        m = new Medico();
        m.setId(1);
        m.setDni("12345678H");
        m.setEspecialidad("Cirugia plastica");
        m.setNombre("Paulino");

        // CREATE DOCTOR
        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(m)))
                .andExpect(status().isCreated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("create paciente, get it and update it correctly")
    void createPaciente_getAndUpdateCorrectly() throws Exception {
        Paciente p = new Paciente();
        p.setId(1L);
        p.setMedico(m);
        p.setCita("2023-10-01");
        p.setNombre("Juan");
        p.setEdad(45);
        p.setDni("98765432Z");

        // CREATE PACIENTE
        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated());

        // CHECK THAT IT IS CREATED
        this.mockMvc.perform(get("/paciente/{id}", p.getId()))
                .andExpect(status().isOk())
                //ID is a long and gives an error even if we initializes with id = 1L
                .andExpect(jsonPath("$.nombre", is(p.getNombre())))
                .andExpect(jsonPath("$.edad", is(p.getEdad())))
                .andExpect(jsonPath("$.dni", is(p.getDni())));

        // UPDATE PACIENTE ATTRIBUTE
        p.setNombre("Carlos");

        this.mockMvc.perform(put("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().is2xxSuccessful());

        // CHECK THAT IT IS UPDATED
        this.mockMvc.perform(get("/paciente/{id}", p.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(p.getNombre())));
    }

    @Test
    @DisplayName("delete paciente and get gives an error")
    void deletePaciente_getGivesAnError() throws Exception {
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNombre("Ana");
        p.setEdad(30);
        p.setDni("12345678A");

        // CREATE PACIENTE
        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated());

        // CHECK THAT IT IS CREATED
        this.mockMvc.perform(get("/paciente/{id}", p.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id", is(p.getId())))
                .andExpect(jsonPath("$.nombre", is(p.getNombre())))
                .andExpect(jsonPath("$.edad", is(p.getEdad())))
                .andExpect(jsonPath("$.dni", is(p.getDni())));

        // REMOVE PACIENTE
        this.mockMvc.perform(delete("/paciente/{id}", p.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        // CHECK THAT IT IS REMOVED
        this.mockMvc.perform(get("/paciente/{id}", p.getId()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("create paciente and get list pacientes of medico, paciente is in the list")
    void createPaciente_getListPacientes() throws Exception {
        Paciente p = new Paciente();
        p.setId(1);
        p.setNombre("Pedro");
        p.setEdad(40);
        p.setDni("87654321B");
        p.setMedico(m);

        // CREATE PACIENTE
        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated());

        // CHECK THAT IT IS CREATED
        this.mockMvc.perform(get("/paciente/{id}", p.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre", is(p.getNombre())))
                .andExpect(jsonPath("$.edad", is(p.getEdad())))
                .andExpect(jsonPath("$.dni", is(p.getDni())));

        // GET LIST PACIENTES OF MEDICO
        this.mockMvc.perform(get("/paciente/medico/{id}", m.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].nombre", is(p.getNombre())))
                .andExpect(jsonPath("$[0].edad", is(p.getEdad())))
                .andExpect(jsonPath("$[0].dni", is(p.getDni())));
    }



    @Test
    @DisplayName("Asociar paciente a médico y detectar cambio de médico")
    void asociarPacienteYDetectarCambioDeMedico() throws Exception {
        // Crear médico 1
        Medico medico1 = new Medico();
        medico1.setId(2);
        medico1.setNombre("Pérez");
        medico1.setEspecialidad("Cardiología");
        medico1.setDni("11111111A");

        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medico1)))
                .andExpect(status().isCreated());

        // CREATE MEDICO 2
        Medico medico2 = new Medico();
        medico2.setId(3);
        medico2.setNombre("Dr. López");
        medico2.setEspecialidad("Neurología");
        medico2.setDni("22222222B");

        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medico2)))
                .andExpect(status().isCreated());

        // CREATE PACIENTE 1 WITH MEDICO 1
        Paciente paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Juan");
        paciente.setEdad(40);
        paciente.setDni("33333333C");
        paciente.setMedico(medico1);

        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated());

        // CHECK ASSOCIATION WITH MEDICO 1
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medico.dni", is(medico1.getDni())));

        // UPDATE ASSOCIATION TO MEDICO 2
        paciente.setMedico(medico2);

        this.mockMvc.perform(put("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isNoContent());

        // CHECK ASSOCIATION WITH MEDICO 2
        this.mockMvc.perform(get("/paciente/{id}", paciente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medico.dni", is(medico2.getDni())));
    }
}