package com.chylafilip.demo.grade;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class GradeRestController {
    private final GradeService gradeService;

    public GradeRestController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("students/{studentId}/grades")
    List<Grade> getGrades(@PathVariable String studentId) {
        return gradeService.getGrades(UUID.fromString(studentId));
    }

//    @PostMapping("students/grades")
//    ResponseEntity<String> addGrade(@RequestBody GradesDTO gradesDTO) {
//        gradesDTO.grades.forEach((studentId, gradeValue) -> gradeService.addGrade(UUID.fromString(studentId), gradeValue, gradesDTO.description));
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("students/grades")
    ResponseEntity<String> addGrade(@RequestBody GradesDTO gradesDTO) {
        try {
            gradesDTO.grades.forEach((studentId, gradeValue) -> gradeService.addGrade(UUID.fromString(studentId), gradeValue, gradesDTO.description));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Data
    private static class GradesDTO {
        private Map<String, Integer> grades = new HashMap<>();
        private String description;
    }
}
