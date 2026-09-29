package com.esn.my_esn_manager.Repositories;

import com.esn.my_esn_manager.Entities.Candidat;
import com.esn.my_esn_manager.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat,Long> {

    List<Candidat> findByResponsableRH(Users responsableRH);

}
