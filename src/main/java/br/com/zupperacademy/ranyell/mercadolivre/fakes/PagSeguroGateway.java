package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fake/retorno-pagseguro")
public class PagSeguroGateway {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostMapping("/{id}")
    public ResponseEntity<PagSeguroGatewayResponse> pagamento(@PathVariable Long id) {
        LOGGER.info("Processando transação id: {}", id);
        if (id % 3 == 0) {
            return ResponseEntity.ok(new PagSeguroGatewayResponse(id, "SUCESSO"));
        }
        return  ResponseEntity.ok(new PagSeguroGatewayResponse(id, "ERRO"));
    }

}
