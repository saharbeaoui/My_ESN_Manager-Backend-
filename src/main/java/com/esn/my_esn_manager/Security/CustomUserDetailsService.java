package com.esn.my_esn_manager.Security;


import com.esn.my_esn_manager.Entities.Roles;
import com.esn.my_esn_manager.Entities.Users;
import com.esn.my_esn_manager.Repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class CustomUserDetailsService implements UserDetailsService  {
    private final UserRepository utilisateurRepository;

    public CustomUserDetailsService(UserRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Users utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utilisateur non trouvé avec l'email : " + email
                        )
                );

     return User.builder()
        .username(utilisateur.getEmail())
        .password(utilisateur.getPassword())
        .roles(utilisateur.getRole().name())
        .disabled(!utilisateur.isActif())
        .build();
    }
}
