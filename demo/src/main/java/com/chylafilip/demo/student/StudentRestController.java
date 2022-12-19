package com.chylafilip.demo.student;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostConstruct
    public void init() {
        studentService.addStudent("Fifi");
        studentService.addStudent("Maciejka");
        studentService.addStudent("Cinek");

//        studentService.getStudents().forEach(student ->
//        {
//            attendanceService.addAttendance(student.getId(), true);
//            attendanceService.addAttendance(student.getId(), false);
//            gradeService.addGrade(student.getId(), 5, "Test1");
//            gradeService.addGrade(student.getId(), 2, "Test2");
//        });
    }

    @GetMapping("/students")
    List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/students/{studentId}")
    Student getStudent(@PathVariable String studentId){
        return studentService.getStudent(UUID.fromString(studentId));
    }

    @PostMapping("/students")
    ResponseEntity<String> addStudent(@RequestBody StudentDTO studentDTO) {
        String id = studentService.addStudent(studentDTO.name);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Data
    private static class StudentDTO {
        String name;
//        List<Grade> grades;
//        List<Attendance> attendanceList;
    }
}
