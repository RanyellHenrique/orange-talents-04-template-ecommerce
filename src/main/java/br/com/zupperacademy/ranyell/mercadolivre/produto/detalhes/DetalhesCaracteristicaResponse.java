package br.com.zupperacademy.ranyell.mercadolivre.produto.detalhes;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Caracteristica;

public class DetalhesCaracteristicaResponse {

    private String descricao;
    private String nome;

    public DetalhesCaracteristicaResponse(Caracteristica caracteristica) {
        this.descricao = caracteristica.getDescricao();
        this.nome = caracteristica.getNome();
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
}
