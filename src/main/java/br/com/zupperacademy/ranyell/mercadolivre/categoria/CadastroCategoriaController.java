package br.com.zupperacademy.ranyell.mercadolivre.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CadastroCategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaRequest request) {
        Categoria novaCategoria = request.toModel();
        // busca e adiciona ao model a categoria mãe caso tenha um id da categoria mãe na requisição
        if(request.getCategoriaMaeId() != null) {
            novaCategoria.setCategoriaMae(repository.getOne(request.getCategoriaMaeId()));
        }
        repository.save(novaCategoria);
        return  ResponseEntity.ok().build();
    }
}
