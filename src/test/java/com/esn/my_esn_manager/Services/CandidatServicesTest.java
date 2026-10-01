package com.esn.my_esn_manager.Services;


import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.Repositories.CandidatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CandidatServicesTest {
    @Mock
    private CandidatRepository candidatRepository;

    @InjectMocks
    private CandidatServices candidatServices;

   /* @Test
    void creer_shouldSaveAndReturnCandidat() {

        Candidat candidat = new Candidat();
        candidat.setNom("Trabelsi");
        candidat.setPrenom("Ahmed");

        when(candidatRepository.save(candidat))
                .thenReturn(candidat);

        Candidat resultat = candidatServices.creer(candidat);

        assertEquals("Trabelsi", resultat.getNom());
        assertEquals("Ahmed", resultat.getPrenom());
    }*/
}
