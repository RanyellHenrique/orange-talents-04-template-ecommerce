package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import br.com.zupperacademy.ranyell.mercadolivre.utils.ExistValue;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public class ProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull @Positive
    private BigDecimal valor;
    @NotNull @PositiveOrZero
    private Integer quantidade;
    @Size(max = 1000) @NotBlank
    private String descricao;
    @ExistValue(fieldName = "id", classDomain = Categoria.class)
    private Long categoriaId;

    @Size(min = 3) @NotNull
    private Set<CaracteristicaRequest> caracteristicas;

    public ProdutoRequest(String nome, BigDecimal valor, Integer quantidade, String descricao,
                          Long categoriaId, Set<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.caracteristicas = caracteristicas;
    }

    public Set<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public Produto toModel(Categoria categoria, Usuario usuario) {
        return new Produto(nome, valor, quantidade, descricao, categoria, usuario);
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
}
