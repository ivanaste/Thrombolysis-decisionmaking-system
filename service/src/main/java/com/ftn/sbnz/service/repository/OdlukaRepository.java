package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.StatusOdluke;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdlukaRepository extends JpaRepository<Odluka, UUID> {

	Optional<Odluka> getOdlukaByPacijentAndStatusNotIn(Pacijent pacijent, List<StatusOdluke> statusi);

	Odluka getFirstByPacijent_JmbgOrderByCreatedAtDesc(String jmbg);
}
