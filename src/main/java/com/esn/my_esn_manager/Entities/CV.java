package com.esn.my_esn_manager.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomFichier;

    @Column(nullable = false)
    private String cheminFichier;

    @Column(nullable = false)
    private String typeFichier;

    private Long taille;

    private LocalDateTime dateUpload;

    @OneToOne
    @JoinColumn(name = "candidat_id", nullable = false, unique = true)
    @JsonIgnore
    private Candidat candidat;
}
