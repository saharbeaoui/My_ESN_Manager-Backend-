package com.esn.my_esn_manager.Services;

import com.esn.my_esn_manager.Entities.Users;
import com.esn.my_esn_manager.IServices.IUtilisateurService;
import com.esn.my_esn_manager.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices  implements IUtilisateurService {

    private final UserRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServices(
            UserRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Users creer(Users utilisateur) {

        utilisateur.setPassword(
                passwordEncoder.encode(utilisateur.getPassword())
        );

        return utilisateurRepository.save(utilisateur);    }
}
