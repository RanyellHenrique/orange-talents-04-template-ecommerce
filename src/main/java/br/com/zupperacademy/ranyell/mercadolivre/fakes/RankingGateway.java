package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fake/ranking")
public class RankingGateway {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<Boolean> ranking(@RequestBody NotaFiscalRequest request) {
        LOGGER.info("Chamando o sistema de Ranking da compra {}", request.getCompraId());
        return  ResponseEntity.ok().body(true);
    }
}
