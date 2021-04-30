package br.com.zupperacademy.ranyell.mercadolivre.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository  extends JpaRepository<Perfil, Long> {
}
