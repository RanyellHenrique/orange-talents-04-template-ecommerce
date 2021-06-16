package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import br.com.zupperacademy.ranyell.mercadolivre.fakes.PaypalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PAYPAL")
public class PaypalService implements  RetornoGatewayPagamento{

    private PaypalClient paypalClient;

    @Autowired
    public PaypalService(PaypalClient paypalClient) {
        this.paypalClient = paypalClient;
    }

    @Override
    public Transacao toTransacao(Compra compra, Long transacaoId) {
        var response = paypalClient.transacao(transacaoId).getBody();
        var status = response.getStatus() == 0 ? StatusTransacao.SUCESSO : StatusTransacao.ERRO;
        return new Transacao(status, response.getIdTransacao(), compra);
    }
}
