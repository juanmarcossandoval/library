package com.bibliotek.library.controllers.dtos;

import com.bibliotek.library.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class UserDto {

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Length(min = 8, max= 50, message = "El nombre de usuario debe tener entre 8 y 50 caracteres")
    private String username;

    @Email(message = "Este no es un email valido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Length(min = 8, message = "La contraseña debe al menos tener 8 caracteres de longitud")
    private String password;

    @NotBlank(message = "La contraseña repetida no puede estar en blanco")
    @Length(min = 8, message = "La contraseña repetida debe al menos tener 8 caracteres de longitud")
    private String passwordCheck;

    public static User mapToEntity(UserDto userDto){
        User usuario = new User();
        usuario.setUsername(userDto.getUsername());
        usuario.setEmail(userDto.getEmail());
        usuario.setPassword(userDto.getPassword());
        return usuario;
    }

}
