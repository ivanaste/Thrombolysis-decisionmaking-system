package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.StatusOdluke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OdlukaOTromboliziRepository extends JpaRepository<Odluka, UUID> {
    Odluka findOdlukaById(UUID id);

    List<Odluka> findAllByStatusInOrderByCreatedAtDesc(List<StatusOdluke> statusi);

}
