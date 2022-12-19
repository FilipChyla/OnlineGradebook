package com.chylafilip.demo.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String addStudent(String studentName) {
        Student student = new Student(studentRepository.generateID(), studentName, new ArrayList<>(), new ArrayList<>());

        studentRepository.saveAndFlush(student);
        log.info("Created student " + student.getName());

        return student.getId().toString();
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public void updateStudent(Student student) {
        studentRepository.saveAndFlush(student);
    }
}
