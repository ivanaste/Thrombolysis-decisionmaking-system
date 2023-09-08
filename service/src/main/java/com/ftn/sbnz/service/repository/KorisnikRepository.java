package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, UUID> {
    Korisnik findByEmail(String email);
}
