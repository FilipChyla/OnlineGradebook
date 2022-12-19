package com.chylafilip.demo.grade;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "grades")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @ToString.Exclude
    private UUID id;
    private int grade;
    private String description;
    private LocalDate date;
}
