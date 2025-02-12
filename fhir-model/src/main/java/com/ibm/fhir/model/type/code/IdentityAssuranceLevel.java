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
public class IdentityAssuranceLevel extends Code {
    /**
     * Level 1
     */
    public static final IdentityAssuranceLevel LEVEL1 = IdentityAssuranceLevel.builder().value(ValueSet.LEVEL1).build();

    /**
     * Level 2
     */
    public static final IdentityAssuranceLevel LEVEL2 = IdentityAssuranceLevel.builder().value(ValueSet.LEVEL2).build();

    /**
     * Level 3
     */
    public static final IdentityAssuranceLevel LEVEL3 = IdentityAssuranceLevel.builder().value(ValueSet.LEVEL3).build();

    /**
     * Level 4
     */
    public static final IdentityAssuranceLevel LEVEL4 = IdentityAssuranceLevel.builder().value(ValueSet.LEVEL4).build();

    private volatile int hashCode;

    private IdentityAssuranceLevel(Builder builder) {
        super(builder);
    }

    public static IdentityAssuranceLevel of(ValueSet value) {
        switch (value) {
        case LEVEL1:
            return LEVEL1;
        case LEVEL2:
            return LEVEL2;
        case LEVEL3:
            return LEVEL3;
        case LEVEL4:
            return LEVEL4;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static IdentityAssuranceLevel of(java.lang.String value) {
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
        IdentityAssuranceLevel other = (IdentityAssuranceLevel) obj;
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
        public IdentityAssuranceLevel build() {
            return new IdentityAssuranceLevel(this);
        }
    }

    public enum ValueSet {
        /**
         * Level 1
         */
        LEVEL1("level1"),

        /**
         * Level 2
         */
        LEVEL2("level2"),

        /**
         * Level 3
         */
        LEVEL3("level3"),

        /**
         * Level 4
         */
        LEVEL4("level4");

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
