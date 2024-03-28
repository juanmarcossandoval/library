package com.bibliotek.library.services;

import com.bibliotek.library.controllers.dtos.UserDto;
import com.bibliotek.library.controllers.responses.NewUserResponse;
import com.bibliotek.library.entities.ERole;
import com.bibliotek.library.entities.Role;
import com.bibliotek.library.entities.User;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.repositories.RoleRepository;
import com.bibliotek.library.repositories.UserRepository;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepo;

    public UserService (UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public NewUserResponse crearNuevo (UserDto nuevoUsuario) throws BadRequestException {
        existUsername(nuevoUsuario.getUsername());
        existEmail(nuevoUsuario.getEmail());
        validPassword(nuevoUsuario.getPassword(), nuevoUsuario.getPasswordCheck());
        User newUser = UserDto.mapToEntity(nuevoUsuario);
        //encriptamos la contraseña
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        //agregamos los roles en este caso solo como usuario comun que se registra en la aplicacion
        Optional<Role> rol = this.roleRepo.findByRole(ERole.USER);
        if (rol.isEmpty()){
            throw new BadRequestException("No se pudo agregar el rol del usuario");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(rol.get());
        newUser.setRoles(roles);
        return NewUserResponse.mapToResponse(this.userRepo.save(newUser));
    }

    public void eliminar(String username) throws BadRequestException {
        Optional<User> buscado = this.userRepo.findByUsername(username);
        if (buscado.isEmpty()) throw new BadRequestException("No hay socios registrados con ese nombre de usuario");
        this.userRepo.delete(buscado.get());
    }

    private void validPassword(String password, String passwordCheck) throws BadRequestException {
        isSecuredPass(password);
        if(passwordDontMatch(password,passwordCheck)) throw new BadRequestException("las contraseñas no coinciden");
    }

    private boolean passwordDontMatch(String password, String passwordCheck){
        return !password.equals(passwordCheck);
    }

    private void existUsername(String username) throws BadRequestException {
        if (this.userRepo.findByUsername(username).isPresent()) throw new BadRequestException("El nombre de usuario ya esta en uso");
    }

    private void existEmail(String email) throws BadRequestException {
        if (this.userRepo.findByEmail(email).isPresent()) throw new BadRequestException("El correo electronico ya esta en uso");
    }

    private void isSecuredPass(String password) throws BadRequestException {
        String minusculas = ".*[a-z].*";
        if (!password.matches(minusculas)) throw new BadRequestException("La contraseña debe contener al menos una letra minuscula");
        String mayusculas = ".*[A-Z].*";
        if (!password.matches(mayusculas)) throw new BadRequestException("La contaseña debe contener al menos una letra mayuscula");
        String numeros = ".*[0-9].*";
        if (!password.matches(numeros)) throw new BadRequestException("La contaseña debe contener al menos un numero");
        String simbolos = ".*[!@#$%_].*";
        if (!password.matches(simbolos)) throw new BadRequestException("La contraseña debe contener al menos uno de los siguientes simbolos: !@#$%_");
        if(password.contains(" ")) throw new BadRequestException("La contraseña no debe contener espacios en blanco");
    }

}
