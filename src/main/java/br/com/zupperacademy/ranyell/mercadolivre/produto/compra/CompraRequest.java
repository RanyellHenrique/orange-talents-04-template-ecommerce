package br.com.zupperacademy.ranyell.mercadolivre.produto.compra;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {
    @NotNull @Positive
    private Integer quantidade;
    private FormaDePagamento formaDePagamento;

    public CompraRequest(Integer quantidade, FormaDePagamento formaDePagamento) {
        this.quantidade = quantidade;
        this.formaDePagamento = formaDePagamento;
    }

    public Compra toModel(Usuario cliente, Produto produto) {
        return new Compra(formaDePagamento, quantidade, produto.getValor(), produto, cliente);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }
}
