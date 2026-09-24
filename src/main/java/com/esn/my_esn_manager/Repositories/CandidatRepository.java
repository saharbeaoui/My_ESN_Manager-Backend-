package com.esn.my_esn_manager.Repositories;

import com.esn.my_esn_manager.Entities.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat,Long> {

}
