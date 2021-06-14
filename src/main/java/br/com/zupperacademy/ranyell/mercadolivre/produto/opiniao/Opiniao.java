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

    public Opiniao(String descricao, Usuario usuario, Produto produto, Integer nota) {
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
        this.nota = nota;
    }
}
