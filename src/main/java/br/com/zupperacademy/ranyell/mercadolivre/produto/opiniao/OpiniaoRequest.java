package br.com.zupperacademy.ranyell.mercadolivre.produto.opiniao;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoRequest {
    @Length(max = 500)
    @NotBlank
    private String descricao;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;
    @NotBlank
    private String titulo;

    public OpiniaoRequest(String descricao, Integer nota, String titulo) {
        this.descricao = descricao;
        this.nota = nota;
        this.titulo = titulo;
    }

    public Opiniao toModel(Usuario usuario, Produto produto) {
        return new Opiniao(descricao, titulo, nota, usuario, produto);
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }
}
