package com.ftn.sbnz.model.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtkucajiSrcaTemplateModel {
    private Integer minBrojOtkucajaSrca;
    private Integer maxBrojOtkucajaSrca;
    private RadSrca radSrca;

    public OtkucajiSrcaTemplateModel(Integer minBrojOtkucajaSrca, Integer maxBrojOtkucajaSrca, RadSrca radSrca) {
        this.minBrojOtkucajaSrca = minBrojOtkucajaSrca;
        this.maxBrojOtkucajaSrca = maxBrojOtkucajaSrca;
        this.radSrca = radSrca;
    }

}
