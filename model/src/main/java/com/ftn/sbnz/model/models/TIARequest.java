package com.ftn.sbnz.model.models;

import lombok.Getter;

@Getter
public class TIARequest {
	private String jmbgPacijenta;
	private boolean hemipareza;
	private boolean hemiplegija;
	private boolean smetnjeGovora;
	private Integer trajanjeSimptoma;
	private boolean dijabetes;
	private Integer stenozaSimptomatskogKrvnogSuda;
}
