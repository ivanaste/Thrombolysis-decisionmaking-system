package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.Simptomi;
import com.ftn.sbnz.model.models.StatusOdluke;
import com.ftn.sbnz.model.models.ZnakIshemije;

import java.util.List;
import java.util.UUID;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.Getter;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Expires("30m")
@Getter
@Setter
public class OdlukaOTromboliziEvent {

	private UUID idOdluke;
	private String jmbgPacijenta;

	private StatusOdluke statusOdluke;

	private Simptomi simptomi;

	private boolean postojeSvedoci;

	private List<ZnakIshemije> znaciIshemije;

	public OdlukaOTromboliziEvent() {
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final String jmbgPacijenta, final StatusOdluke statusOdluke) {
		this.idOdluke = idOdluke;
		this.jmbgPacijenta = jmbgPacijenta;
		this.statusOdluke = statusOdluke;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final String jmbgPacijenta, final StatusOdluke statusOdluke, final Simptomi simptomi,
		final boolean postojeSvedoci) {
		this(idOdluke, jmbgPacijenta, statusOdluke);
		this.simptomi = simptomi;
		this.postojeSvedoci = postojeSvedoci;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final String jmbgPacijenta, final StatusOdluke statusOdluke, final List<ZnakIshemije> znaciIshemije) {
		this(idOdluke, jmbgPacijenta, statusOdluke);
		this.znaciIshemije = znaciIshemije;
	}
}
