package br.com.zupperacademy.ranyell.mercadolivre.compra;

import br.com.zupperacademy.ranyell.mercadolivre.fakes.EnviaEmailFake;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private ProdutoRepository produtoRepository;
    private EnviaEmailFake enviaEmailFake;
    private CompraRepository compraRepository;

    @Autowired
    public CompraController(ProdutoRepository produtoRepository, EnviaEmailFake enviaEmailFake,
                            CompraRepository compraRepository) {
        this.produtoRepository = produtoRepository;
        this.enviaEmailFake = enviaEmailFake;
        this.compraRepository = compraRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> compra(@Valid @RequestBody CompraRequest request,
                                    @AuthenticationPrincipal Usuario usuario,
                                    UriComponentsBuilder uriBuilder) throws BindException {
        var possivelProduto = produtoRepository.findById(request.getProdutoId());

        if (possivelProduto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var produto = possivelProduto.get();
        var abate = produto.abateEstoque(request.getQuantidade());
        if (abate) {
            Compra compra = compraRepository.save(request.toModel(usuario, produto));
            enviaEmailFake.enviar(produto.getUsuario());
            return ResponseEntity.ok(compra.urlRedirecionamento(uriBuilder));
        }
        var problemaCompraRequest = new BindException(request, "compraRequest");
        problemaCompraRequest.reject(null, "A quantidade em estoque é menor que a quantidade requerida");
        throw problemaCompraRequest;
    }
}
