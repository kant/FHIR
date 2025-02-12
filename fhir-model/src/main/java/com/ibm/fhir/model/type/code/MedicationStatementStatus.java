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
public class MedicationStatementStatus extends Code {
    /**
     * Active
     */
    public static final MedicationStatementStatus ACTIVE = MedicationStatementStatus.builder().value(ValueSet.ACTIVE).build();

    /**
     * Completed
     */
    public static final MedicationStatementStatus COMPLETED = MedicationStatementStatus.builder().value(ValueSet.COMPLETED).build();

    /**
     * Entered in Error
     */
    public static final MedicationStatementStatus ENTERED_IN_ERROR = MedicationStatementStatus.builder().value(ValueSet.ENTERED_IN_ERROR).build();

    /**
     * Intended
     */
    public static final MedicationStatementStatus INTENDED = MedicationStatementStatus.builder().value(ValueSet.INTENDED).build();

    /**
     * Stopped
     */
    public static final MedicationStatementStatus STOPPED = MedicationStatementStatus.builder().value(ValueSet.STOPPED).build();

    /**
     * On Hold
     */
    public static final MedicationStatementStatus ON_HOLD = MedicationStatementStatus.builder().value(ValueSet.ON_HOLD).build();

    /**
     * Unknown
     */
    public static final MedicationStatementStatus UNKNOWN = MedicationStatementStatus.builder().value(ValueSet.UNKNOWN).build();

    /**
     * Not Taken
     */
    public static final MedicationStatementStatus NOT_TAKEN = MedicationStatementStatus.builder().value(ValueSet.NOT_TAKEN).build();

    private volatile int hashCode;

    private MedicationStatementStatus(Builder builder) {
        super(builder);
    }

    public static MedicationStatementStatus of(ValueSet value) {
        switch (value) {
        case ACTIVE:
            return ACTIVE;
        case COMPLETED:
            return COMPLETED;
        case ENTERED_IN_ERROR:
            return ENTERED_IN_ERROR;
        case INTENDED:
            return INTENDED;
        case STOPPED:
            return STOPPED;
        case ON_HOLD:
            return ON_HOLD;
        case UNKNOWN:
            return UNKNOWN;
        case NOT_TAKEN:
            return NOT_TAKEN;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static MedicationStatementStatus of(java.lang.String value) {
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
        MedicationStatementStatus other = (MedicationStatementStatus) obj;
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
        public MedicationStatementStatus build() {
            return new MedicationStatementStatus(this);
        }
    }

    public enum ValueSet {
        /**
         * Active
         */
        ACTIVE("active"),

        /**
         * Completed
         */
        COMPLETED("completed"),

        /**
         * Entered in Error
         */
        ENTERED_IN_ERROR("entered-in-error"),

        /**
         * Intended
         */
        INTENDED("intended"),

        /**
         * Stopped
         */
        STOPPED("stopped"),

        /**
         * On Hold
         */
        ON_HOLD("on-hold"),

        /**
         * Unknown
         */
        UNKNOWN("unknown"),

        /**
         * Not Taken
         */
        NOT_TAKEN("not-taken");

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
