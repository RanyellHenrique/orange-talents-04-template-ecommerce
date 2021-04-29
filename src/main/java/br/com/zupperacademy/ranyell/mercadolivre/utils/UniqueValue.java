package br.com.zupperacademy.ranyell.mercadolivre.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValid.class)
public @interface UniqueValue {

    String message() default "Valor deve ser único";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> classDomain();
    String fieldName();
}