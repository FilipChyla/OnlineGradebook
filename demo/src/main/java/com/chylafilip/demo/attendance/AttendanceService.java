package com.chylafilip.demo.attendance;

import com.chylafilip.demo.student.Student;
import com.chylafilip.demo.student.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.studentService = studentService;
    }


    public void addAttendance(UUID studentId, Boolean attendanceValue) {
        Attendance attendance = new Attendance(UUID.randomUUID(), attendanceValue, LocalDate.now());
        attendanceRepository.saveAndFlush(attendance);

        Student student = studentService.getStudent(studentId);
        student.getAttendanceList().add(attendance);
        studentService.editStudent(studentId, student);
    }
}
