package com.esn.my_esn_manager.Entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diplomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String etablissement;

    private Integer anneeObtention;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;
}
