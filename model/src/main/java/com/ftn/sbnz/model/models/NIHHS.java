package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("20m")
public class NIHHS {
	private Integer skor;
	private String jmbgPacijenta;
	private UUID idOdluke;
	private Date executionTime;
}
