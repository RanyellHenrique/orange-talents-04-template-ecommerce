package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class produtoTests {

    private Usuario usuario;
    private Categoria categoria;

    @BeforeEach
    public void setUp() {
        categoria = new Categoria("Informatica");
        usuario = new Usuario("joao@email.com", "12345");
    }

    @ParameterizedTest
    @MethodSource("geradorTest1")
    @DisplayName("Um produto deve ter no minimo 3 caractetisticas")
    public void test1(List<CaracteristicaRequest> caracteristicas) {
            new Produto("notebook", BigDecimal.TEN, 10, "descricao", categoria, usuario, caracteristicas);
    }

    static Stream<Arguments> geradorTest1() {
        return Stream.of(
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2"),
                                new CaracteristicaRequest("nome3", "descricao3"))),
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2"),
                                new CaracteristicaRequest("nome3", "descricao3"),
                                new CaracteristicaRequest("nome4", "descricao4")))
                );
    }

    @ParameterizedTest
    @MethodSource("geradorTest2")
    @DisplayName("O produto deve larçar a exceção IllegalArgumentException quando tiver menos de 3 caracteristicas")
    public void test2(List<CaracteristicaRequest> caracteristicas) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("notebook", BigDecimal.TEN, 10, "descricao", categoria, usuario, caracteristicas);
        });
    }

    static Stream<Arguments> geradorTest2() {
        return Stream.of(
                //caracteristicas iguais
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome3", "descricao3"))),
                //menos de três caracteristicas
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2")))
        );
    }

    @ParameterizedTest
    @MethodSource("geradorTest3")
    @DisplayName("O metodo temCaracteristicasIguais deve retornar verdadeiro quando houver caracteristicas iguais")
    public void test3(List<CaracteristicaRequest> caracteristicas) {
        var produtoRequest = new ProdutoRequest("notebook", BigDecimal.TEN,10, "descrição", 1L, caracteristicas);
        Assertions.assertTrue(produtoRequest.temCaracteristicasIguais());
    }

    static Stream<Arguments> geradorTest3() {
        return Stream.of(
                // 2 caracteristicas iguais
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome3", "descricao3"))),
                //3 caracteristicas iguais
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2"),
                                new CaracteristicaRequest("nome2", "descricao3"),
                                new CaracteristicaRequest("nome2", "descricao3")))
                );
    }

    @ParameterizedTest
    @MethodSource("geradorTest4")
    @DisplayName("O metodo temCaracteristicasIguais deve retornar falso quando não houver caracteristicas iguais")
    public void test4(List<CaracteristicaRequest> caracteristicas) {
        var produtoRequest = new ProdutoRequest("notebook", BigDecimal.TEN,10, "descrição", 1L, caracteristicas);
        Assertions.assertFalse(produtoRequest.temCaracteristicasIguais());
    }

    static Stream<Arguments> geradorTest4() {
        return Stream.of(
                // 2 caracteristicas iguais
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2"),
                                new CaracteristicaRequest("nome3", "descricao3"))),
                //3 caracteristicas iguais
                Arguments.of(
                        List.of(new CaracteristicaRequest("nome1", "descricao1"),
                                new CaracteristicaRequest("nome2", "descricao2"),
                                new CaracteristicaRequest("nome3", "descricao3"),
                                new CaracteristicaRequest("nome4", "descricao4")))
        );
    }

}
