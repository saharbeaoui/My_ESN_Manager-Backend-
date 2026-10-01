package com.esn.my_esn_manager.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    private String adresse;

    private String lieuMobilite;

    private Integer nombreAnneesExperience;
    @ManyToOne
    @JoinColumn(name = "responsable_rh_id", nullable = false)
    private Users responsableRH;

    @OneToMany(
            mappedBy = "candidat",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Diplome> diplomes;
    @OneToOne(mappedBy = "candidat",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private CV cv;
}
