package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.UUID;

@Role(Role.Type.EVENT)
@Expires("6d")
@Getter
@Setter
public class ProcenaRizikaOdMUEvent {
    private UUID idProceneRizika;
    private NivoRizikaOdMU nivoRizika;
    private int stenozaKrvnogSuda;
    private String jmbgPacijenta;

    public ProcenaRizikaOdMUEvent(UUID idProceneRizika, String jmbgPacijenta, NivoRizikaOdMU nivoRizika, Integer stenozaKrvnogSuda) {
        this.idProceneRizika = idProceneRizika;
        this.jmbgPacijenta = jmbgPacijenta;
        this.nivoRizika = nivoRizika;
        this.stenozaKrvnogSuda = stenozaKrvnogSuda;
    }

    public ProcenaRizikaOdMUEvent(UUID idProceneRizika, String jmbgPacijenta, NivoRizikaOdMU nivoRizika) {
        this.idProceneRizika = idProceneRizika;
        this.jmbgPacijenta = jmbgPacijenta;
        this.nivoRizika = nivoRizika;
    }
}
