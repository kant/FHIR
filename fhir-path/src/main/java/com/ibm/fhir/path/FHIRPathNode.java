/*
 * (C) Copyright IBM Corp. 2019
 * 
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.path;

import java.util.Collection;
import java.util.stream.Stream;

import com.ibm.fhir.path.visitor.FHIRPathNodeVisitor;

public interface FHIRPathNode extends Comparable<FHIRPathNode> {
    String name();
    String path();
    FHIRPathType type();
    boolean hasValue();
    FHIRPathSystemValue getValue();
    Collection<FHIRPathNode> children();
    Stream<FHIRPathNode> stream();
    Collection<FHIRPathNode> descendants();
    default boolean isComparableTo(FHIRPathNode other) {
        return false;
    }
    <T extends FHIRPathNode> boolean is(Class<T> nodeType);
    <T extends FHIRPathNode> T as(Class<T> nodeType);
    default boolean isElementNode() {
        return false;
    }
    default boolean isResourceNode() {
        return false;
    }
    default boolean isSystemValue() {
        return false;
    }
    default boolean isTypeInfoNode() {
        return false;
    }
    default FHIRPathElementNode asElementNode() {
        return as(FHIRPathElementNode.class);
    }
    default FHIRPathResourceNode asResourceNode() {
        return as(FHIRPathResourceNode.class);
    }
    default FHIRPathSystemValue asSystemValue() {
        return as(FHIRPathSystemValue.class);
    }
    default FHIRPathTypeInfoNode asTypeInfoNode() {
        return as(FHIRPathTypeInfoNode.class);
    }
    interface Builder { 
        Builder name(String name);
        Builder path(String path);
        Builder value(FHIRPathSystemValue value);
        Builder children(FHIRPathNode... children);
        Builder children(Collection<FHIRPathNode> children);
        FHIRPathNode build();
    }
    void accept(FHIRPathNodeVisitor visitor);
}