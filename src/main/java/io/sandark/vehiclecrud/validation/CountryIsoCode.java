package io.sandark.vehiclecrud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that String is a proper ISO 3166-1 alpha-2 country code.
 * Value should be the exact length of 2 and contained in the list of ISO country codes
 * <p>
 * For instance DE is a valid country code for Germany, EE - Estonia. While
 * GE is not a valid code Germany, and EST is not valid for Estonia.
 *
 * @author Anton Verchenko
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryIsoCodeValidator.class)
public @interface CountryIsoCode {

    String message() default "io.sandark.CountryIsoCode.message";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
