package io.sandark.vehiclecrud.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

class CountryIsoCodeValidatorTest {

    private final CountryIsoCodeValidator validator = new CountryIsoCodeValidator();
    private final ConstraintValidatorContext context = null;

    @Test
    void isValid_forProperCountryCode() {
        Assertions.assertThat(validator.isValid("DE", context)).isTrue();
        Assertions.assertThat(validator.isValid("EE", context)).isTrue();
        Assertions.assertThat(validator.isValid("ES", context)).isTrue();
        Assertions.assertThat(validator.isValid("RU", context)).isTrue();
        Assertions.assertThat(validator.isValid("FI", context)).isTrue();
        Assertions.assertThat(validator.isValid("FR", context)).isTrue();
    }

    @Test
    void isValid_ifNull() {
        Assertions.assertThat(validator.isValid(null, context)).isTrue();
    }

    @Test
    void isNotValid_forCountryCodeLongerThen2() {
        Assertions.assertThat(validator.isValid("DEE", context)).isFalse();
        Assertions.assertThat(validator.isValid("EST", context)).isFalse();
        Assertions.assertThat(validator.isValid("ESP", context)).isFalse();
        Assertions.assertThat(validator.isValid("RUS", context)).isFalse();
        Assertions.assertThat(validator.isValid("FIN", context)).isFalse();
        Assertions.assertThat(validator.isValid("FRA", context)).isFalse();
    }

    @Test
    void isNotValid_forWrongCountryCodes() {
        Assertions.assertThat(validator.isValid("GX", context)).isFalse();
        Assertions.assertThat(validator.isValid("BX", context)).isFalse();
        Assertions.assertThat(validator.isValid("AJ", context)).isFalse();
        Assertions.assertThat(validator.isValid("FX", context)).isFalse();
        Assertions.assertThat(validator.isValid("XX", context)).isFalse();
        Assertions.assertThat(validator.isValid("LX", context)).isFalse();
    }
}