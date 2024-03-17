package com.petshop.auth.adapter.output.repository.database.profile;

import com.petshop.auth.application.domain.ProfileDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile", schema = "petshop_auth")
@Entity(name = "profile")
public class ProfileDatabaseDomain implements Serializable {

    @Id
    @Column(name = "name")
    private ProfileDomain.Profile name;

    @Column(name = "description")
    private String description;
}
