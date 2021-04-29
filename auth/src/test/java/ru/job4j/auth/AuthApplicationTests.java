package ru.job4j.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import java.util.Arrays;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class AuthApplicationTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void delete_whenDelete_thenStatus200() throws Exception {
        Person p1 = Person.of(1, "Jane", "123");
        mockMvc.perform(
                delete("http://localhost:8080/person/{id}", p1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void update_whenUpdate_thenStatus200() throws Exception {
        Person p1 = Person.of(1, "Jane", "123");
        mockMvc.perform(
                put("http://localhost:8080/person/")
                        .content(objectMapper.writeValueAsString(p1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_whenGetPerson_thenStatus200andPersonReturned() throws Exception {
        Person p1 = Person.of(1, "Jane", "123");
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(p1));
        p1 = Optional.of(p1).get();
        mockMvc.perform(
                get("http://localhost:8080/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(p1)));
    }

    @Test
    public void findAll_whenGetPerson_thenStatus200() throws Exception {
        Person p1 = Person.of(1, "Jane", "1");
        Person p2 = Person.of(2, "Jack", "2");
        Mockito.when(personRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        mockMvc.perform(
                get("http://localhost:8080/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(p1, p2))));
    }

    @Test
    public void create_whenPostPerson_thenStatus201() throws Exception {
        Person person = Person.of(1, "Michail", "123");
        String response = mockMvc.perform(
                post("http://localhost:8080/person/")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

}
