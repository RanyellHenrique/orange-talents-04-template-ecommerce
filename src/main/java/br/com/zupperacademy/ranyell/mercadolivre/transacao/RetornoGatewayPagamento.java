package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;

public interface RetornoGatewayPagamento {

    Transacao toTransacao(Compra compra, Long transacaoId);
}
