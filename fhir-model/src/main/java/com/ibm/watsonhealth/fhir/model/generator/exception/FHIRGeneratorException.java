/**
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.model.generator.exception;

import com.ibm.watsonhealth.fhir.exception.FHIRException;

public class FHIRGeneratorException extends FHIRException {
    private static final long serialVersionUID = 1L;
    protected final String path;

    public FHIRGeneratorException(String message, String path, Throwable cause) {
        super(message, cause);
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
