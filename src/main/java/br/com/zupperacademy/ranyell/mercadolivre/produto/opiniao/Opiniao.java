package br.com.zupperacademy.ranyell.mercadolivre.produto.opiniao;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Length(max = 500)
    private String descricao;

    @NotBlank
    private String titulo;

    @NotNull
    @Max(5) @Min(1)
    private Integer nota;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Produto produto;

    @Deprecated
    public Opiniao() {
    }

    public Opiniao(String descricao, String titulo, Integer nota, Usuario usuario, Produto produto) {
        this.descricao = descricao;
        this.titulo = titulo;
        this.nota = nota;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNota() {
        return nota;
    }
}
