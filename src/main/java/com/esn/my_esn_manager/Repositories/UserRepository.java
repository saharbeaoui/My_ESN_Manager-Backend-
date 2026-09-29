package com.esn.my_esn_manager.Repositories;

import com.esn.my_esn_manager.Entities.Roles;
import com.esn.my_esn_manager.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByRole(Roles role);


}
