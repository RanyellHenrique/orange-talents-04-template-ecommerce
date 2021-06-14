package br.com.zupperacademy.ranyell.mercadolivre.produto.detalhes;

import br.com.zupperacademy.ranyell.mercadolivre.produto.opiniao.Opiniao;

public class DetalhesOpiniaoResponse {

    private String titulo;
    private String descricao;
    private Integer nota;

    public DetalhesOpiniaoResponse(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
        this.nota = opiniao.getNota();
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNota() {
        return nota;
    }

    public String getDescricao() {
        return descricao;
    }
}
