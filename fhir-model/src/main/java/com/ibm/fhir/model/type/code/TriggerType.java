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
public class TriggerType extends Code {
    /**
     * Named Event
     */
    public static final TriggerType NAMED_EVENT = TriggerType.builder().value(ValueSet.NAMED_EVENT).build();

    /**
     * Periodic
     */
    public static final TriggerType PERIODIC = TriggerType.builder().value(ValueSet.PERIODIC).build();

    /**
     * Data Changed
     */
    public static final TriggerType DATA_CHANGED = TriggerType.builder().value(ValueSet.DATA_CHANGED).build();

    /**
     * Data Added
     */
    public static final TriggerType DATA_ADDED = TriggerType.builder().value(ValueSet.DATA_ADDED).build();

    /**
     * Data Updated
     */
    public static final TriggerType DATA_MODIFIED = TriggerType.builder().value(ValueSet.DATA_MODIFIED).build();

    /**
     * Data Removed
     */
    public static final TriggerType DATA_REMOVED = TriggerType.builder().value(ValueSet.DATA_REMOVED).build();

    /**
     * Data Accessed
     */
    public static final TriggerType DATA_ACCESSED = TriggerType.builder().value(ValueSet.DATA_ACCESSED).build();

    /**
     * Data Access Ended
     */
    public static final TriggerType DATA_ACCESS_ENDED = TriggerType.builder().value(ValueSet.DATA_ACCESS_ENDED).build();

    private volatile int hashCode;

    private TriggerType(Builder builder) {
        super(builder);
    }

    public static TriggerType of(ValueSet value) {
        switch (value) {
        case NAMED_EVENT:
            return NAMED_EVENT;
        case PERIODIC:
            return PERIODIC;
        case DATA_CHANGED:
            return DATA_CHANGED;
        case DATA_ADDED:
            return DATA_ADDED;
        case DATA_MODIFIED:
            return DATA_MODIFIED;
        case DATA_REMOVED:
            return DATA_REMOVED;
        case DATA_ACCESSED:
            return DATA_ACCESSED;
        case DATA_ACCESS_ENDED:
            return DATA_ACCESS_ENDED;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static TriggerType of(java.lang.String value) {
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
        TriggerType other = (TriggerType) obj;
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
        public TriggerType build() {
            return new TriggerType(this);
        }
    }

    public enum ValueSet {
        /**
         * Named Event
         */
        NAMED_EVENT("named-event"),

        /**
         * Periodic
         */
        PERIODIC("periodic"),

        /**
         * Data Changed
         */
        DATA_CHANGED("data-changed"),

        /**
         * Data Added
         */
        DATA_ADDED("data-added"),

        /**
         * Data Updated
         */
        DATA_MODIFIED("data-modified"),

        /**
         * Data Removed
         */
        DATA_REMOVED("data-removed"),

        /**
         * Data Accessed
         */
        DATA_ACCESSED("data-accessed"),

        /**
         * Data Access Ended
         */
        DATA_ACCESS_ENDED("data-access-ended");

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
