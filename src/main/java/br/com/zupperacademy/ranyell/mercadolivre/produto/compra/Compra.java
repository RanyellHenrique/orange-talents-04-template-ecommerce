package br.com.zupperacademy.ranyell.mercadolivre.produto.compra;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

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
}
