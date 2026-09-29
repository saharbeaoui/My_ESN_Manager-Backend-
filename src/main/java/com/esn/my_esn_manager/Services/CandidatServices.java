package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.Entities.Roles;
import com.esn.my_esn_manager.Entities.Users;
import com.esn.my_esn_manager.IServices.ICandidatService;
import com.esn.my_esn_manager.Repositories.CandidatRepository;
import com.esn.my_esn_manager.Repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatServices implements ICandidatService {

    private final CandidatRepository candidatRepository;

    private final UserRepository userRepository;

    public CandidatServices(CandidatRepository candidatRepository,
                            UserRepository userRepository) {
        this.candidatRepository = candidatRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat creer(Candidat candidat) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users responsableRH =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur connecté introuvable"
                                )
                        );

        candidat.setResponsableRH(responsableRH);

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

    @Override
    public List<Candidat> findMesCandidats() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users rh =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur connecté introuvable"
                                )
                        );

        return candidatRepository.findByResponsableRH(rh);
    }

    @Override
    public List<Candidat> findCandidatsParRH(Long rhId) {
        Users rh = userRepository.findById(rhId)
                .orElseThrow(() ->
                        new RuntimeException("RH introuvable avec l'id : " + rhId)
                );

        if (rh.getRole() != Roles.RH) {
            throw new RuntimeException(
                    "L'utilisateur sélectionné n'est pas un RH"
            );
        }

        return candidatRepository.findByResponsableRH(rh);
    }
}