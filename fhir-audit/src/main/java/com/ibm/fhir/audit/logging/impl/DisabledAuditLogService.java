/*
 * (C) Copyright IBM Corp. 2016,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.audit.logging.impl;

import com.ibm.fhir.audit.logging.api.AuditLogService;
import com.ibm.fhir.audit.logging.beans.AuditLogEntry;
import com.ibm.fhir.config.PropertyGroup;

/**
 * This class serves as a no-op audit service logger. It simply allows audit logging 
 * to be effectively disabled.
 */
public class DisabledAuditLogService implements AuditLogService {

    public DisabledAuditLogService() {
        super();
    }

    @Override
    public void logEntry(AuditLogEntry logEntry) {
        // No Operation
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void initialize(PropertyGroup auditLogProperties) throws Exception {
         // No Operation
    }

}
