package com.example.security.controllers;

import com.example.security.models.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/students")
@EnableMethodSecurity
public class StudentRestController {

    private List<Student> students = Stream.of(
            new Student(1L, "Alex","7A"),
            new Student(2L, "Petr","7A"),
            new Student(3L, "Karl","7B")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Student> getAll(){
        return students;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Student getById(@PathVariable Long id){
        return students.stream().filter(el->el.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Student create(@RequestBody Student student){
        students.add(student);
        return student;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id){
        students.removeIf(student -> student.getId().equals(id));
    }

}
