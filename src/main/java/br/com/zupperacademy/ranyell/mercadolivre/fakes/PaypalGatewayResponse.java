package br.com.zupperacademy.ranyell.mercadolivre.fakes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalGatewayResponse {

    @NotBlank
    private Long idTransacao;
    @NotNull
    private Integer status;

    @Deprecated
    public PaypalGatewayResponse() {
    }

    public PaypalGatewayResponse(Long idTransacao, Integer status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public Long getIdTransacao() {
        return idTransacao;
    }

    public Integer getStatus() {
        return status;
    }
}

