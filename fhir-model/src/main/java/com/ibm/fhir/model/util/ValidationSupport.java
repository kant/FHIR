/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.model.util;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.ibm.fhir.model.resource.Resource;
import com.ibm.fhir.model.type.Element;

/**
 * Static helper methods for validating model objects during construction
 */
public final class ValidationSupport {
    private static final int MIN_STRING_LENGTH = 1;
    private static final int MAX_STRING_LENGTH = 1048576; // 1024 * 1024 = 1MB
    private static final String FHIR_XHTML_XSD = "fhir-xhtml.xsd";
    private static final String FHIR_XML_XSD = "xml.xsd";
    private static final String FHIR_XMLDSIG_CORE_SCHEMA_XSD = "xmldsig-core-schema.xsd";
    private static final SchemaFactory SCHEMA_FACTORY = createSchemaFactory();
    private static final Schema SCHEMA = createSchema();
    private static final ThreadLocal<Validator> THREAD_LOCAL_VALIDATOR = new ThreadLocal<Validator>() {
        @Override
        public Validator initialValue() {
            return SCHEMA.newValidator();
        }
    };

    private ValidationSupport() { }
    
    private static final Set<Character> WHITESPACE = new HashSet<>(Arrays.asList(' ', '\t', '\r', '\n'));
    
    /**
     * A sequence of Unicode characters
     * <pre>
     * pattern:  [ \r\n\t\S]+
     * </pre>
     * 
     * @throws IllegalStateException if the passed String is not a valid FHIR String value
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkString(String s) {
        if (s == null) {
            return;
        }
        if (s.length() > MAX_STRING_LENGTH) {
            throw new IllegalStateException(String.format("String value length: %d is greater than maximum allowed length: %d", s.length(), MAX_STRING_LENGTH));
        }
        
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!Character.isWhitespace(ch)) {
                count++;
            } else if (!WHITESPACE.contains(ch)) {
                throw new IllegalStateException(String.format("String value: '%s' is not valid with respect to pattern: [ \\r\\n\\t\\S]+", s));
            }
        }
        if (count < MIN_STRING_LENGTH) {
            throw new IllegalStateException(String.format("Trimmed String value length: %d is less than minimum required length: %d", count, MIN_STRING_LENGTH));
        }
    }
    
    /**
     * A string which has at least one character and no leading or trailing whitespace and where there is no whitespace other 
     * than single spaces in the contents.
     * <pre>
     * pattern:  [^\s]+(\s[^\s]+)*
     * </pre>
     * 
     * @throws IllegalStateException if the passed String is not a valid FHIR Code value
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkCode(String s) {
        if (s == null) {
            return;
        }
        if (Character.isWhitespace(s.charAt(0)) || s.length() == 0) {
            throw new IllegalStateException(String.format("Code value: '%s' must begin with a non-whitespace character", s));
        }
        if (Character.isWhitespace(s.charAt(s.length() - 1))) {
            throw new IllegalStateException(String.format("Code value: '%s' must end with a non-whitespace character", s));
        }
        
        boolean previousIsSpace = false;
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            if (Character.isWhitespace(current)) {
                if (current != ' ') {
                    throw new IllegalStateException(String.format("Code value: '%s' must not contain whitespace other than a single space", s));
                } else if (previousIsSpace) {
                    throw new IllegalStateException(String.format("Code value: '%s' must not contain consecutive spaces", s));
                }
                previousIsSpace = true;
            } else {
                if (previousIsSpace) {
                    previousIsSpace = false;
                }
            }
        }
    }
    
    /**
     * Any combination of letters, numerals, "-" and ".", with a length limit of 64 characters. (This might be an integer, an 
     * unprefixed OID, UUID or any other identifier pattern that meets these constraints.) Ids are case-insensitive.
     * <pre>
     * pattern:  [A-Za-z0-9\-\.]{1,64}
     * </pre>
     * 
     * @throws IllegalStateException if the passed String is not a valid FHIR Id value
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkId(String s) {
        if (s == null) {
            return;
        }
        if (s.isEmpty()) {
            throw new IllegalStateException(String.format("Id value must not be empty"));
        }
        if (s.length() > 64) {
            throw new IllegalStateException(String.format("Id value length: %d is greater than maximum allowed length: %d", s.length(), 64));
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //45 = '-'
            //46 = '.'
            //48 = '0'
            //57 = '9'
            //65 = 'A'
            //90 = 'Z'
            //97 = 'a'
            //122 = 'z'
            if (c < 45 || c == 47 || (c > 57 && c < 65) || (c > 90 && c < 97) || c > 122 ) {
                throw new IllegalStateException(String.format("Id value: '%s' contain invalid character '%s'", s, c));
            }
        }
    }
    
    /**
     * String of characters used to identify a name or a resource
     * <pre>
     * pattern:  \S*
     * </pre>
     * 
     * @throws IllegalStateException if the passed String is not a valid FHIR Id value
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkUri(String s) {
        if (s == null) {
            return;
        }
        if (s.length() > MAX_STRING_LENGTH) {
            throw new IllegalStateException(String.format("Uri value length: %d is greater than maximum allowed length: %d", s.length(), MAX_STRING_LENGTH));
        }

        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                throw new IllegalStateException(String.format("Uri value: '%s' must not contain whitespace", s));
            }
        }
    }

    /**
     * @throws IllegalStateException if the passed String is longer than the maximum string length
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkMaxLength(String value) {
        if (value != null) {
            if (value.length() > MAX_STRING_LENGTH) {
                throw new IllegalStateException(String.format("String value length: %d is greater than maximum allowed length: %d", value.length(), MAX_STRING_LENGTH));
            }
        }
    }

    /**
     * @throws IllegalStateException if the passed String is shorter than the minimum string length
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkMinLength(String value) {
        if (value != null) {
            if (value.trim().length() < MIN_STRING_LENGTH) {
                throw new IllegalStateException(String.format("Trimmed String value length: %d is less than minimum required length: %d", value.trim().length(), MIN_STRING_LENGTH));
            }
        }
    }

    /**
     * @throws IllegalStateException if the passed Integer value is less than the passed minValue
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkValue(Integer value, int minValue) {
        if (value != null) {
            if (value < minValue) {
                throw new IllegalStateException(String.format("Integer value: %d is less than minimum required value: %d", value, minValue));
            }
        }
    }

    /**
     * @throws IllegalStateException if the passed String value does not match the passed pattern
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkValue(String value, Pattern pattern) {
        if (value != null) {
            if (!pattern.matcher(value).matches()) {
                throw new IllegalStateException(String.format("String value: '%s' is not valid with respect to pattern: %s", value, pattern.pattern()));
            }
        }
    }

    /**
     * @throws IllegalStateException if the type of the passed value is not one of the passed types
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T> T checkValueType(T value, Class<?>... types) {
        if (value != null) {
            List<Class<?>> typeList = Arrays.asList(types);
            Class<?> valueType = value.getClass();
            if (!typeList.contains(valueType)) {
                List<String> typeNameList = typeList.stream().map(Class::getSimpleName).collect(Collectors.toList());
                throw new IllegalStateException(String.format("Invalid value type: %s must be one of: %s", valueType.getSimpleName(), typeNameList.toString()));
            }
        }
        return value;
    }

    /**
     * @throws IllegalStateException if the type of the passed element is not one of the passed types
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     * @apiNote Only differs from {@link #checkValueType} in that we can provide a better error message
     */
    public static <T extends Element> T choiceElement(T element, String elementName, Class<?>... types) {
        if (element != null) {
            Class<?> elementType = element.getClass();
            if (Arrays.stream(types).noneMatch(t -> t.isAssignableFrom(elementType))) {
                List<String> typeNameList = Arrays.stream(types).map(Class::getSimpleName).collect(Collectors.toList());
                throw new IllegalStateException(String.format("Invalid type: %s for choice element: '%s' must be one of: %s", elementType.getSimpleName(), elementName, typeNameList.toString()));
            }
        }
        return element;
    }

    /**
     * @throws IllegalStateException if the passed String value is not valid XHTML
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void checkXHTMLContent(String value) {
        try {
            Validator validator = THREAD_LOCAL_VALIDATOR.get();
            validator.reset();
            validator.validate(new StreamSource(new StringReader(value)));
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Invalid XHTML content: %s", e.getMessage()), e);
        }
    }

    private static Schema createSchema() {
        try {
            StreamSource[] sources = new StreamSource[3];
            sources[0] = new StreamSource(ValidationSupport.class.getClassLoader().getResourceAsStream(FHIR_XML_XSD));
            sources[1] = new StreamSource(ValidationSupport.class.getClassLoader().getResourceAsStream(FHIR_XMLDSIG_CORE_SCHEMA_XSD));
            sources[2] = new StreamSource(ValidationSupport.class.getClassLoader().getResourceAsStream(FHIR_XHTML_XSD));
            return SCHEMA_FACTORY.newSchema(sources);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    
    private static SchemaFactory createSchemaFactory() {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            return schemaFactory;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    
    /**
     * @throws IllegalStateException if the passed element is null or if its type is not one of the passed types
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T extends Element> T requireChoiceElement(T element, String elementName, Class<?>... types) {
        requireNonNull(element, elementName);
        return choiceElement(element, elementName, types);
    }
    
    /**
     * @throws IllegalStateException if the passed list is empty or contains any null objects
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T> List<T> requireNonEmpty(List<T> elements, String elementName) {
        requireNonNull(elements, elementName);
        if (elements.isEmpty()) {
            throw new IllegalStateException(String.format("Missing required element: '%s'", elementName));
        }
        return elements;
    }
    
    /**
     * @throws IllegalStateException if the passed element is null
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T> T requireNonNull(T element, String elementName) {
        if (element == null) {
            throw new IllegalStateException(String.format("Missing required element: '%s'", elementName));
        }
        return element;
    }
    
    /**
     * @throws IllegalStateException if the passed list contains any null objects
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T> List<T> requireNonNull(List<T> elements, String elementName) {
        if (elements.stream().anyMatch(Objects::isNull)) {
            throw new IllegalStateException(String.format("Repeating element: '%s' does not permit null elements", elementName));
        }
        return elements;
    }

    /**
     * @throws IllegalStateException if the passed element has no value and no children
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void requireValueOrChildren(Element element) {
        if (!element.hasValue() && !element.hasChildren()) {
            throw new IllegalStateException("ele-1: All FHIR elements must have a @value or children");
        }
    }
    
    /**
     * @throws IllegalStateException if the passed element has no children
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void requireChildren(Resource resource) {
        if (!resource.hasChildren()) {
            throw new IllegalStateException("global-1: All FHIR elements must have a @value or children");
        }
    }
    
    /**
     * @throws IllegalStateException if the passed element is not null
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static void prohibited(Element element, String elementName) {
        if (element != null) {
            throw new IllegalStateException(String.format("Element: '%s' is prohibited.", elementName));
        }
    }

    /**
     * @throws IllegalStateException if the passed list is not empty
     * @apiNote IllegalStateException is chosen in favor of IllegalArgumentException so that Builder.build()
     *          methods can throw the most appropriate exception without catching and wrapping.
     */
    public static <T extends Element> void prohibited(List<T> elements, String elementName) {
        if (!elements.isEmpty()) {
            throw new IllegalStateException(String.format("Element: '%s' is prohibited.", elementName));
        }
    }
}
