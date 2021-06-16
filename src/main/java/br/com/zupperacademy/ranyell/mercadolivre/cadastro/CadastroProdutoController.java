package br.com.zupperacademy.ranyell.mercadolivre.cadastro;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.CategoriaRepository;
import br.com.zupperacademy.ranyell.mercadolivre.produto.Produto;
import br.com.zupperacademy.ranyell.mercadolivre.produto.ProdutoRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CadastroProdutoController {

    private ProdutoRepository repository;
    private CategoriaRepository categoriaRepository;
    private ProibeCaracteristicasIguais proibeCaracteristicasIguais;

    @Autowired
    public CadastroProdutoController(ProdutoRepository repository, CategoriaRepository categoriaRepository,
                                     ProibeCaracteristicasIguais proibeCaracteristicasIguais) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.proibeCaracteristicasIguais = proibeCaracteristicasIguais;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(proibeCaracteristicasIguais);
    }

    @PostMapping
    @Transactional
    private ResponseEntity<Void> insert(@Valid @RequestBody ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {
        var categoria = categoriaRepository.findById(request.getCategoriaId()).get();
        Produto produto = request.toModel(categoria, usuario);
        repository.save(produto);
        return  ResponseEntity.ok().build();
    }
}
