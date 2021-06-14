package br.com.zupperacademy.ranyell.mercadolivre.produto.opiniao;

import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CadastroOpiniaoController {

    private ProdutoRepository produtoRepository;

    @Autowired
    public CadastroOpiniaoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/{id}/opinioes")
    public ResponseEntity<?> cadastraOpiniao(@Valid @RequestBody OpiniaoRequest request, @PathVariable Long id,
                                             @AuthenticationPrincipal Usuario usuario) {
        var possivelProduto = produtoRepository.findById(id);
        if(possivelProduto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var produto = possivelProduto.get();
        produto.addOpiniao(request.toModel(usuario, produto));
        produtoRepository.save(produto);
        return ResponseEntity.ok().build();
    }
}
