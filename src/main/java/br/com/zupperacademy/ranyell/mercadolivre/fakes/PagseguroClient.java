package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "localhost:8080/fake/retorno-pagseguro", name = "pagseguro-client")
public interface PagseguroClient {

    @PostMapping("/{id}")
    ResponseEntity<PagSeguroGatewayResponse> transacao(@PathVariable Long id);
}
