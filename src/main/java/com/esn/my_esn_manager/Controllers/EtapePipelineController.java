package com.esn.my_esn_manager.Controllers;
import com.esn.my_esn_manager.Entities.EtapePipeline;
import com.esn.my_esn_manager.IServices.IEtapePipelineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/API/EtapePipeline")
public class EtapePipelineController {

    private final IEtapePipelineService etapePipelineService;

    public EtapePipelineController(
            IEtapePipelineService etapePipelineService) {

        this.etapePipelineService =
                etapePipelineService;
    }

    @GetMapping("/FindEtapeById/{id}")
    public EtapePipeline findById(
            @PathVariable Long id) {

        return etapePipelineService.findById(id);
    }
}