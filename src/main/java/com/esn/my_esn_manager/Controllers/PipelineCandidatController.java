package com.esn.my_esn_manager.Controllers;


import com.esn.my_esn_manager.Entities.PipelineCandidat;
import com.esn.my_esn_manager.IServices.IPipelineCandidatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/Pipeline")
public class PipelineCandidatController {

    private final IPipelineCandidatService pipelineCandidatService;

    public PipelineCandidatController(
            IPipelineCandidatService pipelineCandidatService) {

        this.pipelineCandidatService =
                pipelineCandidatService;
    }

    @GetMapping("/candidat/{candidatId}")
    public PipelineCandidat findByCandidatId(
            @PathVariable Long candidatId) {

        return pipelineCandidatService
                .findByCandidatId(candidatId);
    }
}
