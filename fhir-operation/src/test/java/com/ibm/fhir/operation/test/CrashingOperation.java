/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.operation.test;

import com.ibm.fhir.exception.FHIROperationException;
import com.ibm.fhir.model.resource.OperationDefinition;
import com.ibm.fhir.model.resource.Parameters;
import com.ibm.fhir.model.resource.Resource;
import com.ibm.fhir.operation.AbstractOperation;
import com.ibm.fhir.operation.context.FHIROperationContext;
import com.ibm.fhir.rest.FHIRResourceHelpers;

/**
 * This class will test what happens if there is an Operation that fails to initialize.
 * There is no corresponding testcase as the Java ServiceLoader (SPI) mechanism 
 * will automatically load this service if it is configured as a service provider and available on the classpath.
 * The expected result is:
 * 1. to see an error/message explaining why this service was not loaded
 * 2. for other operations to continue working
 * @author lmsurpre
 */
public class CrashingOperation extends AbstractOperation {
    @Override
    protected OperationDefinition buildOperationDefinition() {
        throw new RuntimeException("Testing an operation that fails to initialize");
    }

    /* (non-Javadoc)
     * @see com.ibm.fhir.operation.AbstractOperation#doInvoke(com.ibm.fhir.operation.context.FHIROperationContext, java.lang.Class, java.lang.String, java.lang.String, com.ibm.fhir.model.Parameters, com.ibm.fhir.rest.FHIRResourceHelpers)
     */
    @Override
    protected Parameters doInvoke(FHIROperationContext operationContext, Class<? extends Resource> resourceType, String logicalId, String versionId,
        Parameters parameters, FHIRResourceHelpers resourceHelper) throws FHIROperationException {
        // do nothing
        return null;
    }
}
