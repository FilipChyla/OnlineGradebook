package com.chylafilip.demo.student;

import com.chylafilip.demo.attendance.Attendance;
import com.chylafilip.demo.grade.Grade;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    //TODO: ask whatever tf is this
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    private String name;
    @OneToMany
    private List<Grade> grades;
    @OneToMany
    private List<Attendance> attendanceList;
}