package com.sistema.solicitudes.sgs.security;

import com.sistema.solicitudes.sgs.entities.Usuario;
import com.sistema.solicitudes.sgs.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;


    /**
     * With this method configure the service with SpringBoot security will get the user;
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("The user with email "+username+" does not exists."));

        //We need mapped all the roles
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRol().getDescripcion()));

        return new User(user.getEmail(),
                user.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                authorities);

    }


}
