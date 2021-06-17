package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro.CaracteristicaRequest;
import br.com.zupperacademy.ranyell.mercadolivre.produto.imagem.ImagemProduto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.opiniao.Opiniao;
import br.com.zupperacademy.ranyell.mercadolivre.produto.pergunta.Pergunta;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ImagemProduto> imagens = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.ALL})
    private List<Opiniao> opinioes = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.ALL})
    private List<Pergunta> perguntas = new ArrayList<>();

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void addAllImagens(List<ImagemProduto> imagens) {
        this.imagens.addAll(imagens);
    }

    public  void addOpiniao(Opiniao opiniao) {
        this.opinioes.add(opiniao);
    }

    public void addPergunta(Pergunta pergunta) {
        this.perguntas.add(pergunta);
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public <T> List<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
        return this.imagens.stream().map(funcaoMapeadora)
                .collect(Collectors.toList());
    }

    public <T> List<T> mapeiaCaracteristicas(Function<Caracteristica, T> funcaoMapeadora) {
        return this.caracteristicas.stream().map(funcaoMapeadora)
                .collect(Collectors.toList());
    }

    public <T> List<T> mapeiaOpinioes(Function<Opiniao, T> funcaoMapeadora) {
        return this.opinioes.stream().map(funcaoMapeadora)
                .collect(Collectors.toList());
    }

    public <T> List<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
        return this.perguntas.stream().map(funcaoMapeadora)
                .collect(Collectors.toList());
    }

    public Boolean abateEstoque(@Positive Integer quantidade) {
        if(quantidade > this.quantidade) {
           return false;
        }
        this.quantidade -= quantidade;
        return true;
    }

    public Long getId() {
        return id;
    }

    public List<Opiniao> getOpinioes() {
        return opinioes;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }
}
