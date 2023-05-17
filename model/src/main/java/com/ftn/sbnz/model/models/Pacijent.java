package com.ftn.sbnz.model.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pacijent")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pacijent extends BaseEntity {

	private String jmbg;
	private String ime;
	private String prezime;
	private String brojTelefona;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
	private List<Odluka> odlukeOTrombolizi;

}
