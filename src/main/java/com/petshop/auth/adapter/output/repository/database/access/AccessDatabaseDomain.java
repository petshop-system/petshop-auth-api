package com.petshop.auth.adapter.output.repository.database.access;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access", schema = "petshop_auth")
@Entity(name = "access")
public class AccessDatabaseDomain implements Serializable {

    @Id
    @Column(name = "action")
    private String action;

    @Column(name = "description")
    private String description;
}
