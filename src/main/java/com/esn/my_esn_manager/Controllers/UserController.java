package com.esn.my_esn_manager.Controllers;

import com.esn.my_esn_manager.Entities.Users;
import com.esn.my_esn_manager.IServices.IUtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final IUtilisateurService utilisateurService;

    public UserController(IUtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAnyRole('MANAGER', 'RH')")
    public ResponseEntity<Users> creer(
            @RequestBody Users utilisateur) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(utilisateurService.creer(utilisateur));
    }
}
