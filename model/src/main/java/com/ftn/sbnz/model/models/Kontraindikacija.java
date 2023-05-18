package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Kontraindikacija {

    private VrstaKontraindikacije vrsta;
    private LocalDate datum;
}
