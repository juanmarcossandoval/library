package com.bibliotek.library.controllers;

import com.bibliotek.library.controllers.dtos.UserDto;
import com.bibliotek.library.controllers.responses.NewUserResponse;
import com.bibliotek.library.entities.User;
import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userServ;

    public UserController(UserService userServ){
        this.userServ = userServ;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> postOne(@Valid @RequestBody UserDto nuevoUsuario) throws BadRequestException {
        NewUserResponse nuevo = this.userServ.crearNuevo(nuevoUsuario);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOne(@RequestParam String username) throws BadRequestException {
        this.userServ.eliminar(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
