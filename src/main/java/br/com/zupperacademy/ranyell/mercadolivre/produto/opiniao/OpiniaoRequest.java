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

    public OpiniaoRequest(String descricao, Integer nota) {
        this.descricao = descricao;
        this.nota = nota;
    }


    public Opiniao toModel(Usuario usuario, Produto produto) {
        return  new Opiniao(descricao, usuario, produto, nota);
    }
}
