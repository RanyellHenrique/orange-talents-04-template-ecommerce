package br.com.zupperacademy.ranyell.mercadolivre.produto.detalhes;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.imagem.ImagemProduto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.pergunta.Pergunta;

import java.math.BigDecimal;
import java.util.List;

public class DetalhesProdutoResponse {

    private List<String> links;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Double mediaDeNotas;
    private Long totalDeNotas;
    private List<DetalhesCaracteristicaResponse> caracteristicas;
    private List<DetalhesOpiniaoResponse> opinioes;
    private List<String> perguntas;

    public DetalhesProdutoResponse(Produto produto, Double mediaDeNotas, Long totalDeNotas) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.valor = produto.getValor();
        this.mediaDeNotas = mediaDeNotas;
        this.totalDeNotas = totalDeNotas;
        this.caracteristicas = produto.mapeiaCaracteristicas(DetalhesCaracteristicaResponse::new);
        this.opinioes = produto.mapeiaOpinioes(DetalhesOpiniaoResponse::new);
        this.perguntas = produto.mapeiaPerguntas(Pergunta::getTitulo);
        this.links = produto.mapeiaImagens(ImagemProduto::getUrl);
    }

    public List<String> getLinks() {
        return links;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getMediaDeNotas() {
        return mediaDeNotas;
    }

    public Long getTotalDeNotas() {
        return totalDeNotas;
    }

    public List<DetalhesCaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public List<DetalhesOpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public List<String> getPerguntas() {
        return perguntas;
    }
}
