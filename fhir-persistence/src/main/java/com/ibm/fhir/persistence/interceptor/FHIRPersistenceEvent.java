/*
 * (C) Copyright IBM Corp. 2016,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.ibm.fhir.model.resource.Resource;
import com.ibm.fhir.model.util.FHIRUtil;
import com.ibm.fhir.persistence.FHIRPersistence;

/**
 * This class represents an event fired by the FHIR persistence interceptor framework.
 */
public class FHIRPersistenceEvent {

    /**
     * This property is of type javax.ws.rs.core.UriInfo and contains Application and Request
     * URI information associated with the REST API request for which the interceptor is being invoked.
     */
    public static final String PROPNAME_URI_INFO = "URI_INFO";
    
    /**
     * This property is of type FHIRPersistence and is the handle to the persistence layer implementation
     * being used by the FHIR Server while processing the current request.
     * Persistence interceptors can use this handle to invoke persistence operations.
     */
    public static final String PROPNAME_PERSISTENCE_IMPL = "PERSISTENCE_IMPL";
    
    /**
     * This property is of type javax.ws.rs.core.HttpHeaders and contains the set of HTTP headers
     * associated with the REST API request for which the interceptor is being invoked.
     */
    public static final String PROPNAME_HTTP_HEADERS = "HTTP_HEADERS";
    
    /**
     * This property is of type {@link java.util.Map<String,String> } and contains the
     * set of additional request properties associated with the REST API request for which the interceptor 
     * is being invoked.
     */
    public static final String PROPNAME_REQUEST_PROPERTIES = "REQUEST_PROPERTIES";

    /**
     * This property is of type javax.ws.rs.core.SecurityContext and contains security-related information
     * associated with the REST API request for which the interceptor is being invoked.
     */
    public static final String PROPNAME_SECURITY_CONTEXT = "SECURITY_CONTEXT";
    
    /**
     * This property is of type String and contains the location URI that can be used to 
     * retrieve the resource via a GET request.
     */
    public static final String PROPNAME_RESOURCE_LOCATION_URI = "LOCATION_URI";
    
    /**
     * This property is of type String and contains the resource type associated with a
     * create, update, read, vread, history, search, or delete operation.
     * For other operations, this property will be null.
     */
    public static final String PROPNAME_RESOURCE_TYPE = "RESOURCE_TYPE";
    
    /**
     * This property is of type String and contains the resource id associated with an
     * update, read, vread, history, or delete operation.
     * For update and delete it may be null (i.e. for conditional updates/deletes)
     * For other operations, this property will be null.
     */
    public static final String PROPNAME_RESOURCE_ID = "RESOURCE_ID";
    
    /**
     * This property is of type String and contains the version id associated with a
     * vread operation.   For other operations, this property will be null.
     */
    public static final String PROPNAME_VERSION_ID = "VERSION_ID";

    /**
     * This property holds the FHIRPatch instance associated with the request.
     */
    public static final String PROPNAME_PATCH = "PATCH";
    
    private Resource fhirResource;
    private Resource prevFhirResource = null;
    private boolean  prevFhirResourceSet = false;
    private Map<String, Object> properties;
    
    /**
     * Default ctor.
     */
    public FHIRPersistenceEvent() {
    }
    
    /**
     * Ctor which accepts the FHIR resource and a collection of properties.
     * @param fhirResource the FHIR resource associated with the event
     * @param properties the set of properties associated with the event.
     */
    public FHIRPersistenceEvent(Resource fhirResource, Map<String, Object> properties) {
        this.fhirResource = fhirResource;
        this.properties = properties;
    }

    /**
     * Returns the resource associated with the REST API request that triggered the 
     * interceptor invocation.  This will be non-null before and after a create or update operation,
     * and will be non-null after a read, vread, history or search operation.
     */
    public Resource getFhirResource() {
        return fhirResource;
    }
    
    /**
     * Sets the specific resource in 'this'.
     * Interceptor implementations should *not* call this method.  This method is reserved for use by the FHIR Server.
     */
    public void setFhirResource(Resource resource) {
        this.fhirResource = resource;
    }
    
    /**
     * Returns the "previous" resource associated with the REST API request that triggered
     * the interceptor invocation.  This field is set only for an "update" operation and represents
     * the existing version of the resource prior to the new resource being stored.
     */
    public Resource getPrevFhirResource() {
        return prevFhirResource;
    }

    /**
     * Sets the "previous" resource associated with an "update" request.
     * 
     * @param prevFhirResource the existing most recent version of the resource 
     * prior to the update operation being processed.
     * 
     */
    public void setPrevFhirResource(Resource prevFhirResource) {
        this.prevFhirResource = prevFhirResource;
        this.prevFhirResourceSet = true;
    }

    /**
     * This method returns true if and only if the "previous resource" field has in fact
     * been set.   This flag exists so that we can differentiate between these two scenarios:
     * <ul>
     * <li>The "previous resource" field is explicitly set to null.</li>
     * <li>The "previous resource" field is not explicitly set at all.</li>
     * </ul>
     * 
     * @return true if the "previous resource" field is set 
     * (including the situation where it is set to null); false otherwise
     */
    public boolean isPrevFhirResourceSet() {
        return prevFhirResourceSet;
    }

    /**
     * Returns the resource type associated with the FHIR REST API request that triggered the
     * interceptor invocation.   This will be non-null for a 
     * create, update, read, vread, history, search, or delete operation.
     */
    public String getFhirResourceType() {
        return (String) getProperty(PROPNAME_RESOURCE_TYPE);
    }
    
    /**
     * Returns the resource id associated with the FHIR REST API request that triggered the
     * interceptor invocation.   This will be non-null for a read, vread, history, or non-conditional update/delete operation.
     */
    public String getFhirResourceId() {
        return (String) getProperty(PROPNAME_RESOURCE_ID);
    }
    
    /**
     * Returns the version id associated with the FHIR REST API request that triggered the 
     * interceptor invocation.  This will be non-null for a vread operation.
     */
    public String getFhirVersionId() {
        return (String) getProperty(PROPNAME_VERSION_ID);
    }
    
    /**
     * Returns true if and only if the resource type value contained in the persistence event 
     * represents a standard FHIR resource type.
     */
    public boolean isStandardResourceType() {
        return (this.getFhirResourceType() != null ? FHIRUtil.isStandardResourceType(getFhirResourceType()) : false);
    }
    
    /**
     * Returns the HttpHeaders instance associated with the FHIR REST API request that triggered the
     * interceptor invocation.
     * Note that this HttpHeaders instance is only valid within the scope of the REST API request.
     */
    public HttpHeaders getHttpHeaders() {
        return (HttpHeaders) getProperty(PROPNAME_HTTP_HEADERS);
    }
    
    /**
     * Returns the Map containing additional request properties associated with the 
     * FHIR REST API request that triggered the interceptor invocation.
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getRequestProperties() {
        return (Map<String, String>) getProperty(PROPNAME_REQUEST_PROPERTIES);
    }
   
    /**
     * Returns the SecurityContext instance associated with the FHIR REST API request that triggered the
     * interceptor invocation.
     * Note that this SecurityContext instance is only valid within the scope of the REST API request.
     */
    public SecurityContext getSecurityContext() {
        return (SecurityContext) getProperty(PROPNAME_SECURITY_CONTEXT);
    }
    
    /**
     * Returns the UriInfo instance associated with the FHIR REST API request that triggered the
     * interceptor invocation.  
     * Note that this UriInfo instance is only valid within the scope of the REST API request.
     */
    public UriInfo getUriInfo() {
        return (UriInfo) getProperty(PROPNAME_URI_INFO);
    }
    
    /**
     * Returns the FHIRPersistence instance currently being used by the FHIR REST API layer
     * to process the current request.
     */
    public FHIRPersistence getPersistenceImpl() {
        return (FHIRPersistence) getProperty(PROPNAME_PERSISTENCE_IMPL);
    }
    
    /**
     * Retrieves the named property from the set of properties available to the interceptor.
     * @param propertyName the name of the property to retrieve.
     */
    public Object getProperty(String propertyName) {
        return (properties != null ? properties.get(propertyName) : null);
    }
    
    /**
     * Retrieves the set of properties associated with the FHIR REST API request that triggered
     * the interceptor invocation.
     */
    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        return properties;
    }
    
    /**
     * Retrieves the specified header from the combined list of request headers
     * and additional request properties associated with the request.
     * @param headerName the name of the header to retrieve
     * @return the value of the request header or null if not present
     */
    public String getHeaderString(String headerName) {
        String value = null;
        Map<String, String> props = getRequestProperties();
        if (props != null) {
            value = props.get(headerName);
        }
        if (value == null && getHttpHeaders() != null) {
            value = getHttpHeaders().getHeaderString(headerName);
        }
        return value;
    }
}
