package com.esn.my_esn_manager.IServices;



import com.esn.my_esn_manager.Entities.Candidat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICandidatService {

    Candidat creer(Candidat candidat,
                   MultipartFile cv);

    List<Candidat> findAll();

    Candidat findById(Long id);

    Candidat modifier(Long id, Candidat candidat);

    void supprimer(Long id);
    List<Candidat> findMesCandidats();
    List<Candidat> findCandidatsParRH(Long rhId);

}
