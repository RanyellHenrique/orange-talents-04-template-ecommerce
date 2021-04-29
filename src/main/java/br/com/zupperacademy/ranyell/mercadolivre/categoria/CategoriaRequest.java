package br.com.zupperacademy.ranyell.mercadolivre.categoria;

import br.com.zupperacademy.ranyell.mercadolivre.utils.ExistValue;
import br.com.zupperacademy.ranyell.mercadolivre.utils.UniqueValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(classDomain = Categoria.class, fieldName = "nome")
    private String nome;

    @ExistValue(classDomain = Categoria.class, fieldName = "id", isRequired = false )
    private Long categoriaMaeId;

    public CategoriaRequest(@JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    public Long getCategoriaMaeId() {
        return categoriaMaeId;
    }

    public void setCategoriaMaeId(Long categoriaMaeId) {
        this.categoriaMaeId = categoriaMaeId;
    }

    public Categoria toModel() {
        return new Categoria(nome);
    }
}
