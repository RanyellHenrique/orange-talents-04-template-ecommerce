package br.com.zupperacademy.ranyell.mercadolivre.fakes;

public class NotaFiscalRequest {

    private Long compraId;
    private Long transacaoId;

    public NotaFiscalRequest(Long compraId, Long transacaoId) {
        this.compraId = compraId;
        this.transacaoId = transacaoId;
    }

    public Long getCompraId() {
        return compraId;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }
}
