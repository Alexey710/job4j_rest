package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;
import ru.job4j.auth.repository.PersonRepository;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Autowired
    private RestTemplate rest;

    private final EmployeeRepository employees;

    public EmployeeController(final EmployeeRepository employees) {
        this.employees = employees;
    }

    @GetMapping("/account")
    public List<Person> findAllAccounts() {
        return rest.exchange(
                API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() { }
        ).getBody();
    }

    @PostMapping("/account")
    public ResponseEntity<Person> createAccount(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/account")
    public ResponseEntity<Void> updateAccount(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(
                employees.save(employee),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/")
    public List<Employee> findAll() {
            return StreamSupport.stream(
                    employees.findAll().spliterator(), false
            ).collect(Collectors.toList());
    }
}
