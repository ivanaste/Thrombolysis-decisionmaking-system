package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Odluka;

import java.util.List;
import java.util.UUID;

import com.ftn.sbnz.model.models.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdlukaOTromboliziRepository extends JpaRepository<Odluka, UUID> {
    Odluka findOdlukaById(UUID id);
}
