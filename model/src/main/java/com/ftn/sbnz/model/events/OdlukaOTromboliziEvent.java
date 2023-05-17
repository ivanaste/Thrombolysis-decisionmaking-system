package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.StatusOdluke;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Expires("30m")
@Getter
@Setter
@AllArgsConstructor
public class OdlukaOTromboliziEvent {

	private String jmbgPacijenta;

	private StatusOdluke statusOdluke;
}
