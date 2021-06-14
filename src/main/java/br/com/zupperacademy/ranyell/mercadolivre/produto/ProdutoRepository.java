package br.com.zupperacademy.ranyell.mercadolivre.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsById(Long id);

    @Query("SELECT AVG(o.nota) FROM Opiniao o WHERE  o.produto.id = :id")
    Double mediaDeNotasPorProduto(Long id);

    @Query("SELECT COUNT(o.nota) FROM Opiniao o WHERE  o.produto.id = :id")
    Long totalDeNotasPorProduto(Long id);
}
