package br.com.zupperacademy.ranyell.mercadolivre.fakes;

public class RankingRequest {

    private Long compraId;
    private Long transacaoId;

    public RankingRequest(Long compraId, Long transacaoId) {
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
