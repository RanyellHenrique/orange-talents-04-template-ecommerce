package br.com.zupperacademy.ranyell.mercadolivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

import java.time.Instant;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String senha;
    private Instant criadoEm;

    @Deprecated
    public Usuario() {
    }
    public Usuario( String email, String senha) {
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        criadoEm = Instant.now();
    }
}
