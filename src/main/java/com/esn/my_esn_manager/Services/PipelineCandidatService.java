package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.PipelineCandidat;
import com.esn.my_esn_manager.IServices.IPipelineCandidatService;
import com.esn.my_esn_manager.Repositories.PipelineCandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class PipelineCandidatService implements IPipelineCandidatService {

    private final PipelineCandidatRepository pipelineCandidatRepository;

    public PipelineCandidatService(
            PipelineCandidatRepository pipelineCandidatRepository) {

        this.pipelineCandidatRepository =
                pipelineCandidatRepository;
    }
    @Override
    public PipelineCandidat findByCandidatId(Long candidatId) {
        return pipelineCandidatRepository
                .findByCandidatId(candidatId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pipeline introuvable pour le candidat : "
                                        + candidatId
                        )
                );
    }
}
