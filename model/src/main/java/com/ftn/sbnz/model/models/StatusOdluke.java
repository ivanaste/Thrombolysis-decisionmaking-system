package com.ftn.sbnz.model.models;

import lombok.Getter;

@Getter
public enum StatusOdluke {

	PRIHVACENA ("prihvacena"),
	ODBIJENA ("odbijena"),
	PRIHVACENA_FAZA_1 ("prihvacena na osnovu trenutka nastanka"),
	PRIHVACENA_FAZA_2 ("prihvacena na osnovu anamneze"),
	PRIHVACENA_FAZA_3 ("prihvacena na osnovu neuroloskog pregleda"),
	PRIHVACENA_FAZA_4 ("prihvacena na osnovu NIHHS skora"),
	PRIHVACENA_FAZA_5 ("prihvacena na osnovu CTa glave"),
	PRIHVACENA_FAZA_6 ("prihvacena na osnovu laboratorije krvi");

	private String opis;

	StatusOdluke(String opis) {
		this.opis = opis;
	}

}
