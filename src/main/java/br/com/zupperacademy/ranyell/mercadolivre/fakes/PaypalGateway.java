package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fake/retorno-paypal")
public class PaypalGateway {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostMapping("/{id}")
    public ResponseEntity<PaypalGatewayResponse> pagamento(@PathVariable Long id) {
        LOGGER.info("Processando Transação id: {}", id);
        if (id % 3 == 0) {
            return ResponseEntity.ok(new PaypalGatewayResponse(id, 1));
        }
        return ResponseEntity.ok(new PaypalGatewayResponse(id, 0));
    }

}
