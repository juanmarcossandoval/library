package com.bibliotek.library.controllers.responses;

import com.bibliotek.library.entities.Role;
import com.bibliotek.library.entities.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewUserResponse {

    private String nombreDeUsuario;
    private String correo;
    private List<String> permisos;

    public static NewUserResponse mapToResponse(User usuario){
        NewUserResponse respuesta = new NewUserResponse();
        respuesta.setNombreDeUsuario(usuario.getUsername());
        respuesta.setCorreo(usuario.getEmail());
        respuesta.setPermisos(new ArrayList<>());
        for (Role r :usuario.getRoles()){
            respuesta.getPermisos().add(r.getRole().name());
        }
        return respuesta;
    }

}
