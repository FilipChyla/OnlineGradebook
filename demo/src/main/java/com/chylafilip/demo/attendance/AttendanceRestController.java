package com.chylafilip.demo.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AttendanceRestController {
    private final AttendanceService attendanceService;

    public AttendanceRestController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("students/attendance/{studentId}")
    List<Attendance> getAttendance(@PathVariable String studentId) {
        return attendanceService.getAttendance(UUID.fromString(studentId));
    }

    @PostMapping("students/attendance")
    ResponseEntity<String> addAttendance(@RequestBody AttendancesDTO attendancesDTO) {
        attendanceService.addAttendances(   attendancesDTO.attendanceList.stream().map((attendance) -> UUID.fromString(attendance.studentId)).collect(Collectors.toList()),
                                            attendancesDTO.attendanceList.stream().map((attendance) -> attendance.wasPresent).collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("students/attendance/{attendanceId}")
    ResponseEntity<String> editAttendance(@PathVariable String attendanceId, @RequestBody AttendanceDTO attendanceDTO) {
        attendanceService.editAttendance(UUID.fromString(attendanceId), AttendanceConverter.attendanceDTOToAttendance(attendanceDTO));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("students/attendance/{attendanceId}")
    ResponseEntity<String> deleteAttendance(@PathVariable String attendanceId) {
        attendanceService.deleteAttendance(UUID.fromString(attendanceId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Data
    private static class AttendancesDTO {
        private List<AttendanceDTO> attendanceList;
    }

    @Data
    private static class AttendanceDTO {
        private String studentId;
        private boolean wasPresent;
        private String date;
    }

    private static class AttendanceConverter {
        static Attendance attendanceDTOToAttendance(AttendanceDTO attendanceDTO) {
            Attendance attendance = new Attendance();
            attendance.setWasPresent(attendanceDTO.wasPresent);
            attendance.setDate(LocalDate.parse(attendanceDTO.date));

            return attendance;
        }
    }
}
