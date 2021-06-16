package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagSeguroGatewayResponse {

    @NotBlank
    private Long idTransacao;
    @NotNull
    private String status;

    @Deprecated
    public PagSeguroGatewayResponse() {
    }

    public PagSeguroGatewayResponse(Long idTransacao, String status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public Long getIdTransacao() {
        return idTransacao;
    }

    public String getStatus() {
        return status;
    }
}

