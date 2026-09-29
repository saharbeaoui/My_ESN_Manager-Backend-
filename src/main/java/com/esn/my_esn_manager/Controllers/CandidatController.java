package com.esn.my_esn_manager.Controllers;


import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.IServices.ICandidatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/Candidat")
public class CandidatController {

    private final ICandidatService candidatService;

    public CandidatController(ICandidatService candidatService) {
        this.candidatService = candidatService;
    };

    // CREATE
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat creer(@RequestBody Candidat candidat) {
        return candidatService.creer(candidat);
    }

    // READ ALL
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH', 'REFERENT_TECHNIQUE', 'INGENIEUR_AFFAIRES')")

    public List<Candidat> findAll() {
        return candidatService.findAll();
    }

    // READ BY ID
    @GetMapping("/finbyid/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH', 'REFERENT_TECHNIQUE', 'INGENIEUR_AFFAIRES')")
    public Candidat findById(@PathVariable Long id) {
        return candidatService.findById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat modifier(
            @PathVariable Long id,
            @RequestBody Candidat candidat) {

        return candidatService.modifier(id, candidat);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {

        candidatService.supprimer(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mes-candidats")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public List<Candidat> findMesCandidats() {
        return candidatService.findMesCandidats();
    }
    @GetMapping("/par-rh/{rhId}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Candidat> findCandidatsParRH(
            @PathVariable Long rhId) {

        return candidatService.findCandidatsParRH(rhId);
    }
}
