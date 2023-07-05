package com.ftn.sbnz.service.services.bolesti;

import com.ftn.sbnz.model.models.BolestPacijenta;
import com.ftn.sbnz.model.models.Kontraindikacija;
import com.ftn.sbnz.service.repository.BolestiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BolestiService {

	private final BolestiRepository bolestiRepository;

	@Transactional
	public List<BolestPacijenta> dobaviSveBolestiPacijenta(String jmbgPacijenta) {
		return bolestiRepository.findAllByPacijent_Jmbg(jmbgPacijenta);
	}

	public List<Kontraindikacija> kreirajKontraindikacijeOdPrethodnihBolestiPacijenta(String jmbgPacijenta) {
		List<BolestPacijenta> bolestiPacijenta = dobaviSveBolestiPacijenta(jmbgPacijenta);
		return bolestiPacijenta.stream()
				.map(bolest -> new Kontraindikacija(bolest.getVrstaBolesti(), bolest.getDatumDesavanja()))
				.collect(Collectors.toList());
	}

}
