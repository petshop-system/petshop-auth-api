package com.petshop.auth.adapter.output.repository.database.profileaccess;

import com.petshop.auth.application.domain.ProfileDomain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProfileAccessJPARepository extends CrudRepository<ProfileAccessDatabaseDomain, ProfileAccessID> {


    @Query(value = "select exists(select 1 from petshop_auth.profile_access pa " +
            " where 1 = 1 " +
            " and pa.fk_profile = :profile " +
            " and pa.fk_access = :accessAction)", nativeQuery = true)
    boolean existsProfileForAccess(@Param("profile") String profile,
                                   @Param("accessAction") String action);
}
