package com.chylafilip.demo.attendance;

import com.chylafilip.demo.student.Student;
import com.chylafilip.demo.student.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.studentService = studentService;
    }

    public List<Attendance> getAttendance(UUID studentId) {
        return studentService.getStudent(studentId).getAttendanceList();
    }

    public void addAttendance(UUID studentId, Boolean attendanceValue) {
        Attendance attendance = new Attendance(UUID.randomUUID(), attendanceValue, LocalDate.now());
        attendanceRepository.saveAndFlush(attendance);

        Student student = studentService.getStudent(studentId);
        student.getAttendanceList().add(attendance);
        studentService.editStudent(studentId, student);
    }

    public void addAttendances(List<UUID> studentsId, List<Boolean> presenceList) {
        studentsId.forEach((studentId) -> addAttendance(studentId, presenceList.get(studentsId.indexOf(studentId))));
    }

    public void editAttendance(UUID attendanceId, Attendance attendanceInfo) {
        Attendance attendance = attendanceRepository.getOne(attendanceId);
        attendance.setWasPresent(attendanceInfo.isWasPresent());
    }

    public void deleteAttendance(UUID attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
}
