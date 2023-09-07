package com.ftn.sbnz.model.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@SuperBuilder
@FieldNameConstants
public class Korisnik extends BaseEntity {
    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false, length = 60)
    @NotNull
    String password;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Enumerated(EnumType.STRING)
    Role role;
}
