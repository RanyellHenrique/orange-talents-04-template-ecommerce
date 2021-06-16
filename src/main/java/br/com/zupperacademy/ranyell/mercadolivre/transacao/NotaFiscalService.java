package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.fakes.NotaFiscalClient;
import br.com.zupperacademy.ranyell.mercadolivre.fakes.NotaFiscalRequest;
import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalService implements  EventoCompraSucesso{

    private NotaFiscalClient notaFiscalClient;

    public NotaFiscalService(NotaFiscalClient notaFiscalClient) {
        this.notaFiscalClient = notaFiscalClient;
    }

    @Override
    public void processa(Compra compra) {
        notaFiscalClient.processa(new NotaFiscalRequest(compra.getId(), compra.getId()));
    }
}
