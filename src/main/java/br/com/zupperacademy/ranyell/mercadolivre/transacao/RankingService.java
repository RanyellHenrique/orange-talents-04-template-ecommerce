package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.fakes.RankingClient;
import br.com.zupperacademy.ranyell.mercadolivre.fakes.RankingRequest;
import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService implements  EventoCompraSucesso{

    private RankingClient rankingClient;

    @Autowired
    public RankingService(RankingClient rankingClient) {
        this.rankingClient = rankingClient;
    }

    @Override
    public void processa(Compra compra) {
        rankingClient.processa(new RankingRequest(compra.getId(), compra.getId()));
    }
}
