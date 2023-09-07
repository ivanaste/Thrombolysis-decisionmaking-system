package com.ftn.sbnz.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "pacijent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pacijent extends Korisnik {

    private String jmbg;
    private LocalDate datumRodjenja;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
    @JsonIgnore
    private List<Odluka> odlukeOTrombolizi;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
    @JsonIgnore
    private List<BolestPacijenta> bolesti;

    public Pacijent(String email, @NotNull String password, String name, String surname, Role role, String jmbg, LocalDate datumRodjenja) {
        super(email, password, name, surname, role);
        this.jmbg = jmbg;
        this.datumRodjenja = datumRodjenja;
    }

    public Integer dobaviGodine() {
        return Period.between(this.datumRodjenja, LocalDate.now()).getYears();
    }
}
