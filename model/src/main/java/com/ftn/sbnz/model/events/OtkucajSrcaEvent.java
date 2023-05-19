package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.RRInterval;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("1m")
@Getter
@Setter
public class OtkucajSrcaEvent {
    private Date executionTime;

    private String jmbgPacijenta;

    private RRInterval rrInterval;

    public OtkucajSrcaEvent(String jmbgPacijenta) {
        this.executionTime = new Date();
        this.jmbgPacijenta = jmbgPacijenta;
    }

    public OtkucajSrcaEvent(String jmbgPacijenta, RRInterval rrInterval) {
        this(jmbgPacijenta);
        this.rrInterval = rrInterval;
    }

}
