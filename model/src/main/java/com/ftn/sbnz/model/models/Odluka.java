package com.ftn.sbnz.model.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "odluka")
@Entity
@Builder
public class Odluka extends BaseEntity {

	@ManyToOne
	Pacijent pacijent;

	@Enumerated(EnumType.STRING)
	StatusOdluke status = StatusOdluke.PRIHVACENA_FAZA_1;

}
