package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private Instant criadoEm;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    Set<Caracteristica> caracteristicas = new HashSet<>();


    @Deprecated
    public Produto() {
    }

    public Produto(String nome, BigDecimal valor, Integer quantidade, String descricao, Categoria categoria,
                   Usuario usuario, List<CaracteristicaRequest> caracteristicaRequests) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.criadoEm = Instant.now();
        this.caracteristicas = caracteristicaRequests.stream().map(c -> c.toModel(this)).collect(Collectors.toSet());
        Assert.isTrue(this.caracteristicas.size() >= 3, "O Produto precisa ter no minimo 3 caracteristicas");
    }
}
