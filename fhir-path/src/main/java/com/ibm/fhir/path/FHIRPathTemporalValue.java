/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.path;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

import com.ibm.fhir.path.util.FHIRPathUtil.TimePrecision;

public interface FHIRPathTemporalValue extends FHIRPathSystemValue {
    @Override
    default boolean isTemporalValue() {
        return true;
    }
    
    default boolean isDateValue() {
        return false;
    }
    
    default boolean isDateTimeValue() {
        return false;
    }
    
    default boolean isTimeValue() {
        return false;
    }
    
    TemporalAccessor temporalAccessor();
    Temporal temporal();
    default TimePrecision timePrecision() {
        return TimePrecision.NONE;
    }
    
    default FHIRPathDateValue asDateValue() {
        return as(FHIRPathDateValue.class);
    }
    
    default FHIRPathDateTimeValue asDateTimeValue() {
        return as(FHIRPathDateTimeValue.class);
    }
    
    default FHIRPathTimeValue asTimeValue() {
        return as(FHIRPathTimeValue.class);
    }
    
    FHIRPathTemporalValue add(FHIRPathQuantityValue quantityValue);
    FHIRPathTemporalValue subtract(FHIRPathQuantityValue quantityValue);
}