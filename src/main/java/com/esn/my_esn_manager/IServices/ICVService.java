package com.esn.my_esn_manager.IServices;

import com.esn.my_esn_manager.Entities.CV;
import org.springframework.web.multipart.MultipartFile;

public interface ICVService {
    CV enregistrerCV(
            MultipartFile fichier,
            Long candidatId
    );
}
