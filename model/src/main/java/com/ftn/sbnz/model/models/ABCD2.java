package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Role(Role.Type.EVENT)
@Expires("1m")
public class ABCD2 {
	private Integer skor;
	private Integer godinePacijenta;
	private boolean uProcesu;
	private UUID idProcene;
	private Pritisak pritisak;
	private boolean hemipareza;
	private boolean hemiplegija;
	private boolean smetnjeGovora;
	private Integer trajanjeSimptoma;
	private boolean dijabetes;
	private Integer nivoRacunanja;

	public ABCD2(UUID idProcene, Integer godinePacijenta, Pritisak pritisak, boolean hemipareza, boolean hemiplegija, boolean smetnjeGovora, Integer trajanjeSimptoma, boolean dijabetes) {
		this.idProcene = idProcene;
		this.godinePacijenta = godinePacijenta;
		this.pritisak = pritisak;
		this.hemipareza = hemipareza;
		this.hemiplegija = hemiplegija;
		this.smetnjeGovora = smetnjeGovora;
		this.trajanjeSimptoma = trajanjeSimptoma;
		this.dijabetes = dijabetes;
		this.skor = 0;
		this.nivoRacunanja = 0;
		this.uProcesu = true;
	}
}
