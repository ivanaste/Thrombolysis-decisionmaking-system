package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.Pacijent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepository extends JpaRepository<Pacijent, UUID>, JpaSpecificationExecutor<Pacijent> {

	Pacijent getPacijentByJmbg(String jmbg);
}
