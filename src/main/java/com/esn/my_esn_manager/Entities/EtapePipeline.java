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
public class EtapePipeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEtape typeEtape;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPipeline statut;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime dateModification;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Users responsable;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pipeline_id", nullable = false)
    private PipelineCandidat pipeline;
}
