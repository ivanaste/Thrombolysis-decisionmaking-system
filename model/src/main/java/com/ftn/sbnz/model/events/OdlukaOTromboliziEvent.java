package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.*;

import java.time.LocalDate;
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

	private StatusOdluke statusOdluke;

	private Simptomi simptomi;

	private boolean postojeSvedoci;

	private Monitoring monitoring;

	private List<ZnakIshemije> znaciIshemije;

	private Laboratorija laboratorija;

	private List<Kontraindikacija> kontraindikacije;

	public OdlukaOTromboliziEvent() {
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final StatusOdluke statusOdluke) {
		this.idOdluke = idOdluke;
		this.statusOdluke = statusOdluke;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final StatusOdluke statusOdluke, final Simptomi simptomi,
		final boolean postojeSvedoci) {
		this(idOdluke, statusOdluke);
		this.simptomi = simptomi;
		this.postojeSvedoci = postojeSvedoci;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final String jmbgPacijenta, final StatusOdluke statusOdluke, final List<ZnakIshemije> znaciIshemije) {
		this(idOdluke, statusOdluke);
		this.znaciIshemije = znaciIshemije;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final StatusOdluke statusOdluke, final Laboratorija laboratorija) {
		this(idOdluke, statusOdluke);
		this.laboratorija = laboratorija;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final StatusOdluke statusOdluke, final List<Kontraindikacija> kontraindikacije) {
		this(idOdluke, statusOdluke);
		this.kontraindikacije = kontraindikacije;
	}

	public OdlukaOTromboliziEvent(final UUID idOdluke, final StatusOdluke statusOdluke, final Monitoring monitoring) {
		this(idOdluke, statusOdluke);
		this.monitoring = monitoring;
	}
}
