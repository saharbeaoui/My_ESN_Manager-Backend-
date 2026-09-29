package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.*;
import com.esn.my_esn_manager.IServices.ICandidatService;
import com.esn.my_esn_manager.Repositories.CandidatRepository;
import com.esn.my_esn_manager.Repositories.PipelineCandidatRepository;
import com.esn.my_esn_manager.Repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidatServices implements ICandidatService {

    private final CandidatRepository candidatRepository;

    private final UserRepository userRepository;
    private final PipelineCandidatRepository pipelineCandidatRepository;
    public CandidatServices(CandidatRepository candidatRepository,
                            UserRepository userRepository,
                            PipelineCandidatRepository pipelineCandidatRepository) {
        this.candidatRepository = candidatRepository;
        this.userRepository = userRepository;
        this.pipelineCandidatRepository = pipelineCandidatRepository;

    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public Candidat creer(Candidat candidat) {

        // 1. Récupérer l'utilisateur connecté grâce au JWT
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2. Récupérer le RH responsable
        Users responsableRH = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur connecté introuvable"
                        )
                );

        // 3. Affecter le RH au candidat
        candidat.setResponsableRH(responsableRH);

        Users referentTechnique =
                userRepository.findByRole(Roles.REFERENT_TECHNIQUE)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Référent technique introuvable"
                                )
                        );

        Users manager =
                userRepository.findByRole(Roles.MANAGER)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Manager introuvable"
                                )
                        );

        // 4. Sauvegarder le candidat
        Candidat candidatSaved =
                candidatRepository.save(candidat);

        // 5. Créer automatiquement la pipeline
        PipelineCandidat pipeline =
                new PipelineCandidat();

        pipeline.setCandidat(candidatSaved);
        List<EtapePipeline> etapes = new ArrayList<>();

        etapes.add(
                creerEtape(
                        TypeEtape.APPEL_ABOUTI,
                        pipeline,
                        responsableRH
                )
        );

        etapes.add(
                creerEtape(
                        TypeEtape.ENTRETIEN_RH,
                        pipeline,
                        responsableRH
                )
        );

        etapes.add(
                creerEtape(
                        TypeEtape.ENTRETIEN_TECHNIQUE,
                        pipeline,
                        referentTechnique
                )
        );

        etapes.add(
                creerEtape(
                        TypeEtape.ENTRETIEN_FINAL,
                        pipeline,
                        manager
                )
        );


        // 7. Ajouter les étapes à la pipeline
        pipeline.setEtapes(etapes);

        // 8. Sauvegarder la pipeline et ses étapes
        pipelineCandidatRepository.save(pipeline);

        // 9. Retourner le candidat créé
        return candidatSaved;
    }

    private EtapePipeline creerEtape(
            TypeEtape typeEtape,
            PipelineCandidat pipeline,
            Users responsable) {

        EtapePipeline etape = new EtapePipeline();

        etape.setTypeEtape(typeEtape);

        etape.setStatut(StatutPipeline.EN_ATTENTE);

        etape.setResponsable(responsable);

        etape.setPipeline(pipeline);

        return etape;
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