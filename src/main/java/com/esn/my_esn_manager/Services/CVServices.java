package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.CV;
import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.IServices.ICVService;
import com.esn.my_esn_manager.Repositories.CVRepository;
import com.esn.my_esn_manager.Repositories.CandidatRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CVServices implements ICVService {

    private final CVRepository cvRepository;
    private final CandidatRepository candidatRepository;

    private final Path dossierCV =
            Paths.get("uploads/cv");

    public CVServices(
            CVRepository cvRepository,
            CandidatRepository candidatRepository) {

        this.cvRepository = cvRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public CV enregistrerCV(
            MultipartFile fichier,
            Long candidatId) {

        if (fichier == null || fichier.isEmpty()) {
            throw new RuntimeException(
                    "Le fichier CV est obligatoire"
            );
        }

        // Vérifier le type
        if (!"application/pdf".equalsIgnoreCase(
                fichier.getContentType())) {

            throw new RuntimeException(
                    "Le CV doit être au format PDF"
            );
        }

        // Vérifier la taille : 5 Mo maximum
        if (fichier.getSize() > 5 * 1024 * 1024) {

            throw new RuntimeException(
                    "La taille du CV ne doit pas dépasser 5 Mo"
            );
        }

        Candidat candidat =
                candidatRepository.findById(candidatId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Candidat introuvable"
                                )
                        );

        try {

            // Créer le dossier s'il n'existe pas
            Files.createDirectories(dossierCV);

            // Nom unique pour éviter les doublons
            String nomOriginal =
                    fichier.getOriginalFilename();

            String extension = ".pdf";

            if (nomOriginal != null &&
                    nomOriginal.toLowerCase().endsWith(".pdf")) {
                extension = ".pdf";
            }

            String nomUnique =
                    "cv_" +
                            candidatId +
                            "_" +
                            UUID.randomUUID() +
                            extension;

            Path chemin =
                    dossierCV.resolve(nomUnique);

            // Enregistrer le fichier
            Files.copy(
                    fichier.getInputStream(),
                    chemin,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Créer les métadonnées
            CV cv = new CV();

            cv.setNomFichier(nomOriginal);
            cv.setCheminFichier(
                    chemin.toString()
            );
            cv.setTypeFichier(
                    fichier.getContentType()
            );
            cv.setTaille(
                    fichier.getSize()
            );
            cv.setDateUpload(
                    LocalDateTime.now()
            );
            cv.setCandidat(candidat);

            return cvRepository.save(cv);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur lors de l'enregistrement du CV",
                    e
            );
        }
    }
}