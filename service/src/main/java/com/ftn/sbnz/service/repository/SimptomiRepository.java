package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.Simptomi;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimptomiRepository extends JpaRepository<Simptomi, UUID> {
}
