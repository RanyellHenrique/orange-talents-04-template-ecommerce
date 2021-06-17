package br.com.zupperacademy.ranyell.mercadolivre.produto.detalhes;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.categoria.CategoriaRepository;
import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro.CaracteristicaRequest;
import br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro.ProdutoRequest;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.UsuarioRepository;
import br.com.zupperacademy.ranyell.mercadolivre.utils.security.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
class DetalhesProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    Usuario usuario;
    String token;
    Categoria categoria;
    Produto produto;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("will@email.com", "123456");
        usuarioRepository.save(usuario);
        token = obtainAccessToken(usuario.getUsername(), "123456");
        produto = produtoRepository.save(novoProduto());
        categoria = categoriaRepository.save(new Categoria("Informatica"));
    }

    @AfterEach
    void clearUp() {
        produtoRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveRetornarUmProduto() throws Exception {
        //ação e validação
        mockMvc.perform(get("/produtos/" + produto.getId())
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(novoProduto().getNome()))
                .andExpect(jsonPath("$.descricao").value(novoProduto().getDescricao()));
    }

    @Test
    void naoDeveRetonarUmProdutoQuandoOIdInformadoNaoExistir() throws Exception {
        mockMvc.perform(get("/produtos/" + Long.MAX_VALUE)
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    private Produto novoProduto() {
        var caracteristicas = List.of(
                new CaracteristicaRequest("Peso", "2 KG"),
                new CaracteristicaRequest("Tamanho", "50cm x 50cm x 3cm"),
                new CaracteristicaRequest("Cor", "Cinza"));

        return new Produto("Pc Gamer", BigDecimal.TEN, 25,
                "Descrição", categoria, usuario, caracteristicas);
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        var request = new LoginRequest(username, password);
        String json = jsonMapper.writeValueAsString(request);

        ResultActions result
                = mockMvc.perform(post("/auth")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }
}