package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.EtapePipeline;
import com.esn.my_esn_manager.IServices.IEtapePipelineService;
import com.esn.my_esn_manager.Repositories.EtapePipelineRepository;
import org.springframework.stereotype.Service;

@Service
public class EtapePipelineService implements IEtapePipelineService {
    private final EtapePipelineRepository etapePipelineRepository;

    public EtapePipelineService(
            EtapePipelineRepository etapePipelineRepository) {

        this.etapePipelineRepository =
                etapePipelineRepository;
    }

    @Override
    public EtapePipeline findById(Long id) {
        return etapePipelineRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Étape introuvable avec l'id : " + id
                        )
                );
    }
}
