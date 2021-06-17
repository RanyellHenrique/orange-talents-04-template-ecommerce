package br.com.zupperacademy.ranyell.mercadolivre.compra;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.transacao.Transacao;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private FormaDePagamento formaDePagamento;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusDaCompra statusDaCompra;
    @NotNull @Positive
    private Integer quantidade;
    @NotNull
    private BigDecimal valorUnitario;

    @ManyToOne
    @NotNull
    private Produto produto;

    @ManyToOne
    @NotNull
    private Usuario cliente;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(FormaDePagamento formaDePagamento, Integer quantidade, BigDecimal valorUnitario, Produto produto, Usuario cliente) {
        this.formaDePagamento = formaDePagamento;
        this.statusDaCompra = StatusDaCompra.INICIADA;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.produto = produto;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public StatusDaCompra getStatusDaCompra() {
        return statusDaCompra;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public String urlRedirecionamento(UriComponentsBuilder uri) {
        return this.formaDePagamento.pagamento(this, uri);
    }

    public void addTransacao(Transacao transacao) {

        Assert.isTrue(transacoesConcluidasComSucesso().size() <= 1,"Deu ruim deu ruim deu ruim, tem mais de uma transacao concluida com sucesso aqui na compra "+this.id);

        transacoes.add(transacao);
        statusDaCompra = transacao.getStatus().getStatusDaCompra();
    }

    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::ConcluidaComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,"Deu ruim deu ruim deu ruim, tem mais de uma transacao concluida com sucesso aqui na compra "+this.id);

        return transacoesConcluidasComSucesso;
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();
    }
}
