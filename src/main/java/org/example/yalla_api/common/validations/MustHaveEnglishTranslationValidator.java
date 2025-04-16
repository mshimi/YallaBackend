package org.example.yalla_api.common.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.yalla_api.common.enums.Language;

import java.util.Collection;

public class MustHaveEnglishTranslationValidator
        implements ConstraintValidator<MustHaveEnglishTranslation, Collection<? extends TranslationEntry>> {

    @Override
    public boolean isValid(Collection<? extends TranslationEntry> translations, ConstraintValidatorContext context) {
        if (translations == null || translations.isEmpty()) return false;

        return translations.stream().anyMatch(t -> t.getLang() == Language.EN);
    }
}
