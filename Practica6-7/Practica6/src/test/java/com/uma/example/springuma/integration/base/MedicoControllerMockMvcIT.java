package com.uma.example.springuma.integration.base;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MedicoControllerMockMvcIT extends AbstractIntegration{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("create medico, get it and update it correctly")
    void createMedico_getAndUpdateCorrectly() throws Exception {
        Medico m = new Medico();
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

        //CHECKED THAT IS CREATED
        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id", is(m.getId())))
                .andExpect(jsonPath("$.nombre", is(m.getNombre())))
                .andExpect(jsonPath("$.dni", is(m.getDni())))
                .andExpect(jsonPath("$.especialidad", is(m.getEspecialidad())));

        //Change for update
        m.setNombre("Javina");

        //UPDATE DOCTOR ATRIBUTES
        this.mockMvc.perform(put("/medico")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(m)))
                .andExpect(status().is2xxSuccessful());

        //CHECK THAT IS UPDATED
        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni())
                .contentType("application/json"))
                .andExpect(jsonPath("$.nombre", is(m.getNombre())));



        //REMOVE DOCTOR
        this.mockMvc.perform(delete("/medico/{id}", m.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(m)));

        //CHECK THAT IS REMOVED
        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete medico and get gives an error")
    void deleteMedico_getGivesAnError() throws Exception {
        Medico m = new Medico();
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

        //CHECKED THAT IS CREATED
        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre", is(m.getNombre())))
                .andExpect(jsonPath("$.dni", is(m.getDni())))
                .andExpect(jsonPath("$.especialidad", is(m.getEspecialidad())));

        //REMOVE DOCTOR
        this.mockMvc.perform(delete("/medico/{id}", m.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(m)));


        //CHECK THAT IS REMOVED
        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni()))
                .andExpect(status().isNotFound());
    }


//    CRUD TEST UNIFIED
//    @Test
//    @DisplayName("create medico and get it correctly")
//    void createMedico_isObtainedWithGet() throws Exception {
//        Medico m = new Medico();
//        m.setId(1);
//        m.setDni("12345678H");
//        m.setEspecialidad("Cirugia plastica");
//        m.setNombre("Paulino");
//
//        // CREATE DOCTOR
//        this.mockMvc.perform(post("/medico")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(m)))
//                .andExpect(status().isCreated())
//                .andExpect(status().is2xxSuccessful());
//
//        //CHECKED THAT IS CREATED
//        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
////                .andExpect(jsonPath("$.id", is(m.getId())))
//                .andExpect(jsonPath("$.nombre", is(m.getNombre())))
//                .andExpect(jsonPath("$.dni", is(m.getDni())))
//                .andExpect(jsonPath("$.especialidad", is(m.getEspecialidad())));
//
//        //Change for update
//        m.setNombre("Javina");
//
//        //UPDATE DOCTOR ATRIBUTES
//        this.mockMvc.perform(put("/medico")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(m)))
//                .andExpect(status().is2xxSuccessful());
//
//        //CHECK THAT IS UPDATED
//        this.mockMvc.perform(get("/medico/dni/{dni}", m.getDni())
//                        .contentType("application/json"))
//                .andExpect(jsonPath("$.nombre", is(m.getNombre())));
//
//
//
//        //REMOVE DOCTOR
//        this.mockMvc.perform(delete("/medico/{id}", m.getId())
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(m)));
//
//        //CHECK THAT IS REMOVED
//        this.mockMvc.perform(get("/medico/{id}", m.getId()))
//                .andExpect(status().isNotFound());
//    }
}






