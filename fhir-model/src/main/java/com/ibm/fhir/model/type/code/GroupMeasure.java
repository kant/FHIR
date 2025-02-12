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
public class GroupMeasure extends Code {
    /**
     * Mean
     */
    public static final GroupMeasure MEAN = GroupMeasure.builder().value(ValueSet.MEAN).build();

    /**
     * Median
     */
    public static final GroupMeasure MEDIAN = GroupMeasure.builder().value(ValueSet.MEDIAN).build();

    /**
     * Mean of Study Means
     */
    public static final GroupMeasure MEAN_OF_MEAN = GroupMeasure.builder().value(ValueSet.MEAN_OF_MEAN).build();

    /**
     * Mean of Study Medins
     */
    public static final GroupMeasure MEAN_OF_MEDIAN = GroupMeasure.builder().value(ValueSet.MEAN_OF_MEDIAN).build();

    /**
     * Median of Study Means
     */
    public static final GroupMeasure MEDIAN_OF_MEAN = GroupMeasure.builder().value(ValueSet.MEDIAN_OF_MEAN).build();

    /**
     * Median of Study Medians
     */
    public static final GroupMeasure MEDIAN_OF_MEDIAN = GroupMeasure.builder().value(ValueSet.MEDIAN_OF_MEDIAN).build();

    private volatile int hashCode;

    private GroupMeasure(Builder builder) {
        super(builder);
    }

    public static GroupMeasure of(ValueSet value) {
        switch (value) {
        case MEAN:
            return MEAN;
        case MEDIAN:
            return MEDIAN;
        case MEAN_OF_MEAN:
            return MEAN_OF_MEAN;
        case MEAN_OF_MEDIAN:
            return MEAN_OF_MEDIAN;
        case MEDIAN_OF_MEAN:
            return MEDIAN_OF_MEAN;
        case MEDIAN_OF_MEDIAN:
            return MEDIAN_OF_MEDIAN;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static GroupMeasure of(java.lang.String value) {
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
        GroupMeasure other = (GroupMeasure) obj;
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
        public GroupMeasure build() {
            return new GroupMeasure(this);
        }
    }

    public enum ValueSet {
        /**
         * Mean
         */
        MEAN("mean"),

        /**
         * Median
         */
        MEDIAN("median"),

        /**
         * Mean of Study Means
         */
        MEAN_OF_MEAN("mean-of-mean"),

        /**
         * Mean of Study Medins
         */
        MEAN_OF_MEDIAN("mean-of-median"),

        /**
         * Median of Study Means
         */
        MEDIAN_OF_MEAN("median-of-mean"),

        /**
         * Median of Study Medians
         */
        MEDIAN_OF_MEDIAN("median-of-median");

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
