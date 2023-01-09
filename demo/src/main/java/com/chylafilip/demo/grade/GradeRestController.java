package com.chylafilip.demo.grade;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PostMapping("students/grades")
    ResponseEntity<String> addGrades(@RequestBody GradesDTO gradesDTO) {
        gradeService.addGrades( gradesDTO.description,
                                gradesDTO.grades.stream().map((grade -> UUID.fromString(grade.studentId))).collect(Collectors.toList()),
                                gradesDTO.grades.stream().map((grade -> grade.gradeValue)).collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("students/grades/{gradeId}")
    ResponseEntity<String> editGrade(@RequestBody GradeDTO gradeDTO, @PathVariable String gradeId) {
        gradeService.editGrade(UUID.fromString(gradeId), GradeConverter.gradeDTOToGrade(gradeDTO));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("students/grades/{gradeId}")
    ResponseEntity<String> deleteGrade(@PathVariable String gradeId) {
        gradeService.deleteGrade(UUID.fromString(gradeId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Data
    private static class GradesDTO {
        private String description;
        private List<GradeDTO> grades;
    }

    @Data
    private static class GradeDTO {
        private String studentId;
        private int gradeValue;
        private String description;
        private String date;
    }

    private static class GradeConverter {
        static Grade gradeDTOToGrade(GradeDTO gradeDTO) {
            Grade grade = new Grade();
            grade.setGrade(gradeDTO.gradeValue);
            grade.setDescription(gradeDTO.description);
            grade.setDate(LocalDate.parse(gradeDTO.date));
            return grade;
        }
    }
}
