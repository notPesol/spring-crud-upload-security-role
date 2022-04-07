package com.pesol.spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	String firstField;
	
	String secondField;
	
	String message;
	
	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		firstField = constraintAnnotation.first();
		secondField = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}
	
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		Object firstValue = new BeanWrapperImpl(value).getPropertyValue(firstField);
		Object secondValue = new BeanWrapperImpl(value).getPropertyValue(secondField);
		
		if (firstValue != null) {
			return firstValue.equals(secondValue);
		}
		
		return false;
	}

}
