package com.sistema.solicitudes.sgs.security;


import com.sistema.solicitudes.sgs.security.filters.JwtAuthenticationFilter;
import com.sistema.solicitudes.sgs.security.filters.JwtAuthorizationFilter;
import com.sistema.solicitudes.sgs.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceDetailsImpl userServiceDetails;


    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    private final String[] PUBLIC_URL  = {"**"};

    /**
     * We create this bean because Springboot needs manage the instance on the SpringContext.
     * You can personalize this method includes all the security config as wel.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http,  AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeRequests(auth -> {
                    auth.antMatchers(PUBLIC_URL).permitAll(); //input all the endpoints its free por the user.
                    //auth.anyRequest().authenticated();
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    /**
     * Springboot security need a password encode bean.
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * We need create a authenticationManager to manage all the user authentication.
     * @param http a HttpSecurity that Springboot take the responsibility to pass it;
     * @return An authentication manager with BCryptPasswordEncoder and out userDetailService;
     */
    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userServiceDetails)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

}
