package com.ftn.sbnz.model.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "procena_rizika_od_mu")
@Entity
@Builder
public class ProcenaRizikaOdMU extends BaseEntity {

	@ManyToOne
	private Pacijent pacijent;

	@Enumerated(EnumType.STRING)
	private NivoRizikaOdMU nivoRizika = NivoRizikaOdMU.PROCENA_U_TOKU;

}
