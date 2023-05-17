package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "simptomi")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Simptomi extends BaseEntity {

	private LocalDateTime trenutakNastanka;

	private StanjeSvesti stanjeSvesti;

	private boolean nastaliUTokuSna;

}
