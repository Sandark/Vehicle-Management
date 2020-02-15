package io.sandark.vehiclecrud.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CountryIsoCodeValidator implements ConstraintValidator<CountryIsoCode, String> {

    private static final List<String> validCountryCodes = Arrays.asList(Locale.getISOCountries());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || (value.length() == 2 && validCountryCodes.contains(value));
    }
}
