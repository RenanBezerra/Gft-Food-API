package com.example.demo.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {

	@OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
	String message() default "descrição obrigatória inválida";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valorField();
	
	String descricaoField();
	
	String descricaoObrigatoria();
	
}
