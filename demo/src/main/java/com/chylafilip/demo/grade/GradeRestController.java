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
//        try {
//            gradesDTO.grades.forEach((studentId, gradeValue) -> gradeService.addGrade(UUID.fromString(studentId), gradeValue, gradesDTO.description));
//        }catch (IllegalArgumentException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("students/grades")
    ResponseEntity<String> addGrade(@RequestBody Map<String, GradeDTO> grades) {
        try {
            grades.forEach((studentId, grade) -> gradeService.addGrade(UUID.fromString(studentId), grade.gradeValue, "tmp"));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("students/grades/{gradesId}")
    ResponseEntity<String> deleteGrade(){

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Data
    private static class GradeDTO {
        private int gradeValue;
    }
}
