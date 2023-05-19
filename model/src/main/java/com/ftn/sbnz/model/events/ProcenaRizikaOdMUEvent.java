package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.UUID;

@Role(Role.Type.EVENT)
@Expires("30m")
@Getter
@Setter
public class ProcenaRizikaOdMUEvent {
    private UUID idProceneRizika;
    private NivoRizikaOdMU nivoRizika;
    private Integer ABCD2Skor;
    private Integer stenozaKrvnogSuda;

    public ProcenaRizikaOdMUEvent(UUID idProceneRizika, NivoRizikaOdMU nivoRizika, Integer ABCD2Skor, Integer stenozaKrvnogSuda) {
        this.idProceneRizika = idProceneRizika;
        this.nivoRizika = nivoRizika;
        this.ABCD2Skor = ABCD2Skor;
        this.stenozaKrvnogSuda = stenozaKrvnogSuda;
    }
}
