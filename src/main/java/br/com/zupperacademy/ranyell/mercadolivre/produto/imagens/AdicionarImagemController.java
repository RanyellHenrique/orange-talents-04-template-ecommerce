package br.com.zupperacademy.ranyell.mercadolivre.produto.imagens;

import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class AdicionarImagemController {


    private ProdutoRepository produtoRepository;
    private Uploader uploader;

    @Autowired
    public AdicionarImagemController(ProdutoRepository produtoRepository, Uploader uploader) {
        this.produtoRepository = produtoRepository;
        this.uploader = uploader;
    }

    @PostMapping("/{id}/imagens")
    @Transactional
    public ResponseEntity<Void> insertImage(@Valid ImagensRequest request,
                                            @AuthenticationPrincipal Usuario usuario, @PathVariable("id") Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var produto = produtoRepository.findById(id).get();

        if (!produto.getUsuario().getUsername().equals(usuario.getUsername())) {
            return ResponseEntity.status(403).build();
        }
        request.getImagens().stream()
                .map(imagem -> uploader.upload(imagem))
                .map(url -> new ImagemProduto(url, produto))
                .map(imagem -> {
                    produto.addImagem(imagem);
                    return imagem;
                })
                .collect(Collectors.toList());
        produtoRepository.save(produto);
        return ResponseEntity.ok().build();
    }
}
