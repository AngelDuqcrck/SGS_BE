package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findUserById(Integer userId);
}