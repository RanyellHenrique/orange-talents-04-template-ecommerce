package br.com.zupperacademy.ranyell.mercadolivre.produto.imagem;

import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;

import javax.persistence.*;

@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    private Produto produto;

    @Deprecated
    public ImagemProduto() {
    }

    public ImagemProduto(String url, Produto produto) {
        this.url = url;
        this.produto = produto;
    }
}
