package com.chylafilip.demo.grade;

import com.chylafilip.demo.student.Student;
import com.chylafilip.demo.student.StudentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentService studentService;

    public GradeService(GradeRepository gradeRepository, StudentService studentService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
    }

    public List<Grade> getGrades(UUID id) {
        return studentService.getStudent(id).getGrades();
    }

    public void addGrade(UUID studentId, int gradeValue, String description) throws IllegalArgumentException {
        if (gradeValue < 2 || gradeValue > 5) {
            throw new IllegalArgumentException();
        }

        Grade grade = new Grade(gradeRepository.generateID(), gradeValue, description, LocalDate.now());
        gradeRepository.saveAndFlush(grade);

        Student student = studentService.getStudent(studentId);
        student.getGrades().add(grade);
        studentService.updateStudent(student);

        log.info("Added grade: " + grade + " to student: " + student);

    }

    public void addGrades(String description, List<String> studentsId, List<Integer> gradeValues) {
        studentsId.forEach((studentId) -> addGrade(UUID.fromString(studentId), gradeValues.get(studentsId.indexOf(studentId)), description));
    }

    public void deleteGrade(UUID gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    public void editGrade(UUID gradeId, Grade grade) {
        Grade gradeToUpdate = gradeRepository.getOne(gradeId);
        gradeToUpdate.setDate(grade.getDate());
        gradeToUpdate.setGrade(grade.getGrade());
        gradeToUpdate.setDescription(grade.getDescription());
        gradeRepository.saveAndFlush(gradeToUpdate);
    }
}
