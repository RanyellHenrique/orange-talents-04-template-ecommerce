package br.com.zupperacademy.ranyell.mercadolivre.produto.pergunta;

import br.com.zupperacademy.ranyell.mercadolivre.fakes.EnviaEmail;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CadastroPerguntaController {

    private ProdutoRepository produtoRepository;
    private EnviaEmail enviaEmail;

    public CadastroPerguntaController(ProdutoRepository produtoRepository, EnviaEmail enviaEmail) {
        this.produtoRepository = produtoRepository;
        this.enviaEmail = enviaEmail;
    }

    @PostMapping("/{id}/perguntas")
    @Transactional
    public ResponseEntity<?> cadastra(@Valid @RequestBody PerguntaRequest request, @PathVariable Long id,
                                      @AuthenticationPrincipal Usuario usuario) {
        var possivelProduto = produtoRepository.findById(id);
        if(possivelProduto.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        var produto  = possivelProduto.get();
        produto.addPergunta(request.toModel(usuario, produto));
        produtoRepository.save(produto);
        enviaEmail.enviar(produto.getUsuario());
        return ResponseEntity.ok().build();
    }
}
