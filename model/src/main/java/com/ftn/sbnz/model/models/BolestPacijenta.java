package com.ftn.sbnz.model.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "bolesti")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BolestPacijenta extends BaseEntity {
    @ManyToOne
    Pacijent pacijent;

    @Enumerated(EnumType.STRING)
    VrstaBolesti vrstaBolesti;
}
