/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.model.generator;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibm.fhir.model.generator.exception.FHIRGeneratorException;
import com.ibm.fhir.model.visitor.Visitable;

public abstract class FHIRAbstractGenerator implements FHIRGenerator {
    protected Map<String, Object> properties = new HashMap<>();

    @Override
    public abstract void generate(Visitable visitable, OutputStream out) throws FHIRGeneratorException;

    @Override
    public abstract void generate(Visitable visitable, Writer writer) throws FHIRGeneratorException;

    @Override
    public abstract boolean isPrettyPrinting();

    public void setProperty(String name, Object value) {
        Objects.requireNonNull(name);
        if (!isPropertySupported(name)) {
            throw new IllegalArgumentException("Property: " + name + " is not supported.");
        }
        properties.put(name, Objects.requireNonNull(value));
    }
    
    @Override
    public Object getProperty(String name) {
        return properties.get(Objects.requireNonNull(name));
    }
    
    @Override
    public <T> T getProperty(String name, Class<T> type) {
        return Objects.requireNonNull(type).cast(getProperty(name));
    }
    
    @Override
    public Object getPropertyOrDefault(String name, Object defaultValue) {
        return properties.getOrDefault(Objects.requireNonNull(name), Objects.requireNonNull(defaultValue));
    }
    
    @Override
    public <T> T getPropertyOrDefault(String name, T defaultValue, Class<T> type) {
        return Objects.requireNonNull(type).cast(getPropertyOrDefault(name, defaultValue));
    }
    
    @Override
    public boolean isPropertySupported(String name) {
        return false;
    }
    
    @Override
    public <T extends FHIRGenerator> T as(Class<T> generatorClass) {
        return generatorClass.cast(this);
    }
}
