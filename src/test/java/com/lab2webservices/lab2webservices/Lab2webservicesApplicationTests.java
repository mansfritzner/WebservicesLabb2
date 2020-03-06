package com.lab2webservices.lab2webservices;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RacketController.class)
@Slf4j
@Import({RacketDataModelAssembler.class})
class Lab2webservicesApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RacketRepository repository;

    @BeforeEach
    void setUpNewTest() {
        when(repository.findAll()).thenReturn(List.of(new Racket(1L, "Tennis", 1),
                new Racket(2L, "Badminton", 2)));
        when(repository.findById(1L)).thenReturn(Optional.of(new Racket(1L, "Tennis", 1)));
        when(repository.findByRacketName("Tennis")).thenReturn(Optional.of(new Racket(1L, "Tennis",1)));
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Racket.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            var p = (Racket) args[0];
            return new Racket(1L, p.getRacketName(), p.getBrandId());
        });
    }

    @Test
    void getAllReturnsListOfAllRackets() throws Exception {
        mockMvc.perform(
                get("/api/v1/rackets").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.racketList[0]._links.self.href", is("http://localhost/api/v1/rackets/1")))
                .andExpect(jsonPath("_embedded.racketList[0].racketName", is("Tennis")));
    }

    @Test
    @DisplayName("Calls Get method with url /api/v1/rackets/1")
    void getOneRacketWithValidIdOne() throws Exception {
        mockMvc.perform(
                get("/api/v1/rackets/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/rackets/1")));
    }

    @Test
    @DisplayName("Calls Get method with invalid id url /api/v1/rackets/3")
    void getOneRacketWithInValidIdThree() throws Exception {
        mockMvc.perform(
                get("/api/rackets/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewRacketWithPostReturnsCreatedRacket() throws Exception {
        mockMvc.perform(
                post("/api/v1/rackets/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"name\":\"Tennis\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Delete racket with ID in url")
    void deleteUserInRepository() throws Exception {
        mockMvc.perform(delete("/api/v1/rackets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Trying to delete racket with invalid ID")
    void deleteUserWithInvalidID() throws Exception {
        mockMvc.perform(delete("/api/v1/rackets/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Put with complete data")
    void putUserWithCompleteDataWithId1() throws Exception {
        mockMvc.perform(put("/api/v1/rackets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"racketName\":\"Tennis\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/rackets/1")))
                .andExpect(jsonPath("racketName", is("Tennis")));
    }

    @Test
    @DisplayName("Put with incomplete data, should return null on missing content")
    void putRacketWithIncompleteData() throws Exception {
        mockMvc.perform(put("/api/v1/rackets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"racketName\":\"Tennis\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/rackets/1")))
                .andExpect(jsonPath("racketName", is("Tennis")))
                .andExpect(jsonPath("realName").doesNotExist());
    }

    @Test
    @DisplayName("Patch racket with new complete data")
    void patchRacketWithAllData() throws Exception {
        mockMvc.perform(patch("/api/v1/rackets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"racketName\":\"Tennis 1\",\"brandId\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/rackets/1")))
                .andExpect(jsonPath("racketName", is("Tennis 1")));
    }

    @Test
    @DisplayName("Patch with only racketname and expect other values to remain unchanged")
    void patchRacketWithNewRacketName() throws Exception {
        mockMvc.perform(patch("/api/v1/rackets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"racketName\":\"Tennis 2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/rackets/1")))
                .andExpect(jsonPath("racketName", is("Tennis 2")))
                .andExpect(jsonPath("brandId", is(1)));
    }
}