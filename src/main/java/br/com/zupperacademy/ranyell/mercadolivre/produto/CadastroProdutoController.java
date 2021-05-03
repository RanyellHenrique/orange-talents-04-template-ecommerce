package br.com.zupperacademy.ranyell.mercadolivre.produto;

import br.com.zupperacademy.ranyell.mercadolivre.categoria.CategoriaRepository;
import br.com.zupperacademy.ranyell.mercadolivre.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class CadastroProdutoController {

    private ProdutoRepository repository;
    private CaracteristicaRepository caracteristicaRepository;
    private CategoriaRepository categoriaRepository;

    public CadastroProdutoController(ProdutoRepository repository, CaracteristicaRepository caracteristicaRepository,
                                     CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.categoriaRepository = categoriaRepository;
    }
    /*
    *
    * 
    * */
    @PostMapping
    @Transactional
    private ResponseEntity<Void> insert(@Valid @RequestBody ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {
        var categoria = categoriaRepository.findById(request.getCategoriaId()).get();
        Produto produto = request.toModel(categoria, usuario);
        repository.save(produto);
        var caracteristicas = request.getCaracteristicas().stream()
                .map(c -> c.toModel(produto))
                .map(c -> caracteristicaRepository.save(c))
                .collect(Collectors.toSet());
        return  ResponseEntity.ok().build();
    }
}
