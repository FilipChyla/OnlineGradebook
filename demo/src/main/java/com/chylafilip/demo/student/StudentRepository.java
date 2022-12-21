package com.chylafilip.demo.student;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    default UUID generateID() {
        return UUID.randomUUID();
    }

    @Override
    void deleteById(UUID uuid);
}
