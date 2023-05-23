package com.ftn.sbnz.model.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateModel {
    private Integer minSkor;
    private Integer maxSkor;
    private Integer stenozaKrvnogSuda;

    private NivoRizikaOdMU stariNivoRizika;

    private NivoRizikaOdMU noviNivoRizika;

    public TemplateModel(Integer minSkor, Integer maxSkor, NivoRizikaOdMU stariNivoRizika, NivoRizikaOdMU noviNivoRizika) {
        this.minSkor = minSkor;
        this.maxSkor = maxSkor;
        this.stariNivoRizika = stariNivoRizika;
        this.noviNivoRizika = noviNivoRizika;
    }

    public TemplateModel(Integer minSkor, Integer maxSkor, Integer stenozaKrvnogSuda, NivoRizikaOdMU stariNivoRizika, NivoRizikaOdMU noviNivoRizika) {
        this(minSkor, maxSkor, stariNivoRizika, noviNivoRizika);
        this.stenozaKrvnogSuda = stenozaKrvnogSuda;
    }

}
