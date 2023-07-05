package com.ftn.sbnz.model.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NeuroloskiPregledTemplateModel {
    private VrstaBolesti vrstaBolesti;
    private String jedinicaVremena;
    private Integer vrednost;


    public NeuroloskiPregledTemplateModel(VrstaBolesti vrstaBolesti, String jedinicaVremena, Integer vrednost) {
        this.vrstaBolesti = vrstaBolesti;
        this.jedinicaVremena = jedinicaVremena;
        this.vrednost = vrednost;
    }
}
