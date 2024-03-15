package com.petshop.auth.adapter.output.repository.database.profileaccess;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile_access", schema = "petshop_auth")
@Entity(name = "profile_access")
public class ProfileAccessDatabaseDomain implements Serializable {

    @EmbeddedId
    private ProfileAccessID profileAccessID;

}
