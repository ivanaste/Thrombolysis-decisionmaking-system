package com.ftn.sbnz.model.dto.request;

import com.ftn.sbnz.model.models.StanjeSvesti;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NastanakSimptomaRequest {

	private String jmbgPacijenta;

	private LocalDate datumRodjenjaPacijenta;

	private LocalDateTime trenutakNastanka;

	private StanjeSvesti stanjeSvesti;

	private boolean nastaliUTokuSna;

	private boolean postojeSvedoci;
}
