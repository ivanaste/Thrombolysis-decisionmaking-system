package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Simptomi {

	private LocalDateTime trenutakNastanka;

	private StanjeSvesti stanjeSvesti;

	private boolean nastaliUTokuSna;

}
