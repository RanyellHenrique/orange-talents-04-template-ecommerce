package br.com.zupperacademy.ranyell.mercadolivre.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class CadastroUsuarioController {

    private UsuarioRepository repository;
    private PerfilRepository perfilRepository;

    @Autowired
    public CadastroUsuarioController(UsuarioRepository repository, PerfilRepository perfilRepository) {
        this.repository = repository;
        this.perfilRepository = perfilRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioRequest request) {
        var novoUsuario = request.toModel();
        novoUsuario.addPerfil(perfilRepository.getOne(1L));
        repository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
