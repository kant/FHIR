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
public class NamingSystemIdentifierType extends Code {
    /**
     * OID
     */
    public static final NamingSystemIdentifierType OID = NamingSystemIdentifierType.builder().value(ValueSet.OID).build();

    /**
     * UUID
     */
    public static final NamingSystemIdentifierType UUID = NamingSystemIdentifierType.builder().value(ValueSet.UUID).build();

    /**
     * URI
     */
    public static final NamingSystemIdentifierType URI = NamingSystemIdentifierType.builder().value(ValueSet.URI).build();

    /**
     * Other
     */
    public static final NamingSystemIdentifierType OTHER = NamingSystemIdentifierType.builder().value(ValueSet.OTHER).build();

    private volatile int hashCode;

    private NamingSystemIdentifierType(Builder builder) {
        super(builder);
    }

    public static NamingSystemIdentifierType of(ValueSet value) {
        switch (value) {
        case OID:
            return OID;
        case UUID:
            return UUID;
        case URI:
            return URI;
        case OTHER:
            return OTHER;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static NamingSystemIdentifierType of(java.lang.String value) {
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
        NamingSystemIdentifierType other = (NamingSystemIdentifierType) obj;
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
        public NamingSystemIdentifierType build() {
            return new NamingSystemIdentifierType(this);
        }
    }

    public enum ValueSet {
        /**
         * OID
         */
        OID("oid"),

        /**
         * UUID
         */
        UUID("uuid"),

        /**
         * URI
         */
        URI("uri"),

        /**
         * Other
         */
        OTHER("other");

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
