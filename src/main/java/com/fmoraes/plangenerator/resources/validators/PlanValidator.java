/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmoraes.plangenerator.resources.validators;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author fmoraes
 */
@ApplicationScoped
public class PlanValidator implements ConstraintValidator<ValidPlan, JsonObject> {

    private static final String POSITIVE_NUMBER_REGEX = "\\d+(\\.\\d+)?";

    @Override
    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {
        String loanAmount = json.getString("loanAmount", null);
        String nominalRate = json.getString("nominalRate", null);
        int duration = json.getInt("duration", -1);
        String startDate = json.getString("startDate", null);
        int errors = 0;

        context.disableDefaultConstraintViolation();
        if (loanAmount == null) {
            context.buildConstraintViolationWithTemplate("cannot be null")
                    .addPropertyNode("loanAmount")
                    .addConstraintViolation();
            errors++;
        } else if (!loanAmount.matches(POSITIVE_NUMBER_REGEX)) {
            context.buildConstraintViolationWithTemplate("must be a positive number")
                    .addPropertyNode("loanAmount")
                    .addConstraintViolation();
            errors++;
        }

        if (nominalRate == null) {
            context.buildConstraintViolationWithTemplate("cannot be null")
                    .addPropertyNode("nominalRate")
                    .addConstraintViolation();
            errors++;
        } else if (!nominalRate.matches(POSITIVE_NUMBER_REGEX)) {
            context.buildConstraintViolationWithTemplate("must be a positive number")
                    .addPropertyNode("nominalRate")
                    .addConstraintViolation();
            errors++;
        }

        if (startDate == null) {
            context.buildConstraintViolationWithTemplate("cannot be null")
                    .addPropertyNode("startDate")
                    .addConstraintViolation();
            errors++;
        } else {
            try {
                ZonedDateTime.parse(startDate);
            } catch (DateTimeParseException e) {
                context.buildConstraintViolationWithTemplate("unable to parse date")
                        .addPropertyNode("startDate")
                        .addConstraintViolation();
                errors++;
            }
        }

        if (duration < 0) {
            context.buildConstraintViolationWithTemplate("must be bigger than zero")
                    .addPropertyNode("duration")
                    .addConstraintViolation();

            errors++;
        }

        return errors == 0;
    }

}
