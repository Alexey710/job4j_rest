package ru.job4j.auth.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;
    
    private String tin;

    private Timestamp created;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Person> accounts = new ArrayList<>();

    public static Employee of(
            int id, String name, String surname,
            String tin, Timestamp created,
            List<Person> accounts) {
        Employee employee = new Employee();
        employee.id = id;
        employee.name = name;
        employee.surname = surname;
        employee.tin = tin;
        employee.created = created;
        employee.accounts = accounts;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = ( Employee ) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", surname='" + surname + '\''
                + ", tin='" + tin + '\'' + ", created=" + created + ", accounts=" + accounts + '}';
    }
}
