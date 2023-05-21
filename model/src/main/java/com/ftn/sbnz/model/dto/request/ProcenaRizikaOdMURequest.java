package com.ftn.sbnz.model.dto.request;

import lombok.Getter;

@Getter
public class ProcenaRizikaOdMURequest {
	private String jmbgPacijenta;
	private boolean hemipareza;
	private boolean hemiplegija;
	private boolean smetnjeGovora;
	private Integer trajanjeSimptoma;
	private boolean dijabetes;
	private Integer stenozaSimptomatskogKrvnogSuda;
}
