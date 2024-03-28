package com.bibliotek.library.config;

import com.bibliotek.library.repositories.UserRepository;
import com.bibliotek.library.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    //cadena de filtro para manejar las autorizaciones de acceso a los endpoints segun el rol del usuario
    //autorizador
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //csrf es una configuracion de seguridad que previene el ataque a las request para extraer informacion sensible
                //lo deshabilitamos para poder usar el postman, pero cuando tengamos un servidor con formularios de fronend
                // hay que quitar esta linea y configurar el origen de las peticiones CORS
                .csrf().disable()
                //configuramos los endpoint segun el tipo de acceso segun el rol
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST,"/usuarios/registro").permitAll();
                    auth.requestMatchers("/usuarios/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                //configuramos si el servidor guarda o no informacion de los usuarios unas vez logueados
                .sessionManagement( session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // este setea la autorizacion basica
                //cuando se implemente JWT esta linea debe quitarse
                .httpBasic()
                .and()
                .build();
    }

    //encriptador de las passwords
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Servicio de detalles de usuarios en memoria temporal hasta implementar el servicio de verdad
    /**@Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("AdminJWT")
                .password(passwordEncoder().encode("1234"))
                .roles()
                .build()
        );
        return manager;
    }**/

    @Bean
    UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl(userRepository);
    }

    //administrador del inicio de sesion -> autenticacion
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

}
