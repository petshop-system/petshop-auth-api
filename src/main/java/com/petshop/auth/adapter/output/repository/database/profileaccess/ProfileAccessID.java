package com.petshop.auth.adapter.output.repository.database.profileaccess;

import com.petshop.auth.adapter.output.repository.database.access.AccessDatabaseDomain;
import com.petshop.auth.adapter.output.repository.database.profile.ProfileDatabaseDomain;
import com.petshop.auth.application.domain.ProfileDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ProfileAccessID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "fk_profile")
    private ProfileDatabaseDomain profileDatabaseDomain;

    @ManyToOne
    @JoinColumn(name = "fk_access")
    private AccessDatabaseDomain accessDatabaseDomain;

}
