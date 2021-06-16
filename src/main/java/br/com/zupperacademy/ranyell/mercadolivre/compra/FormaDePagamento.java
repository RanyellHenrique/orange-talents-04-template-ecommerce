package br.com.zupperacademy.ranyell.mercadolivre.compra;

import org.springframework.web.util.UriComponentsBuilder;

public enum FormaDePagamento {
    PAYPAL {
        @Override
        public String pagamento(Compra compra, UriComponentsBuilder uri) {
            var url = uri
                    .path("/retorno-paypal/{id}")
                    .buildAndExpand(compra.getId())
                    .toString();
            return "paypal.com/" + compra.getId() + "&redirectUrl=" + url;
        }
    }, PAGSEGURO {
        @Override
        public String pagamento(Compra compra, UriComponentsBuilder uri) {
            var url = uri
                    .path("/retorno-pagseguro/{id}")
                    .buildAndExpand(compra.getId())
                    .toString();
            return "pagseguro.com/" + compra.getId() + "&redirectUrl=" + url;
        }
    };

    public abstract String pagamento(Compra compra, UriComponentsBuilder uri);
}
