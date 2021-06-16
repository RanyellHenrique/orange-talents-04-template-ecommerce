package br.com.zupperacademy.ranyell.mercadolivre.compra;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {
    @NotNull @Positive
    private Integer quantidade;
    @NotNull
    private FormaDePagamento formaDePagamento;
    @NotNull
    private Long produtoId;

    public CompraRequest(Integer quantidade, FormaDePagamento formaDePagamento, Long produtoId) {
        this.quantidade = quantidade;
        this.formaDePagamento = formaDePagamento;
        this.produtoId = produtoId;
    }

    public Compra toModel(Usuario cliente, Produto produto) {
        return new Compra(formaDePagamento, quantidade, produto.getValor(), produto, cliente);
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }
}
