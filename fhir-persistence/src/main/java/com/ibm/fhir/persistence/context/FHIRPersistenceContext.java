/*
 * (C) Copyright IBM Corp. 2016,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.context;

import com.ibm.fhir.persistence.interceptor.FHIRPersistenceEvent;
import com.ibm.fhir.search.context.FHIRSearchContext;

/**
 * This interface is used to provide request context-related information
 * to the FHIR Server persistence layer.
 */
public interface FHIRPersistenceContext {
    
    /**
     * Returns the FHIRPersistenceEvent instance for the current request.
     * This contains information about the security context, HTTP headers,
     * request URI information, etc.
     */
    FHIRPersistenceEvent getPersistenceEvent();
    
    /**
     * Returns the FHIRHistoryContext instance associated with the current request.
     * This will be null if the current request is not a 'history' operation.
     */
    FHIRHistoryContext getHistoryContext();
    
    /**
     * Returns the FHIRSearchContext instance associated with the current request.
     * This will be null if the current request is not a 'search' operation.
     */
    FHIRSearchContext getSearchContext();
    
    /**
     * Indicates whether the persistence layer should include "deleted" resources in the operation response.
     */
    boolean includeDeleted();
}
