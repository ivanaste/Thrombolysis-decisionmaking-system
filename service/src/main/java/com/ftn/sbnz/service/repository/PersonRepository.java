package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person getByEmail(String email);
}
