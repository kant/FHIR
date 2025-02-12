/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.ig.us.core;

import java.util.Collection;

import com.ibm.fhir.model.format.Format;
import com.ibm.fhir.registry.resource.FHIRRegistryResource;
import com.ibm.fhir.registry.spi.FHIRRegistryResourceProvider;
import com.ibm.fhir.registry.util.FHIRRegistryUtil;

public class USCoreResourceProvider implements FHIRRegistryResourceProvider {
    @Override
    public Collection<FHIRRegistryResource> getResources() {
        return FHIRRegistryUtil.getResources(Format.JSON, getClass().getClassLoader(), "us-core.index");
    }
}