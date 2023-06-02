package com.ftn.sbnz.model.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NIHHS {

	private Integer skor;

	private String jmbgPacijenta;

	private UUID idOdluke;
}
