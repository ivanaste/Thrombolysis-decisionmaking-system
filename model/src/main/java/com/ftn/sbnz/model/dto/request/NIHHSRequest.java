package com.ftn.sbnz.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NIHHSRequest {

	public String jmbgPacijenta;
	public Integer stanjeSvesti;
	public Integer stanjeSvestiPitanja;
	private Integer stanjeSvestiNalozi;
	private Integer pokretiBulbusa;
	private Integer sirinaVidnogPolja;
	private Integer mimicnaPareza;
	private Integer motorikaLevaRuka;
	private Integer motorikaDesnaRuka;
	private Integer motorikaLevaNoga;
	private Integer motorikaDesnaNoga;
	private Integer ataksijaEkstremiteta;
	private Integer senzibilitet;
	private Integer govor;
	private Integer dizartrija;
	private Integer fenomenNeglekta;

	public NIHHSRequest() {}

	public int calculateSumOfProperties() {
		int sum = 0;
		sum += stanjeSvesti;
		sum += stanjeSvestiPitanja;
		sum += stanjeSvestiNalozi;
		sum += pokretiBulbusa;
		sum += sirinaVidnogPolja;
		sum += mimicnaPareza;
		sum += motorikaLevaRuka;
		sum += motorikaDesnaRuka;
		sum += motorikaLevaNoga;
		sum += motorikaDesnaNoga;
		sum += ataksijaEkstremiteta;
		sum += senzibilitet;
		sum += govor;
		sum += dizartrija;
		sum += fenomenNeglekta;

		return sum;
	}
}
