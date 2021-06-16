package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import br.com.zupperacademy.ranyell.mercadolivre.fakes.PagseguroClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PAGSEGURO")
public class PagseguroService implements  RetornoGatewayPagamento{

    private PagseguroClient pagseguroClient;

    @Autowired
    public PagseguroService(PagseguroClient pagseguroClient) {
        this.pagseguroClient = pagseguroClient;
    }

    @Override
    public Transacao toTransacao(Compra compra, Long transacaoId) {
        var response = pagseguroClient.transacao(transacaoId).getBody();
        var status = response.getStatus().equals(StatusTransacao.SUCESSO.name()) ? StatusTransacao.SUCESSO : StatusTransacao.ERRO;
        return new Transacao(status, response.getIdTransacao(), compra);
    }
}
