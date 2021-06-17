package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EnviaEmailFake implements EnviaEmail {

    private final Logger LOOGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void enviar(Usuario usuario) {
        LOOGER.info("Email Enviado para vendedor {} ", usuario.getUsername());
    }
}
