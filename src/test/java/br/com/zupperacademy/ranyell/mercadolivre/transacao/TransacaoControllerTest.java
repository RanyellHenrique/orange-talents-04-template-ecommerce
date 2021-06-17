package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.Categoria;
import br.com.zupperacademy.ranyell.mercadolivre.categoria.CategoriaRepository;
import br.com.zupperacademy.ranyell.mercadolivre.compra.Compra;
import br.com.zupperacademy.ranyell.mercadolivre.compra.CompraRepository;
import br.com.zupperacademy.ranyell.mercadolivre.compra.FormaDePagamento;
import br.com.zupperacademy.ranyell.mercadolivre.fakes.*;
import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro.CaracteristicaRequest;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
class TransacaoControllerTest {

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

    @Autowired
    CompraRepository compraRepository;

    @MockBean
    PagseguroClient pagseguroClient;

    @MockBean
    NotaFiscalClient notaFiscalClient;

    @MockBean()
    RankingClient rankingClient;

    Usuario usuario;
    String token;
    Categoria categoria;
    Produto produto;
    Compra compraPagseguro;
    Compra compraPaypal;


    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("will@email.com", "123456");
        usuarioRepository.save(usuario);
        token = obtainAccessToken(usuario.getUsername(), "123456");
        produto = produtoRepository.save(novoProduto());
        categoria = categoriaRepository.save(new Categoria("Informatica"));
        compraPagseguro = compraRepository.save(new Compra(FormaDePagamento.PAGSEGURO, 2, new BigDecimal("4500.0"), produto, usuario));
        compraPaypal = compraRepository.save(new Compra(FormaDePagamento.PAYPAL, 2, new BigDecimal("4500.0"), produto, usuario));
    }

    @AfterEach
    void clearUp() {
        compraRepository.deleteAll();
        produtoRepository.deleteAll();
        categoriaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveCadastrarUmaNovaTransacao() throws Exception {
        when(pagseguroClient.transacao(any())).thenReturn(ResponseEntity.ok(new PagSeguroGatewayResponse(compraPagseguro.getId(), "SUCESSO")));
        when(notaFiscalClient.processa(any())).thenReturn(ResponseEntity.ok(true));
        when(rankingClient.processa(any())).thenReturn(ResponseEntity.ok(true));

        mockMvc.perform(post("/compras/" + compraPagseguro.getId() + "/transacoes/" + 1)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveCadastrarUmaNovaTransacaoQuandoACompraNaoExistir() throws Exception {
        mockMvc.perform(post("/compras/" + Long.MAX_VALUE + "/transacoes/" + 1)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
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

    private Produto novoProduto() {
        var caracteristicas = List.of(
                new CaracteristicaRequest("Peso", "2 KG"),
                new CaracteristicaRequest("Tamanho", "50cm x 50cm x 3cm"),
                new CaracteristicaRequest("Cor", "Cinza"));

        return new Produto("Pc Gamer", BigDecimal.TEN, 25,
                "Descrição", categoria, usuario, caracteristicas);
    }

}