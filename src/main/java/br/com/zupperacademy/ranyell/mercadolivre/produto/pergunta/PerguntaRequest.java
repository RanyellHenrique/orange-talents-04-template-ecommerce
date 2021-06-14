package br.com.zupperacademy.ranyell.mercadolivre.produto.pergunta;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {
    @NotBlank
    private String titulo;

    public PerguntaRequest(@JsonProperty("titulo") String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(Usuario usuario, Produto produto) {
        return new Pergunta(titulo, usuario, produto);
    }


}
