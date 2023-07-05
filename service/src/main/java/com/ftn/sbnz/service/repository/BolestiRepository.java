package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.BolestPacijenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BolestiRepository extends JpaRepository<BolestPacijenta, UUID> {

	List<BolestPacijenta> findAllByPacijent_Jmbg(String jmbgPacijenta);
}
