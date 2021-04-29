package br.com.zupperacademy.ranyell.mercadolivre.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistValid.class)
public @interface ExistValue {

    String message() default "Valor não existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> classDomain();
    String fieldName();
}