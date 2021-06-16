package br.com.zupperacademy.ranyell.mercadolivre.cadastro;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Caracteristica;
import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;

import javax.validation.constraints.NotBlank;

public class CaracteristicaRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public CaracteristicaRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica toModel(Produto produto) {
        return new Caracteristica(nome, descricao, produto);
    }

    public String getNome() {
        return nome;
    }
}
