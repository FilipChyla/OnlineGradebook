package com.chylafilip.demo.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AttendanceRestController {
    private final AttendanceService attendanceService;

    public AttendanceRestController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("students/attendance")
    ResponseEntity<String> addAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        attendanceDTO.attendanceList.forEach((studentId, attendance) -> attendanceService.addAttendance(UUID.fromString(studentId), attendance));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Data
    private static class AttendanceDTO {
        private Map<String, Boolean> attendanceList = new HashMap<>();
    }
}
