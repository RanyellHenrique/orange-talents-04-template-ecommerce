package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.StatusDaCompra;

public enum StatusTransacao {
    SUCESSO(StatusDaCompra.SUCESSO), ERRO(StatusDaCompra.FALHA);


    private  StatusDaCompra statusDaCompra;

    StatusTransacao(StatusDaCompra statusDaCompra) {
        this.statusDaCompra = statusDaCompra;
    }

    public StatusDaCompra getStatusDaCompra() {
        return statusDaCompra;
    }
}
