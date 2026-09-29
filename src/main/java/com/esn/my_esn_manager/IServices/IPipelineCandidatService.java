package com.esn.my_esn_manager.IServices;

import com.esn.my_esn_manager.Entities.PipelineCandidat;

public interface IPipelineCandidatService {
    PipelineCandidat findByCandidatId(Long candidatId);
}
