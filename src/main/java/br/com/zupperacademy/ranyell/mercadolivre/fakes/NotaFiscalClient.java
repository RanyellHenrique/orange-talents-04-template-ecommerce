package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "localhost:8080/fake/notas-fiscais", name = "Nota-fiscal-client")
public interface NotaFiscalClient {
    @PostMapping
    ResponseEntity<Boolean> processa(@RequestBody NotaFiscalRequest request);
}
