package br.com.zupperacademy.ranyell.mercadolivre.produto.cadastro;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProibeCaracteristicasIguais implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProdutoRequest.class.isAssignableFrom(clazz);
    }

    /*
     * Verifica na coleção de caracteristicas do produto possui caracteristicas com mesmo nome
     * */
    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) return;
        ProdutoRequest request = (ProdutoRequest) target;
        if(request.temCaracteristicasIguais()) {
            errors.rejectValue("caracteristicas", null, "O produto não pode possuir caracteristocas com o mesmo nome");
        }
    }
}
