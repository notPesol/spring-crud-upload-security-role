package com.pesol.spring.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE} )
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {

	String message() default "Fields values don't match!";
	
	String first();
	
	String second();
	
	@interface List {
		
		FieldMatch[] values();
	}
}
