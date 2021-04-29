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

    @Autowired
    private  UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioRequest request) {
        var novoUsuario = request.toModel();
        repository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
