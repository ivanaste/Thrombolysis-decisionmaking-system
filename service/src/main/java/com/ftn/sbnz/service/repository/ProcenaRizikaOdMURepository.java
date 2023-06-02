package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import com.ftn.sbnz.model.models.ProcenaRizikaOdMU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcenaRizikaOdMURepository extends JpaRepository<ProcenaRizikaOdMU, UUID> {
    List<ProcenaRizikaOdMU> findAllByNivoRizikaEquals(NivoRizikaOdMU nivoRizika);

    List<ProcenaRizikaOdMU> findAllByOrderByCreatedAtDesc();

}
