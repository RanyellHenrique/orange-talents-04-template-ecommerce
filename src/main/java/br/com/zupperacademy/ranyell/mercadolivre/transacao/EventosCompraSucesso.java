package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosCompraSucesso {

    private Set<EventoCompraSucesso> eventoCompraSucessos;

    @Autowired
    public EventosCompraSucesso(Set<EventoCompraSucesso> eventoCompraSucessos) {
        this.eventoCompraSucessos = eventoCompraSucessos;
    }

    public void processa(Compra compra) {
        if(compra.processadaComSucesso()) {
            eventoCompraSucessos.forEach(evento -> evento.processa(compra));
        }
        else {

        }
    }
}
