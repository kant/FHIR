/*
 * (C) Copyright IBM Corp. 2017,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.server.test;

import static com.ibm.fhir.model.type.String.string;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibm.fhir.core.FHIRMediaType;
import com.ibm.fhir.model.resource.Bundle;
import com.ibm.fhir.model.resource.Observation;
import com.ibm.fhir.model.resource.Observation.Component;
import com.ibm.fhir.model.resource.Patient;
import com.ibm.fhir.model.test.TestUtil;
import com.ibm.fhir.model.type.Code;
import com.ibm.fhir.model.type.Coding;
import com.ibm.fhir.model.type.Date;
import com.ibm.fhir.model.type.HumanName;
import com.ibm.fhir.model.type.Quantity;
import com.ibm.fhir.model.type.Uri;
import com.ibm.fhir.model.type.code.AdministrativeGender;
import com.ibm.fhir.model.type.code.SortDirection;
import com.ibm.fhir.model.util.FHIRUtil;
import com.ibm.fhir.model.util.JsonSupport;

public class SortingTest extends FHIRServerTestBase {

    private static final boolean DEBUG_SEARCH = false;

    // used for adjustInto calls to obtain usable Zulu instants from Year, YearMonth, LocalDate
    private static final String REFERENCE_DATE_STRING = "2018-01-01T00:00:00";
    private static final LocalDateTime REFERENCE_DATE = LocalDateTime.parse(REFERENCE_DATE_STRING);

    private String patientId;

    @BeforeMethod
    public void shouldSkipTests() throws Exception {
        if (!this.isSortingSupported()) {
            throw new SkipException("Sorting feature not supported; skipping tests");
        }
    }

    @Test
    public void testCreatePatient1() throws Exception {
        WebTarget target = getWebTarget();

        // Build a new Patient and then call the 'create' API.
        Patient patient = TestUtil.readLocalResource("Patient_JohnDoe.json");

        patient = patient.toBuilder().gender(AdministrativeGender.MALE).build();
        Entity<Patient> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Patient").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        patientId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response =
                target.path("Patient/" + patientId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Patient responsePatient = response.readEntity(Patient.class);
        TestUtil.assertResourceEquals(patient, responsePatient);
    }

    @Test
    public void testCreatePatient2() throws Exception {
        WebTarget target = getWebTarget();

        // Build a new Patient and then call the 'create' API.
        Patient patient = TestUtil.readLocalResource("Patient_DavidOrtiz.json");

        patient = patient.toBuilder().gender(AdministrativeGender.MALE).build();
        Entity<Patient> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Patient").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        patientId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response =
                target.path("Patient/" + patientId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Patient responsePatient = response.readEntity(Patient.class);
        TestUtil.assertResourceEquals(patient, responsePatient);
    }

    @Test
    public void testCreatePatient3() throws Exception {
        WebTarget target = getWebTarget();

        // Build a new Patient and then call the 'create' API.
        Patient patient = TestUtil.readLocalResource("patient-example-a.json");

        Entity<Patient> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Patient").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        patientId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response =
                target.path("Patient/" + patientId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Patient responsePatient = response.readEntity(Patient.class);
        TestUtil.assertResourceEquals(patient, responsePatient);
    }

    @Test
    public void testCreatePatient4() throws Exception {
        WebTarget target = getWebTarget();

        // Build a new Patient and then call the 'create' API.
        Patient patient = TestUtil.readLocalResource("patient-example-c.json");

        Entity<Patient> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Patient").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        patientId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response =
                target.path("Patient/" + patientId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Patient responsePatient = response.readEntity(Patient.class);
        TestUtil.assertResourceEquals(patient, responsePatient);
    }

    @Test
    public void testCreatePatient5() throws Exception {
        WebTarget target = getWebTarget();

        // Build a new Patient and then call the 'create' API.
        Patient patient = TestUtil.readLocalResource("patient-example-a1.json");

        Entity<Patient> entity = Entity.entity(patient, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Patient").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        patientId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response =
                target.path("Patient/" + patientId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Patient responsePatient = response.readEntity(Patient.class);
        TestUtil.assertResourceEquals(patient, responsePatient);
    }

    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1" })
    public void testCreateObservation1() throws Exception {
        WebTarget target = getWebTarget();

        Observation observation = TestUtil.buildPatientObservation(patientId, "Observation1.json");
        Entity<Observation> entity = Entity.entity(observation, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Observation").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        String observationId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response = target.path("Observation/"
                + observationId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Observation responseObservation = response.readEntity(Observation.class);

        // use it for search
        observationId = responseObservation.getId();
        TestUtil.assertResourceEquals(observation, responseObservation);
    }

    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1" })
    public void testCreateObservation2() throws Exception {
        WebTarget target = getWebTarget();

        Observation observation = TestUtil.buildPatientObservation(patientId, "Observation2.json");
        Entity<Observation> entity = Entity.entity(observation, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Observation").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        String observationId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response = target.path("Observation/"
                + observationId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Observation responseObservation = response.readEntity(Observation.class);

        // use it for search
        observationId = responseObservation.getId();
        TestUtil.assertResourceEquals(observation, responseObservation);
    }

    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1" })
    public void testCreateObservation3() throws Exception {
        WebTarget target = getWebTarget();

        Observation observation = TestUtil.buildPatientObservation(patientId, "Observation3.json");
        Entity<Observation> entity = Entity.entity(observation, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Observation").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        String observationId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response = target.path("Observation/"
                + observationId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Observation responseObservation = response.readEntity(Observation.class);

        // use it for search
        observationId = responseObservation.getId();
        TestUtil.assertResourceEquals(observation, responseObservation);
    }

    @Test(groups = { "server-search" })
    public void testCreateObservation5() throws Exception {
        WebTarget target = getWebTarget();

        Observation observation = TestUtil.buildPatientObservation("1", "Observation5.json");
        Entity<Observation> entity = Entity.entity(observation, FHIRMediaType.APPLICATION_FHIR_JSON);
        Response response = target.path("Observation").request().post(entity, Response.class);
        assertResponse(response, Response.Status.CREATED.getStatusCode());

        // Get the patient's logical id value.
        String observationId = getLocationLogicalId(response);

        // Next, call the 'read' API to retrieve the new patient and verify it.
        response = target.path("Observation/"
                + observationId).request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Observation responseObservation = response.readEntity(Observation.class);

        // use it for search
        observationId = responseObservation.getId();
        TestUtil.assertResourceEquals(observation, responseObservation);
    }

    // Patient?gender=male&_sort=family
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortAscending() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "family").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {

                // Since a patient can have multiple family names, we need to pick the right one to add to the list
                // variable,
                // whose ordering will be checked later. Since we are sorting by family name ascending, we need to pick
                // the FIRST family name in the natural ordering to add to the list.

                List<String> familyNames = getFamilyNames(patient, SortDirection.ASCENDING);
                if (familyNames.size() > 0) {
                    list.add(this.getFamilyNames(patient, SortDirection.ASCENDING).get(0));
                }
            }
        }
        assertTrueNaturalOrderingString(list);
    }

    // Patient?gender=male&_sort=family
    @SuppressWarnings("rawtypes")
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortAscending_filter_elements() throws Exception {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "family").queryParam("_elements", "gender", "name")
                .request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        Coding subsettedTag =
                Coding.builder().system(Uri.of("http://terminology.hl7.org/CodeSystem/v3-ObservationValue"))
                .code(Code.of("SUBSETTED")).display(string("subsetted")).build();

        boolean result = false;
        for (Bundle.Entry entry : bundle.getEntry()) {

            if (DEBUG_SEARCH) {

                SearchAllTest.generateOutput(entry.getResource());
                System.out.println(result + " "
                        + FHIRUtil.hasTag(entry.getResource(), subsettedTag));
            }

            result = result
                    || FHIRUtil.hasTag(entry.getResource(), subsettedTag);
        }
        assertTrue(result);

        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {
                // Since a patient can have multiple family names, we need to pick the right one to add to the list
                // variable,
                // whose ordering will be checked later. Since we are sorting by family name ascending, we need to pick
                // the FIRST family name in the natural ordering to add to the list.
                List<String> familyNames = getFamilyNames(patient, SortDirection.ASCENDING);
                if (familyNames.size() > 0) {
                    list.add(this.getFamilyNames(patient, SortDirection.ASCENDING).get(0));

                    // Validate Patient element filtering
                    Method[] patientMethods = Patient.class.getMethods();
                    for (int j = 0; j < patientMethods.length; j++) {
                        Method patientMethod = patientMethods[j];
                        if (patientMethod.getName().startsWith("get")) {
                            Object elementValue = patientMethod.invoke(patient);
                            // Only these elements should be present.
                            if (patientMethod.getName().equals("getId")
                                    || patientMethod.getName().equals("getMeta")
                                    || patientMethod.getName().equals("getGender")
                                    || patientMethod.getName().equals("getName")) {
                                assertNotNull(elementValue);
                            } else if (!patientMethod.getName().equals("getClass")) {
                                if (elementValue instanceof List) {
                                    assertEquals(0, ((List) elementValue).size());
                                } else {
                                    assertNull(elementValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        assertTrueNaturalOrderingString(list);
    }

    // Patient?gender=male&_sort=-family
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortDescending() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "-family").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {
                // Since a patient can have multiple family names, we need to pick the right one to add to the list
                // variable,
                // whose ordering will be checked later. Since we are sorting by family name descending, we need to pick
                // the LAST family name in the natural ordering to add to the list.
                List<String> familyNames = getFamilyNames(patient, SortDirection.DESCENDING);

                // Certain Examples do not include family names
                if (familyNames.size() > 0) {
                    list.add(this.getFamilyNames(patient, SortDirection.DESCENDING).get(0));
                }

            }
        }
        assertTrueNaturalOrderingReverse(list);
    }

    // Patient?gender=male&_sort=telecom
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortTelecom() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "telecom").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();
        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getTelecom() != null && patient.getTelecom().size() > 0) {

                if (DEBUG_SEARCH) {
                    SearchAllTest.generateOutput(patient);
                }

                if (patient.getTelecom().get(0).getValue() != null) {
                    list.add(patient.getTelecom().get(0).getValue().getValue());
                } else {
                    // Null value
                    System.out.println(" Null value in first telecom, not unexpected. ");
                }

            }
        }

        assertTrueNaturalOrderingString(list);
    }

    // Patient?gender=male&_sort=-birthDate
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortBirthDate() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "-birthdate").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<java.time.Instant> list = new ArrayList<java.time.Instant>();
        for (int i = 0; i < bundle.getEntry().size(); i++) {
            if (((Patient) bundle.getEntry().get(i).getResource()).getBirthDate() != null) {
                list.add(getInstantFromPartial(((Patient) bundle.getEntry().get(i).getResource()).getBirthDate().getValue()));
            }
        }

        assertTrueNaturalOrderingReverseInstant(list);
    }

    // Patient?gender=male&_sort=family&_sort=birthdate
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortTwoParameters() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("_count", "50").queryParam("_sort", "-family")
                .queryParam("_sort", "birthdate").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();
        String previousFamily = null;
        Date previousBirthDate = null;

        // Check birthDate order first, and then check family name order.
        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {

                List<String> familyNames = getFamilyNames(patient, SortDirection.DESCENDING);

                // Certain Examples do not include family names
                if (familyNames.size() > 0) {
                    String currentFamily =
                            familyNames.get(0);
                    Date currentBirthDate = null;
                    if (patient.getBirthDate() != null) {
                        currentBirthDate =
                                patient.getBirthDate();
                    } else {
                        currentBirthDate = null;
                    }

                    if (DEBUG_SEARCH) {
                        SearchAllTest.generateOutput(patient);
                    }
                    if (previousFamily != null && previousFamily.equals(currentFamily)) {
                        assertTrue(previousBirthDate == null || currentBirthDate == null
                                || (getInstantFromPartial(previousBirthDate.getValue())
                                        .compareTo(getInstantFromPartial(currentBirthDate.getValue())) <= 0));
                    }
                    list.add(currentFamily);

                    previousFamily = currentFamily;
                    previousBirthDate = currentBirthDate;
                }
            }
        }
        assertTrueNaturalOrderingReverse(list);
    }

    // Patient?gender=male&_sort=-family&_sort=-birthdate
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortTwoParametersDescending() {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("_count", "50").queryParam("_sort", "-family")
                .queryParam("_sort", "-birthdate").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();
        String previousFamily = null;
        Date previousBirthDate = null;

        // Check birthDate order first, and then check family name order.
        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {

                List<String> familyNames = getFamilyNames(patient, SortDirection.DESCENDING);

                // Certain Examples do not include family names
                if (familyNames.size() > 0) {
                    String currentFamily =
                            this.getFamilyNames(patient, SortDirection.DESCENDING).get(0);
                    Date currentBirthDate = null;
                    if (patient.getBirthDate() != null) {
                        currentBirthDate =
                                patient.getBirthDate();
                    } else {
                        currentBirthDate = null;
                    }
                    if (previousFamily != null && previousFamily.equals(currentFamily)) {
                        assertTrue(previousBirthDate == null || currentBirthDate == null
                                || (getInstantFromPartial(previousBirthDate.getValue())
                                        .compareTo(getInstantFromPartial(currentBirthDate.getValue())) >= 0));
                    }
                    list.add(currentFamily);

                    previousFamily = currentFamily;
                    previousBirthDate = currentBirthDate;
                }
            }
        }

        assertTrueNaturalOrderingReverse(list);
    }

    // Observation?status=final&code=http://loinc.org|55284-4&_sort=component-value-quantity
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreateObservation1",
            "testCreateObservation2", "testCreateObservation3", "testCreateObservation5" })
    public void testSortValueQuantityAscending() {
        WebTarget target = getWebTarget();
        // we do support coding instead of code for the pipe search.
        Response response =
                target.path("Observation").queryParam("status", "final").queryParam("code", "55284-4")
                .queryParam("_count", "100").queryParam("_sort", "component-value-quantity")
                .request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        assertTrue(bundle.getEntry().size() > 1);
        List<BigDecimal> list = new ArrayList<>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Observation observation = (Observation) entry.getResource();
            BigDecimal minValue = null;
            for (Component component : observation.getComponent()) {
                BigDecimal componentValue = component.getValue().as(Quantity.class).getValue().getValue();
                if (minValue == null || minValue.compareTo(componentValue) > 0) {
                    minValue = componentValue;
                }
            }
            if (minValue != null) {
                list.add(minValue);
            }
        }
        if (DEBUG_SEARCH) {
            System.out.println(list);
        }
        assertTrueNaturalOrdering(list);
    }

    private List<String> getFamilyNames(Patient patient, SortDirection sortDirection) {
        List<String> patientFamilyNameList = new ArrayList<>();

        for (HumanName patientName : patient.getName()) {
            if (patientName.getUse() != null
                    && patientName.getUse().getValue().compareTo("usual") == 0) {
                if (DEBUG_SEARCH) {
                    // Only output when DEBUG_SEARCHging.
                    SearchAllTest.generateOutput(patient);
                }

                System.out.println("Skipping usual type as there is no family name ");

            } else {
                if (DEBUG_SEARCH) {
                    SearchAllTest.generateOutput(patient);
                }

                if (patientName.getFamily() != null) {
                    patientFamilyNameList.add(patientName.getFamily().getValue());
                }

            }
        }
        Collections.sort(patientFamilyNameList);
        if (sortDirection.equals(SortDirection.DESCENDING)) {
            Collections.reverse(patientFamilyNameList);
        }

        return patientFamilyNameList;
    }

    /*
     * The following methods were used with google-collections. The methods are converted to streams.
     */
    private void assertTrueNaturalOrdering(List<BigDecimal> sortedList) {

        Iterator<BigDecimal> iter = sortedList.iterator();
        boolean done = false;

        if (iter.hasNext()) {
            // Must have at least one.

            BigDecimal prior = iter.next();

            while (iter.hasNext()) {
                BigDecimal current = iter.next();

                if (DEBUG_SEARCH) {
                    System.out.println(prior + " " + current);
                }

                assertTrue(prior.compareTo(current) <= 0);
                prior = current;
            }
            done = true;
        }
        assertTrue(done);

    }

    private void assertTrueNaturalOrderingString(List<String> list) {

        if (DEBUG_SEARCH) {
            System.out.println(list);
        }
        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());

        Iterator<String> primary = list.iterator();
        Iterator<String> secondary = sortedList.iterator();
        boolean done = false;
        while (primary.hasNext()) {
            String p = primary.next();
            String s = secondary.next();

            assertTrue(p.compareTo(s) == 0);

            // At least one
            done = true;
        }
        assertTrue(done);

    }

    private void assertTrueNaturalOrderingReverse(List<String> list) {

        if (DEBUG_SEARCH) {
            System.out.println(list);
        }

        Iterator<String> primary = list.iterator();

        List<String> testList = list.stream().sorted().collect(Collectors.toList());
        Collections.reverse(testList);

        Iterator<String> secondary = testList.iterator();

        boolean done = false;
        while (primary.hasNext()) {
            String thisString = primary.next();
            String testString = secondary.next();

            if (DEBUG_SEARCH) {
                System.out.println(thisString + " " + testString);
            }
            assertTrue(testString.compareTo(thisString) >= 0);

            // At least one
            done = true;
        }
        assertTrue(done);

    }

    private void assertTrueNaturalOrderingReverseInstant(List<Instant> sortedList) {

        Iterator<Instant> primary = sortedList.iterator();

        if (DEBUG_SEARCH) {
            System.out.println(sortedList);
        }

        boolean done = false;
        Instant lastInstant = Instant.MAX;
        while (primary.hasNext()) {
            Instant thisInstant = primary.next();

            if (DEBUG_SEARCH) {
                System.out.println("Compare " + lastInstant + " " + lastInstant);
            }

            assertTrue(lastInstant.compareTo(thisInstant) == 0 || lastInstant.isAfter(thisInstant));
            lastInstant = thisInstant;
            // At least one
            done = true;
        }
        assertTrue(done);

    }

    public static java.time.Instant getInstantFromPartial(TemporalAccessor ta) {
        /*
         * Original Implementation in QueryBuilderUtil.java
         */
        java.time.LocalDateTime result;

        if (ta instanceof Year) {
            result = REFERENCE_DATE.with(ChronoField.YEAR, ((Year) ta).getValue());
        } else if (ta instanceof YearMonth) {
            result = REFERENCE_DATE.with(ChronoField.YEAR, ((YearMonth) ta).getYear());
            result = result.with(ChronoField.MONTH_OF_YEAR, ((YearMonth) ta).getMonthValue());
        } else if (ta instanceof LocalDate) {
            // as long as we follow this order, we should never end up with an invalid date-time
            result = REFERENCE_DATE.with(ChronoField.YEAR, ((LocalDate) ta).getYear());
            result = result.with(ChronoField.MONTH_OF_YEAR, ((LocalDate) ta).getMonthValue());
            result = result.with(ChronoField.DAY_OF_MONTH, ((LocalDate) ta).getDayOfMonth());
        } else {
            throw new IllegalArgumentException("Invalid partial TemporalAccessor: "
                    + ta.getClass().getName());
        }

        return ZonedDateTime.of(result, ZoneOffset.UTC).toInstant();
    }


    // Patient?gender=male&_sort=family
    @SuppressWarnings("rawtypes")
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortAscending_filter_summary() throws Exception {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male")
                .queryParam("_count", "50").queryParam("_sort", "family")
                .queryParam("_summary", "true").request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        Coding subsettedTag =
                Coding.builder().system(Uri.of("http://terminology.hl7.org/CodeSystem/v3-ObservationValue"))
                .code(Code.of("SUBSETTED")).display(string("subsetted")).build();

        boolean result = false;
        for (Bundle.Entry entry : bundle.getEntry()) {

            if (DEBUG_SEARCH) {

                SearchAllTest.generateOutput(entry.getResource());
                System.out.println(result + " "
                        + FHIRUtil.hasTag(entry.getResource(), subsettedTag));
            }

            result = result
                    || FHIRUtil.hasTag(entry.getResource(), subsettedTag);
        }
        assertTrue(result);

        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {
                // Since a patient can have multiple family names, we need to pick the right one to add to the list
                // variable,
                // whose ordering will be checked later. Since we are sorting by family name ascending, we need to pick
                // the FIRST family name in the natural ordering to add to the list.
                List<String> familyNames = getFamilyNames(patient, SortDirection.ASCENDING);
                if (familyNames.size() > 0) {
                    list.add(this.getFamilyNames(patient, SortDirection.ASCENDING).get(0));

                    // Validate Patient _summary filtering
                    Method[] patientMethods = Patient.class.getMethods();
                    Set <String> summaryElements = JsonSupport.getSummaryElementNames(Patient.class);
                    for (int j = 0; j < patientMethods.length; j++) {
                        Method patientMethod = patientMethods[j];
                        if (patientMethod.getName().startsWith("get")) {
                            Object elementValue = patientMethod.invoke(patient);
                            // Only these summary elements should be present.
                            if (!summaryElements.stream().anyMatch(patientMethod.getName().substring(3)::equalsIgnoreCase)) {
                                if (elementValue instanceof List) {
                                    assertEquals(0, ((List) elementValue).size());
                                } else if (!patientMethod.getName().equals("getClass")){
                                    assertNull(elementValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        assertTrueNaturalOrderingString(list);
    }




    // Patient?gender=male&_sort=family
    @SuppressWarnings("rawtypes")
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortAscending_filter_elements_summary() throws Exception {
        WebTarget target = getWebTarget();
        // The _summary=true should be ignored.
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "family").queryParam("_elements", "gender", "name")
                .queryParam("_summary", "true")
                .request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        Coding subsettedTag =
                Coding.builder().system(Uri.of("http://terminology.hl7.org/CodeSystem/v3-ObservationValue"))
                .code(Code.of("SUBSETTED")).display(string("subsetted")).build();

        boolean result = false;
        for (Bundle.Entry entry : bundle.getEntry()) {

            if (DEBUG_SEARCH) {

                SearchAllTest.generateOutput(entry.getResource());
                System.out.println(result + " "
                        + FHIRUtil.hasTag(entry.getResource(), subsettedTag));
            }

            result = result
                    || FHIRUtil.hasTag(entry.getResource(), subsettedTag);
        }
        assertTrue(result);

        assertTrue(bundle.getEntry().size() > 1);
        List<String> list = new ArrayList<String>();

        for (Bundle.Entry entry : bundle.getEntry()) {
            Patient patient = (Patient) entry.getResource();
            if (patient.getName() != null
                    && patient.getName().size() > 0) {
                // Since a patient can have multiple family names, we need to pick the right one to add to the list
                // variable,
                // whose ordering will be checked later. Since we are sorting by family name ascending, we need to pick
                // the FIRST family name in the natural ordering to add to the list.
                List<String> familyNames = getFamilyNames(patient, SortDirection.ASCENDING);
                if (familyNames.size() > 0) {
                    list.add(this.getFamilyNames(patient, SortDirection.ASCENDING).get(0));

                    // Validate Patient element filtering, the summary filter should have been ignored
                    Method[] patientMethods = Patient.class.getMethods();
                    for (int j = 0; j < patientMethods.length; j++) {
                        Method patientMethod = patientMethods[j];
                        if (patientMethod.getName().startsWith("get")) {
                            Object elementValue = patientMethod.invoke(patient);
                            // Only these elements should be present.
                            if (patientMethod.getName().equals("getId")
                                    || patientMethod.getName().equals("getMeta")
                                    || patientMethod.getName().equals("getGender")
                                    || patientMethod.getName().equals("getName")) {
                                assertNotNull(elementValue);
                            } else if (!patientMethod.getName().equals("getClass")) {
                                if (elementValue instanceof List) {
                                    assertEquals(0, ((List) elementValue).size());
                                } else {
                                    assertNull(elementValue);
                                }
                            }
                        }
                    }
                }
            }
        }
        assertTrueNaturalOrderingString(list);
    }


    // Patient?gender=male&_sort=family
    @Test(groups = { "server-search" }, dependsOnMethods = { "testCreatePatient1",
            "testCreatePatient2", "testCreatePatient3", "testCreatePatient4", "testCreatePatient5" })
    public void testSortAscending_filter_summary_count() throws Exception {
        WebTarget target = getWebTarget();
        Response response =
                target.path("Patient").queryParam("gender", "male").queryParam("_count", "50")
                .queryParam("_sort", "family").queryParam("_summary", "count")
                .request(FHIRMediaType.APPLICATION_FHIR_JSON).get();
        assertResponse(response, Response.Status.OK.getStatusCode());
        Bundle bundle = response.readEntity(Bundle.class);
        assertNotNull(bundle);
        // There should be no entry
        assertTrue(bundle.getEntry().size() == 0);
        // There should be no previous and next links.
        assertTrue(bundle.getLink().size() == 1);

    }

}
