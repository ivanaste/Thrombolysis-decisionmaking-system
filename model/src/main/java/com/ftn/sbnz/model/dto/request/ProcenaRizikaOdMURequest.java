package com.ftn.sbnz.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcenaRizikaOdMURequest {
    private String jmbgPacijenta;
    private boolean hemipareza;
    private boolean hemiplegija;
    private boolean smetnjeGovora;
    private Integer trajanjeSimptoma;
    private boolean dijabetes;
    private Integer stenozaSimptomatskogKrvnogSuda;
    private LocalDate datumRodjenjaPacijenta;
}
