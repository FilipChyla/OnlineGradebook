package com.chylafilip.demo.grade;

import com.chylafilip.demo.student.Student;
import com.chylafilip.demo.student.StudentService;
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

    public void addGrade(UUID studentId, int gradeValue, String description) throws IllegalArgumentException{
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
}
