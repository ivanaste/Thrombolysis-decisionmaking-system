package com.ftn.sbnz.model.events;

import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.UUID;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("1m")
@Getter
@Setter
public class OtkucajSrcaEvent {
    private Date executionTime;

    private String jmbgPacijenta;

    private Integer rrInterval;
    private UUID pZubac;
    private UUID rZubac;

    public OtkucajSrcaEvent(String jmbgPacijenta, UUID rZubac) {
        this.executionTime = new Date();
        this.jmbgPacijenta = jmbgPacijenta;
        this.rZubac = rZubac;
    }

    public OtkucajSrcaEvent(String jmbgPacijenta, UUID rZubac, UUID pZubac) {
        this.executionTime = new Date();
        this.jmbgPacijenta = jmbgPacijenta;
        this.rZubac = rZubac;
        this.pZubac = pZubac;
    }

    public OtkucajSrcaEvent(String jmbgPacijenta) {
        this.executionTime = new Date();
        this.jmbgPacijenta = jmbgPacijenta;
    }

    public OtkucajSrcaEvent(String jmbgPacijenta, Integer rrInterval) {
        this(jmbgPacijenta);
        this.rrInterval = rrInterval;
    }

}
