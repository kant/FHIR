/**
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.examples;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import com.ibm.watsonhealth.fhir.model.builder.Builder;
import com.ibm.watsonhealth.fhir.model.type.Element;
import com.ibm.watsonhealth.fhir.model.type.Identifier;
import com.ibm.watsonhealth.fhir.model.type.Narrative;
import com.ibm.watsonhealth.fhir.model.type.Reference;
import com.ibm.watsonhealth.fhir.model.util.JsonSupport;

public class MinimalDataCreator extends DataCreatorBase {

    public MinimalDataCreator() throws IOException {
        super();
    }

    @Override
    protected Builder<?> addData(Builder<?> builder, int choiceIndicator) throws Exception {
        Method[] methods = builder.getClass().getDeclaredMethods();
        
        boolean empty = true;
        for (Method method : methods) {
            String name = reverseJavaEncoding(method.getName());
            
            if (isRequiredElement(builder.getClass().getEnclosingClass(), name)) {
                
                Class<?>[] parameterClasses = method.getParameterTypes();
                
                if (parameterClasses.length != 1) {
                    throw new RuntimeException("Error adding data via builder " + builder.getClass() + "; expected 1 parameter, but found " + parameterClasses.length);
                }
                
                Class<?> parameterType = parameterClasses[0];
                // Special case to avoid infinite recursion
                if (builder instanceof Identifier.Builder && Reference.class.isAssignableFrom(parameterType)) {
                    continue;
                }
                
                // Special case for Narrative
                if (builder instanceof Narrative.Builder && method.getName().equals("div")) {
                    ((Narrative.Builder) builder).div("<div xmlns=\"http://www.w3.org/1999/xhtml\"></div>");
                    continue;
                }
                
                if (Element.class.isAssignableFrom(parameterType)
                    || Collection.class.isAssignableFrom(parameterType)) {
                
                    Object argument = createArgument(builder.getClass().getEnclosingClass(), method, parameterType, 0, choiceIndicator);
                    if (argument != null && !(argument instanceof Collection && ((Collection<?>) argument).isEmpty())) {
                        method.invoke(builder, argument);
                        empty = false;
                    }
                }
            }
        }
        
        if (empty) {
            if (builder instanceof Element.Builder){
                // We have a primitive type (i.e. an edge node)
                setDataAbsentReason((Element.Builder) builder);
            }
        }
        return builder;
    }
    
    private boolean isRequiredElement(Class<?> clazz, String name) {
        String elementName = name;
        // If this is a choice element then set elementName to a "concrete" elementName
        // because JsonSupport.isRequiredElement only works with concrete elements (e.g. choiceString, not choice)
        if (JsonSupport.isChoiceElement(clazz, name)) {
            // hacky
            String lookupName = clazz.getCanonicalName().substring(clazz.getPackage().getName().length() + 1);
            Set<String> requiredElementNames = JsonSupport.getRequiredElementNames(lookupName);
            for (String requiredElementName : requiredElementNames) {
                if (requiredElementName.startsWith(name)) {
                    elementName = requiredElementName;
                    break;
                }
            }
        }
        
        return JsonSupport.isRequiredElement(clazz, elementName);
    }

    private String reverseJavaEncoding(String javaName) {
        if (javaName.equals("clazz")) {
            return "class";
        } else if (javaName.startsWith("_")) {
            return javaName.substring(1);
        }
        return javaName;
    }
}
