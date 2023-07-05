package com.ftn.sbnz.model.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AlarmEKG {
    private String jmbgPacijenta;
    private RadSrca radSrca;
    private boolean iregularanNIIHS;

    public AlarmEKG(String jmbgPacijenta, RadSrca radSrca) {
        this.jmbgPacijenta = jmbgPacijenta;
        this.radSrca = radSrca;
    }

    public AlarmEKG(String jmbgPacijenta, boolean iregularanNIIHS) {
        this.jmbgPacijenta = jmbgPacijenta;
        this.iregularanNIIHS = iregularanNIIHS;
    }
}
