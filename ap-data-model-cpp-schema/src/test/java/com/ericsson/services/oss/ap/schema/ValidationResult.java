/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2020
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.services.oss.ap.schema;

/**
 * POJO to store the result of a schema validation
 */
public class ValidationResult {

    private String errorMessage;
    private boolean validationSuccessful;

    public ValidationResult(final boolean validationSuccessful, final String errorMessage) {
        this.validationSuccessful = validationSuccessful;
        this.errorMessage = errorMessage;
    }

    /**
     * Returns true when schema validation was successful
     */
    public boolean isValid() {
        return validationSuccessful;
    }

    /**
     * The error message describling why the schema validation failed. null means the validation was successful
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}