/**
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.model.path.function;

import static com.ibm.watsonhealth.fhir.model.path.evaluator.FHIRPathEvaluator.SINGLETON_FALSE;
import static com.ibm.watsonhealth.fhir.model.path.evaluator.FHIRPathEvaluator.SINGLETON_TRUE;
import static com.ibm.watsonhealth.fhir.model.path.util.FHIRPathUtil.empty;
import static com.ibm.watsonhealth.fhir.model.path.util.FHIRPathUtil.getStringValue;

import java.util.Collection;
import java.util.List;

import com.ibm.watsonhealth.fhir.model.path.FHIRPathNode;
import com.ibm.watsonhealth.fhir.model.path.FHIRPathStringValue;

public class MatchesFunction extends FHIRPathAbstractFunction {
    @Override
    public String getName() {
        return "matches";
    }

    @Override
    public int getMinArity() {
        return 1;
    }

    @Override
    public int getMaxArity() {
        return 1;
    }
    
    @Override
    public Collection<FHIRPathNode> apply(Collection<FHIRPathNode> context, List<Collection<FHIRPathNode>> arguments) {
        if (context.isEmpty()) {
            return empty();
        }
        
        FHIRPathStringValue string = getStringValue(context);
        FHIRPathStringValue regex = getStringValue(arguments.get(0));
        
        return string.matches(regex) ? SINGLETON_TRUE : SINGLETON_FALSE;
    }
}
