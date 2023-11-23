package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Repository that define the methods to access to Request's data 
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    /**
     * Find a user by respective user id
     * @param userId that contains the id assigned to the user
     * @return an Request object containing the request if found, otherwise returns null 
     */
    List<Request> findByUserId(Integer userId);



}