package com.ftn.sbnz.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "pacijent")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pacijent extends BaseEntity {

    private String jmbg;
    private LocalDate datumRodjenja;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
    @JsonIgnore
    private List<Odluka> odlukeOTrombolizi;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
    @JsonIgnore
    private List<BolestPacijenta> bolesti;

    public Integer dobaviGodine() {
        return Period.between(this.datumRodjenja, LocalDate.now()).getYears();
    }
}
