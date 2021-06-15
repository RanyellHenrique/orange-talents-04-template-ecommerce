package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public interface EnviaEmail {
    void enviar(Usuario usuario);
}
