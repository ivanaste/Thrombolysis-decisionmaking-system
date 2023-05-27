package com.ftn.sbnz.service.services.korisnik;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Role;
import com.ftn.sbnz.service.repository.KorisnikRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KorisnikService {

	private final KorisnikRepository korisnikRepository;

	@Transactional
	public Pacijent getPacijentByJmbg(String jmbg) {
		return korisnikRepository.getPacijentByJmbg(jmbg);
	}

	@Transactional
	public Pacijent sacuvajPacijenta(String jmbg, LocalDate datumRodjenja) {
		Pacijent pacijent = korisnikRepository.getPacijentByJmbg(jmbg);
		if (Objects.isNull(pacijent)) {
			pacijent = Pacijent.builder().jmbg(jmbg).datumRodjenja(datumRodjenja).role(Role.PATIENT).build();
			return korisnikRepository.save(pacijent);
		}
		return pacijent;
	}
}
