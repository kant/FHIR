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
public class AuditEventAgentNetworkType extends Code {
    /**
     * Machine Name
     */
    public static final AuditEventAgentNetworkType TYPE_1 = AuditEventAgentNetworkType.builder().value(ValueSet.TYPE_1).build();

    /**
     * IP Address
     */
    public static final AuditEventAgentNetworkType TYPE_2 = AuditEventAgentNetworkType.builder().value(ValueSet.TYPE_2).build();

    /**
     * Telephone Number
     */
    public static final AuditEventAgentNetworkType TYPE_3 = AuditEventAgentNetworkType.builder().value(ValueSet.TYPE_3).build();

    /**
     * Email address
     */
    public static final AuditEventAgentNetworkType TYPE_4 = AuditEventAgentNetworkType.builder().value(ValueSet.TYPE_4).build();

    /**
     * URI
     */
    public static final AuditEventAgentNetworkType TYPE_5 = AuditEventAgentNetworkType.builder().value(ValueSet.TYPE_5).build();

    private volatile int hashCode;

    private AuditEventAgentNetworkType(Builder builder) {
        super(builder);
    }

    public static AuditEventAgentNetworkType of(ValueSet value) {
        switch (value) {
        case TYPE_1:
            return TYPE_1;
        case TYPE_2:
            return TYPE_2;
        case TYPE_3:
            return TYPE_3;
        case TYPE_4:
            return TYPE_4;
        case TYPE_5:
            return TYPE_5;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static AuditEventAgentNetworkType of(java.lang.String value) {
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
        AuditEventAgentNetworkType other = (AuditEventAgentNetworkType) obj;
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
        public AuditEventAgentNetworkType build() {
            return new AuditEventAgentNetworkType(this);
        }
    }

    public enum ValueSet {
        /**
         * Machine Name
         */
        TYPE_1("1"),

        /**
         * IP Address
         */
        TYPE_2("2"),

        /**
         * Telephone Number
         */
        TYPE_3("3"),

        /**
         * Email address
         */
        TYPE_4("4"),

        /**
         * URI
         */
        TYPE_5("5");

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
