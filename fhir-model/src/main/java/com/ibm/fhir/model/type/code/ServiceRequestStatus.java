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
public class ServiceRequestStatus extends Code {
    /**
     * Draft
     */
    public static final ServiceRequestStatus DRAFT = ServiceRequestStatus.builder().value(ValueSet.DRAFT).build();

    /**
     * Active
     */
    public static final ServiceRequestStatus ACTIVE = ServiceRequestStatus.builder().value(ValueSet.ACTIVE).build();

    /**
     * On Hold
     */
    public static final ServiceRequestStatus ON_HOLD = ServiceRequestStatus.builder().value(ValueSet.ON_HOLD).build();

    /**
     * Revoked
     */
    public static final ServiceRequestStatus REVOKED = ServiceRequestStatus.builder().value(ValueSet.REVOKED).build();

    /**
     * Completed
     */
    public static final ServiceRequestStatus COMPLETED = ServiceRequestStatus.builder().value(ValueSet.COMPLETED).build();

    /**
     * Entered in Error
     */
    public static final ServiceRequestStatus ENTERED_IN_ERROR = ServiceRequestStatus.builder().value(ValueSet.ENTERED_IN_ERROR).build();

    /**
     * Unknown
     */
    public static final ServiceRequestStatus UNKNOWN = ServiceRequestStatus.builder().value(ValueSet.UNKNOWN).build();

    private volatile int hashCode;

    private ServiceRequestStatus(Builder builder) {
        super(builder);
    }

    public static ServiceRequestStatus of(ValueSet value) {
        switch (value) {
        case DRAFT:
            return DRAFT;
        case ACTIVE:
            return ACTIVE;
        case ON_HOLD:
            return ON_HOLD;
        case REVOKED:
            return REVOKED;
        case COMPLETED:
            return COMPLETED;
        case ENTERED_IN_ERROR:
            return ENTERED_IN_ERROR;
        case UNKNOWN:
            return UNKNOWN;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static ServiceRequestStatus of(java.lang.String value) {
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
        ServiceRequestStatus other = (ServiceRequestStatus) obj;
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
        public ServiceRequestStatus build() {
            return new ServiceRequestStatus(this);
        }
    }

    public enum ValueSet {
        /**
         * Draft
         */
        DRAFT("draft"),

        /**
         * Active
         */
        ACTIVE("active"),

        /**
         * On Hold
         */
        ON_HOLD("on-hold"),

        /**
         * Revoked
         */
        REVOKED("revoked"),

        /**
         * Completed
         */
        COMPLETED("completed"),

        /**
         * Entered in Error
         */
        ENTERED_IN_ERROR("entered-in-error"),

        /**
         * Unknown
         */
        UNKNOWN("unknown");

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
