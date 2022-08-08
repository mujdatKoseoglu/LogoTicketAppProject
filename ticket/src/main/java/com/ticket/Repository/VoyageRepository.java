package com.ticket.Repository;

import com.ticket.Model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage,Integer> {

}
