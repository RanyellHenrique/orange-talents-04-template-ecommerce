package br.com.zupperacademy.ranyell.mercadolivre.produto.imagem;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class AdicionarImagemControllerTest {

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
    Produto produto;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("will@gmail.com", "123456");
        usuarioRepository.save(usuario);
        token = obtainAccessToken(usuario.getUsername(), "123456");
        caracteristicas = new ArrayList<>();
        categoria = categoriaRepository.save(new Categoria("Informatica"));
        produtoRepository.save(produto);
    }

//    @Test
//    void deveCadastrarUmaNovaimagem() throws Exception {
//        var request = getImagensRequest();
//        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
//        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
//        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/produtos/"+produto.getId()+"/imagens")
//        .file(firstFile))
//        .andExpect(status().isOk());
//    }


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

    private ImagensRequest getImagensRequest() {
        return new ImagensRequest(List.of(
                new MockMultipartFile(
                        "file",
                        "imagem",
                        MediaType.TEXT_PLAIN_VALUE,
                        "sdsa".getBytes(StandardCharsets.UTF_8))));
    }


}