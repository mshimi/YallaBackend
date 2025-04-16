package org.example.yalla_api.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MustHaveEnglishTranslationValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MustHaveEnglishTranslation {

    String message() default "English translation (Language.EN) is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
