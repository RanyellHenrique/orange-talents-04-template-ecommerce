package br.com.zupperacademy.ranyell.mercadolivre.Usuario;

import br.com.zupperacademy.ranyell.mercadolivre.utils.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank @Email
    @UniqueValue(fieldName = "email", classDomain = Usuario.class)
    private String email;
    @NotBlank @Size(min = 6)
    private String senha;

    public UsuarioRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario toModel() {
        return new Usuario(email, senha);
    }
}
