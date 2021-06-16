package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;

public interface EventoCompraSucesso {

    void processa(Compra compra);
}
