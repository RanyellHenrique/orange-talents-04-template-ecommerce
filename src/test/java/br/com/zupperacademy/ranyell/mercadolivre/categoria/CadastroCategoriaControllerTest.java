package br.com.zupperacademy.ranyell.mercadolivre.categoria;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
class CadastroCategoriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    Usuario usuario;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("will@gmail.com", "123456");
        usuarioRepository.save(usuario);
        token = obtainAccessToken(usuario.getUsername(), "123456");
    }

    @AfterEach
    void clearUp() {
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveCriarUmaNovaCategoria() throws Exception {
        CategoriaRequest request = new CategoriaRequest("informatica");
        String json = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post("/categorias")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json))
                .andExpect(status().isOk());

        var categoria = categoriaRepository.findByNome(request.getNome());

        assertEquals(request.getNome(), categoria.getNome());
        assertEquals(1, categoriaRepository.count());
    }

    @Test
    void deveCriarUmaNovaCategoriaQuandoForInformadaUmaCategoriaMaeValida() throws Exception {
        Categoria categoriaMae = categoriaRepository.save(new Categoria("Eletrônica"));
        CategoriaRequest request = new CategoriaRequest("informatica");
        request.setCategoriaMaeId(categoriaMae.getId());
        String json = jsonMapper.writeValueAsString(request);
        //ação e validação
        mockMvc.perform(post("/categorias")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json))
                .andExpect(status().isOk());

        var categoria = categoriaRepository.findByNome(request.getNome());

        assertEquals(request.getNome(), categoria.getNome());
        assertEquals(2, categoriaRepository.count());
        assertEquals(request.getCategoriaMaeId(), categoria.getCategoriaMae().getId());
    }

    @Test
    void naoDeveCriarUmaCategoriaQuandoOsAtributosForemInvalidos() throws Exception {
        CategoriaRequest request = new CategoriaRequest("");
        String json = jsonMapper.writeValueAsString(request);
        //ação e validação
        mockMvc.perform(post("/categorias")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json))
                .andExpect(status().isBadRequest());
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