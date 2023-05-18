package com.ftn.sbnz.model.dto.request;

import com.ftn.sbnz.model.models.Kontraindikacija;
import com.ftn.sbnz.model.models.VrstaKontraindikacije;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeuroloskiPregledRequest {
	private UUID idOdluke;
	private List<VrstaKontraindikacije> kontraindikacije;

	private List<LocalDate> datumiDesavanjaKontraindikacija;

	public List<Kontraindikacija> kreirajKontraindikacije() {
		List<Kontraindikacija> kontraindikacije = new ArrayList<>();
		for(int i = 0; i < this.kontraindikacije.size(); i++) {
			kontraindikacije.add(new Kontraindikacija(this.kontraindikacije.get(i), datumiDesavanjaKontraindikacija.get(i)));
		}
		return kontraindikacije;
	}
}
