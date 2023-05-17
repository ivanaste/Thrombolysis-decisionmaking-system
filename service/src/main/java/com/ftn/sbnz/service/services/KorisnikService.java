package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.service.repository.KorisnikRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KorisnikService {

	private final KorisnikRepository korisnikRepository;

	@Transactional
	public Pacijent getPacijentByJmbg(String jmbg) {
		return korisnikRepository.getPacijentByJmbg(jmbg);
	}
}
