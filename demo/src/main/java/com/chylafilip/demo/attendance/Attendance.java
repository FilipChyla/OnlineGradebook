package com.chylafilip.demo.attendance;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @ToString.Exclude
    private UUID id;
    private boolean wasPresent;
    private LocalDate date;
}
