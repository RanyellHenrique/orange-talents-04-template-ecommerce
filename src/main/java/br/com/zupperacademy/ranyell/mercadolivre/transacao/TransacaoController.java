package br.com.zupperacademy.ranyell.mercadolivre.transacao;

import br.com.zupperacademy.ranyell.mercadolivre.compra.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@RequestMapping("/compras")
public class TransacaoController {

    private CompraRepository compraRepository;
    private Map<String, RetornoGatewayPagamento> gatewaysPagamento;
    private EventosCompraSucesso eventosCompraSucesso;

    @Autowired
    public TransacaoController(CompraRepository compraRepository, Map<String, RetornoGatewayPagamento> gatewaysPagamento, EventosCompraSucesso eventosCompraSucesso) {
        this.compraRepository = compraRepository;
        this.gatewaysPagamento = gatewaysPagamento;
        this.eventosCompraSucesso = eventosCompraSucesso;
    }

    @PostMapping("{compraId}/transacoes/{transacaoId}")
    @Transactional
    public ResponseEntity<?> transacao(@PathVariable Long compraId, @PathVariable Long transacaoId) {
        var possivelCompra = compraRepository.findById(compraId);
        if (possivelCompra.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var compra = possivelCompra.get();
        Transacao transacao = gatewaysPagamento.get(compra.getFormaDePagamento().name()).toTransacao(compra, transacaoId);
        compra.addTransacao(transacao);
        compraRepository.save(compra);
        eventosCompraSucesso.processa(compra);
        return ResponseEntity.ok().build();
    }
}
