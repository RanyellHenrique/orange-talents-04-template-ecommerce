package br.com.zupperacademy.ranyell.mercadolivre.produto.pergunta;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotNull
    @ManyToOne
    private Produto produto;
    @NotNull
    private LocalDateTime criadoEm;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.criadoEm = LocalDateTime.now();
    }
}
