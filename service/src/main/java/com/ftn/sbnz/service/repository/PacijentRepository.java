package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, UUID>, JpaSpecificationExecutor<Pacijent> {

    Pacijent getPacijentByJmbg(String jmbg);
	
}
