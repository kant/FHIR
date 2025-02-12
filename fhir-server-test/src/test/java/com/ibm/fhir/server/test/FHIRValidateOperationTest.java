/*
 * (C) Copyright IBM Corp. 2017,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.server.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.testng.annotations.Test;

import com.ibm.fhir.core.FHIRMediaType;
import com.ibm.fhir.model.resource.OperationOutcome;
import com.ibm.fhir.model.type.code.IssueSeverity;

public class FHIRValidateOperationTest extends FHIRServerTestBase {
    private static final JsonBuilderFactory BUILDER_FACTORY = Json.createBuilderFactory(null);
    @Test(groups = { "validate-operation" })
    public void testValidatePatient() {        
        JsonObject patient = buildPatient();
        Entity<JsonObject> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_JSON);
        
        WebTarget target = getWebTarget();
        Response response = target.path("Resource/$validate").request().post(entity, Response.class);
        assertResponse(response, Response.Status.OK.getStatusCode());
        OperationOutcome operationOutcome = response.readEntity(OperationOutcome.class);
        
        assertEquals(operationOutcome.getId(), "NoError");
        assertEquals(operationOutcome.getIssue().size(), 1);
        assertEquals(operationOutcome.getIssue().get(0).getSeverity(), IssueSeverity.WARNING);
        assertTrue(operationOutcome.getIssue().get(0).getDetails().getText().getValue().startsWith("dom-6"));
    }

    @Test(groups = { "validate-operation" })
    public void testValidateInvalidPatient() {
        JsonObject patient = buildInvalidPatient();
        Entity<JsonObject> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_JSON);

        WebTarget target = getWebTarget();
        Response response = target.path("Resource/$validate").request().post(entity, Response.class);
        assertResponse(response, Response.Status.OK.getStatusCode());
        OperationOutcome operationOutcome = response.readEntity(OperationOutcome.class);
        
        assertEquals(operationOutcome.getId(), "Error");
        assertEquals(operationOutcome.getIssue().size(), 2);
        assertEquals(operationOutcome.getIssue().get(0).getSeverity(), IssueSeverity.WARNING);
        assertTrue(operationOutcome.getIssue().get(0).getDetails().getText().getValue().startsWith("dom-6"));
        assertEquals(operationOutcome.getIssue().get(1).getSeverity(), IssueSeverity.ERROR);
        assertTrue(operationOutcome.getIssue().get(1).getDetails().getText().getValue().startsWith("cpt-2"));
    }

    private JsonObject buildPatient() {
        return BUILDER_FACTORY.createObjectBuilder().add("resourceType", "Patient")
            .add("name", BUILDER_FACTORY.createArrayBuilder()
                .add(BUILDER_FACTORY.createObjectBuilder()
                    .add("family", "Doe")
                    .add("given", BUILDER_FACTORY.createArrayBuilder()
                        .add("John"))))
            .add("birthDate", "1950-08-15")
            .add("telecom", BUILDER_FACTORY.createArrayBuilder()
                .add(BUILDER_FACTORY.createObjectBuilder()
                    .add("use", "home")
                    .add("system", "phone")
                    .add("value", "555-1234")))
            .build();
    }

    private JsonObject buildInvalidPatient() {
        return BUILDER_FACTORY.createObjectBuilder().add("resourceType", "Patient")
            .add("name", BUILDER_FACTORY.createArrayBuilder()
                .add(BUILDER_FACTORY.createObjectBuilder()
                    .add("family", "Doe")
                    .add("given", BUILDER_FACTORY.createArrayBuilder()
                        .add("John"))))
            .add("birthDate", "1950-08-15")
            .add("telecom", BUILDER_FACTORY.createArrayBuilder()
                .add(BUILDER_FACTORY.createObjectBuilder()
                    .add("use", "home")
//                  .add("system", "phone")
                    .add("value", "555-1234")))
            .build();
    }
}
