package br.com.zupperacademy.ranyell.mercadolivre.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
class CadastroUsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    void deveCadastrarUmNovoUsuario() throws Exception {
        var request = new UsuarioRequest("email@email.com", "123456");
        var json = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post("/usuarios")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        var usuario = usuarioRepository.findByEmail(request.getEmail());
        assertEquals(request.getEmail(), usuario.getUsername());
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void naoDeveCadastrarUmNovoUsuarioQuandoOEmailJaExistir() throws Exception{
        var request = new UsuarioRequest("email@email.com", "123456");
        var usuarioExistente = usuarioRepository.save(request.toModel());
        var json = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post("/usuarios")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        var usuario = usuarioRepository.findByEmail(request.getEmail());
        assertEquals(request.getEmail(), usuario.getUsername());
        assertEquals(1, usuarioRepository.count());
    }
}