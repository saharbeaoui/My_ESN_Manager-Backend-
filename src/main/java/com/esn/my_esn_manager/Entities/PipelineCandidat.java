package com.esn.my_esn_manager.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PipelineCandidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "candidat_id", nullable = false, unique = true)
    private Candidat candidat;

    @OneToMany(
            mappedBy = "pipeline",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EtapePipeline> etapes;
}
