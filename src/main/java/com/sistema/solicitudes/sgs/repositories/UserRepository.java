package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Repository that define the methods to access to User's data 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndRol(Integer id, Rol rol);

}
