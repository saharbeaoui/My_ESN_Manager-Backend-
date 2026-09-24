package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.IServices.ICandidatService;
import com.esn.my_esn_manager.Repositories.CandidatRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatServices implements ICandidatService {

    private final CandidatRepository candidatRepository;

    public CandidatServices(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat creer(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH', 'REFERENT_TECHNIQUE', 'INGENIEUR_AFFAIRES')")
    public List<Candidat> findAll() {
        return candidatRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH', 'REFERENT_TECHNIQUE', 'INGENIEUR_AFFAIRES')")
    public Candidat findById(Long id) {
        return candidatRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Aucun candidat trouvé avec cet Id"
                        )
                );
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat modifier(Long id, Candidat candidat) {

        Candidat candidatExistant = findById(id);

        candidatExistant.setNom(candidat.getNom());
        candidatExistant.setPrenom(candidat.getPrenom());
        candidatExistant.setDateNaissance(candidat.getDateNaissance());
        candidatExistant.setCivilite(candidat.getCivilite());
        candidatExistant.setAdresse(candidat.getAdresse());
        candidatExistant.setLieuMobilite(candidat.getLieuMobilite());
        candidatExistant.setNombreAnneesExperience(
                candidat.getNombreAnneesExperience()
        );

        return candidatRepository.save(candidatExistant);
    }

    @Override
    @PreAuthorize("hasRole('MANAGER')")
    public void supprimer(Long id) {

        if (!candidatRepository.existsById(id)) {
            throw new RuntimeException(
                    "Candidat non trouvé avec l'id : " + id
            );
        }

        candidatRepository.deleteById(id);
    }
}