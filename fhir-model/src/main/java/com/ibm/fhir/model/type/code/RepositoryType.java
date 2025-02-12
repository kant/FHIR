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
public class RepositoryType extends Code {
    /**
     * Click and see
     */
    public static final RepositoryType DIRECTLINK = RepositoryType.builder().value(ValueSet.DIRECTLINK).build();

    /**
     * The URL is the RESTful or other kind of API that can access to the result.
     */
    public static final RepositoryType OPENAPI = RepositoryType.builder().value(ValueSet.OPENAPI).build();

    /**
     * Result cannot be access unless an account is logged in
     */
    public static final RepositoryType LOGIN = RepositoryType.builder().value(ValueSet.LOGIN).build();

    /**
     * Result need to be fetched with API and need LOGIN( or cookies are required when visiting the link of resource)
     */
    public static final RepositoryType OAUTH = RepositoryType.builder().value(ValueSet.OAUTH).build();

    /**
     * Some other complicated or particular way to get resource from URL.
     */
    public static final RepositoryType OTHER = RepositoryType.builder().value(ValueSet.OTHER).build();

    private volatile int hashCode;

    private RepositoryType(Builder builder) {
        super(builder);
    }

    public static RepositoryType of(ValueSet value) {
        switch (value) {
        case DIRECTLINK:
            return DIRECTLINK;
        case OPENAPI:
            return OPENAPI;
        case LOGIN:
            return LOGIN;
        case OAUTH:
            return OAUTH;
        case OTHER:
            return OTHER;
        default:
            throw new IllegalStateException(value.name());
        }
    }

    public static RepositoryType of(java.lang.String value) {
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
        RepositoryType other = (RepositoryType) obj;
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
        public RepositoryType build() {
            return new RepositoryType(this);
        }
    }

    public enum ValueSet {
        /**
         * Click and see
         */
        DIRECTLINK("directlink"),

        /**
         * The URL is the RESTful or other kind of API that can access to the result.
         */
        OPENAPI("openapi"),

        /**
         * Result cannot be access unless an account is logged in
         */
        LOGIN("login"),

        /**
         * Result need to be fetched with API and need LOGIN( or cookies are required when visiting the link of resource)
         */
        OAUTH("oauth"),

        /**
         * Some other complicated or particular way to get resource from URL.
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
