package com.esn.my_esn_manager.Repositories;

import com.esn.my_esn_manager.Entities.PipelineCandidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PipelineCandidatRepository extends JpaRepository<PipelineCandidat, Long> {
    Optional<PipelineCandidat> findByCandidatId(Long candidatId);

}
