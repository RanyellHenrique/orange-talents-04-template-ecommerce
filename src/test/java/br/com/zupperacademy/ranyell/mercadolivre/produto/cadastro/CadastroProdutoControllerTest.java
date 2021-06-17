package br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.categoria.CategoriaRepository;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
class CadastroProdutoControllerTest {

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
    List<CaracteristicaRequest> caracteristicas;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("will@email.com", "123456");
        usuarioRepository.save(usuario);
        token = obtainAccessToken(usuario.getUsername(), "123456");

        caracteristicas = new ArrayList<>();

        categoria = categoriaRepository.save(new Categoria("Informatica"));
    }

    @AfterEach
    void clearUp() {
        produtoRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveCriarUmNovoProduto() throws Exception {
        //cenario
        var request = novoProduto();
        String json = jsonMapper.writeValueAsString(request);
        //ação e validação
        mockMvc.perform(post("/produtos")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json))
                .andExpect(status().isOk());

        var produto = produtoRepository.findById(1L).get();
        assertEquals(request.getNome(), produto.getNome());
    }


    @Test
    void naoDeveCadastrarUmNovoProdutoQuandoTiverCaracteristicasIguais() throws Exception {
        //cenario
        var request = novoProduto();
        request.getCaracteristicas().add(new CaracteristicaRequest("Peso", "2KG"));
        String json = jsonMapper.writeValueAsString(request);
        //ação e validação
        mockMvc.perform(post("/produtos")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json))
                .andExpect(status().isBadRequest());
        assertEquals(0, produtoRepository.count());
    }

    private ProdutoRequest novoProduto() {
        caracteristicas.add(new CaracteristicaRequest("Peso", "2 KG"));
        caracteristicas.add(new CaracteristicaRequest("Tamanho", "50cm x 50cm x 3cm"));
        caracteristicas.add(new CaracteristicaRequest("Cor", "Cinza"));

        return new ProdutoRequest(
                "PC Gamer",
                new BigDecimal("4950.00"),
                25,
                "descrição",
                categoria.getId(),
                caracteristicas
        );
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