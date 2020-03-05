//package com.lab2webservices.lab2webservices;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.CoreMatchers.*;
//
//
////https://stackabuse.com/spring-annotations-testing/
////https://github.com/spring-projects/spring-hateoas-examples/tree/master/simplified
//
////https://stackoverflow.com/questions/44364115/import-vs-contextconfiguration-for-importing-beans-in-unit-tests
//
////This is a form of Unit testing for spring?
//@WebMvcTest(PhoneController.class)
//@Import({PhoneDataModelAssembler.class})
////@AutoConfigureRestDocs(outputDir = "target/snippets")
//public class PhoneControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    PhoneRepository repository;
//
//    @BeforeEach
//    void setUp() {
//        when(repository.findAll()).thenReturn(List.of(new Phone(1L, "Iphone X", 1), new Phone(2L, "Samsung Galaxy S10", 2)));
//        when(repository.findById(1L)).thenReturn(Optional.of(new Phone(1L, "Iphone X", 1)));
//        when(repository.save(any(Phone.class))).thenAnswer(invocationOnMock -> {
//            Object[] args = invocationOnMock.getArguments();
//            var p = (Phone) args[0];
//            return new Phone(1L, p.getPhoneName(), p.getBrandId());
//        });
//
//    }
//
//    @Test
//    void getAllReturnsListOfAllPersons() throws Exception {
//        mockMvc.perform(
//                get("/api/phones").contentType("application/hal+json"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("_embedded.phoneList[0]._links.self.href", is("http://localhost/api/v1/phones/1")))
//                .andExpect(jsonPath("_embedded.phoneList[0].phoneName", is("Iphone X")));
//        //Build json paths with: https://jsonpath.com/
//    }
//
//    @Test
//    @DisplayName("Calls Get method with url /api/persons/1")
//    void getOnePersonWithValidIdOne() throws Exception {
//        mockMvc.perform(
//                get("/api/phones/1").accept("application/hal+json"))
//                .andExpect(status().isOk())
//                //.andExpect(jsonPath("content[0].links[2].rel", is("self")))
//                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/phones/1")));
//    }
//
//    @Test
//    @DisplayName("Calls Get method with invalid id url /api/v1/phones/3")
//    void getOnePersonWithInValidIdThree() throws Exception {
//        mockMvc.perform(
//                get("/api/v1/phones/3").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void addNewPersonWithPostReturnsCreatedPerson() throws Exception {
//        mockMvc.perform(
//                post("/api/v1/phones/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\":1,\"phoneName\":\"Iphone X,\" \"brandId\":1}"))
//                .andExpect(status().isCreated());
//    }
//
//
//}