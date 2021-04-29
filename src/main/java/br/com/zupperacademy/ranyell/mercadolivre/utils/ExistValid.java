package br.com.zupperacademy.ranyell.mercadolivre.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistValid implements ConstraintValidator<ExistValue, Object> {

    private String fieldName;
    private Class<?> classDomain;

    @PersistenceContext
    private EntityManager manager;


    @Override
    public void initialize(ExistValue constraintAnnotation) {
        this.classDomain = constraintAnnotation.classDomain();
        this.fieldName = constraintAnnotation.fieldName();
    }

    /*
     * Método responsável por verificar se o atributo  existe no banco de dados
     * @param value
     * @param context
     */

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) return true;
        Query query = manager.createQuery("select 1 from "+classDomain.getSimpleName()+ " where "+fieldName+ " = :value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        return !list.isEmpty();
    }
}