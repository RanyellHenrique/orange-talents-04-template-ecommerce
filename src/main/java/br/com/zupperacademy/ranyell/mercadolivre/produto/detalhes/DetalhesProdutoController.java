package br.com.zupperacademy.ranyell.mercadolivre.produto.detalhes;

import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class DetalhesProdutoController {

    private ProdutoRepository produtoRepository;

    @Autowired
    public DetalhesProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesProdutoResponse> detalhes(@PathVariable Long id) {
        var possivelProduto = produtoRepository.findById(id);
        if(possivelProduto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var produto = possivelProduto.get();
        var mediaDeNotas = produtoRepository.mediaDeNotasPorProduto(1L);
        var totalDeNotas = produtoRepository.totalDeNotasPorProduto(1L);
        return ResponseEntity.ok(new DetalhesProdutoResponse(produto, mediaDeNotas, totalDeNotas));
    }
}
