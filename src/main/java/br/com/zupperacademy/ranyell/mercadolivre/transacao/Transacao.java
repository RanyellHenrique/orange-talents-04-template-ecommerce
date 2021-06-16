package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;
    @NotNull
    private  Long idTransacaoGateway;
    @NotNull
    private LocalDateTime instante;
    @ManyToOne
    @NotNull
    private Compra compra;

    @Deprecated
    public Transacao() {
    }

    public Transacao(StatusTransacao status, Long idTransacaoGateway, Compra compra) {
        this.status = status;
        this.idTransacaoGateway = idTransacaoGateway;
        this.compra = compra;
        this.instante = LocalDateTime.now();
    }

    public boolean ConcluidaComSucesso() {
        return this.status.equals(StatusTransacao.SUCESSO);
    }

    public StatusTransacao getStatus() {
        return status;
    }
}
