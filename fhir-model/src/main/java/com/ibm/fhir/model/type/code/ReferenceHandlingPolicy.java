/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.model.type.code;

import com.ibm.fhir.model.type.Code;
import com.ibm.fhir.model.type.Extension;
import com.ibm.fhir.model.type.String;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

@Generated("com.ibm.fhir.tools.CodeGenerator")
public class ReferenceHandlingPolicy extends Code {
    /**
     * Literal References
     */
    public static final ReferenceHandlingPolicy LITERAL = ReferenceHandlingPolicy.builder().value(ValueSet.LITERAL).build();

    /**
     * Logical References
     */
    public static final ReferenceHandlingPolicy LOGICAL = ReferenceHandlingPolicy.builder().value(ValueSet.LOGICAL).build();

    /**
     * Resolves References
     */
    public static final ReferenceHandlingPolicy RESOLVES = ReferenceHandlingPolicy.builder().value(ValueSet.RESOLVES).build();

    /**
     * Reference Integrity Enforced
     */
    public static final ReferenceHandlingPolicy ENFORCED = ReferenceHandlingPolicy.builder().value(ValueSet.ENFORCED).build();

    /**
     * Local References Only
     */
    public static final ReferenceHandlingPolicy LOCAL = ReferenceHandlingPolicy.builder().value(ValueSet.LOCAL).build();

    private volatile int hashCode;

    private ReferenceHandlingPolicy(Builder builder) {
        super(builder);
    }

    public static ReferenceHandlingPolicy of(ValueSet value) {
        switch (value) {
        case LITERAL:
            return LITERAL;
        case LOGICAL:
            return LOGICAL;
        case RESOLVES:
            return RESOLVES;
        case ENFORCED:
            return ENFORCED;
        case LOCAL:
            return LOCAL;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static ReferenceHandlingPolicy of(java.lang.String value) {
        return of(ValueSet.from(value));
    }

    public static String string(java.lang.String value) {
        return of(ValueSet.from(value));
    }

    public static Code code(java.lang.String value) {
        return of(ValueSet.from(value));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ReferenceHandlingPolicy other = (ReferenceHandlingPolicy) obj;
        return Objects.equals(id, other.id) && Objects.equals(extension, other.extension) && Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Objects.hash(id, extension, value);
            hashCode = result;
        }
        return result;
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.id(id);
        builder.extension(extension);
        builder.value(value);
        return builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Code.Builder {
        private Builder() {
            super();
        }

        @Override
        public Builder id(java.lang.String id) {
            return (Builder) super.id(id);
        }

        @Override
        public Builder extension(Extension... extension) {
            return (Builder) super.extension(extension);
        }

        @Override
        public Builder extension(Collection<Extension> extension) {
            return (Builder) super.extension(extension);
        }

        @Override
        public Builder value(java.lang.String value) {
            return (value != null) ? (Builder) super.value(ValueSet.from(value).value()) : this;
        }

        public Builder value(ValueSet value) {
            return (value != null) ? (Builder) super.value(value.value()) : this;
        }

        @Override
        public ReferenceHandlingPolicy build() {
            return new ReferenceHandlingPolicy(this);
        }
    }

    public enum ValueSet {
        /**
         * Literal References
         */
        LITERAL("literal"),

        /**
         * Logical References
         */
        LOGICAL("logical"),

        /**
         * Resolves References
         */
        RESOLVES("resolves"),

        /**
         * Reference Integrity Enforced
         */
        ENFORCED("enforced"),

        /**
         * Local References Only
         */
        LOCAL("local");

        private final java.lang.String value;

        ValueSet(java.lang.String value) {
            this.value = value;
        }

        public java.lang.String value() {
            return value;
        }

        public static ValueSet from(java.lang.String value) {
            for (ValueSet c : ValueSet.values()) {
                if (c.value.equals(value)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(value);
        }
    }
}
