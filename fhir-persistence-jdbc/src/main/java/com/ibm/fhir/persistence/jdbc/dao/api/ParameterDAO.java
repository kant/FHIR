/*
 * (C) Copyright IBM Corp. 2017,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.jdbc.dao.api;

import java.sql.Array;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ibm.fhir.persistence.exception.FHIRPersistenceException;
import com.ibm.fhir.persistence.jdbc.dto.Parameter;
import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException;
import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException;

/**
 * This Data Access Object interface defines methods for creating, updating, 
 * and retrieving rows in the IBM FHIR Server parameter-related tables.
 */
public interface ParameterDAO extends FHIRDbDAO {
    
    /**
     * Performs a batch insert of the passed Parameter objects into the FHIR database.
     * @param parameters - A List of search parameters associated with a FHIR Resource.
     * @throws FHIRPersistenceDataAccessException
     * @throws FHIRPersistenceDBConnectException
     */
    void insert(List<Parameter> parameters) throws FHIRPersistenceDataAccessException, FHIRPersistenceDBConnectException;

    /**
     * Deletes from the Parameter table all rows associated with the passed resource id.
     * @param resourceId - The id of the resource for which Parameter rows should be deleted.
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException 
     */
    default void deleteByResource(long resourceId) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException {};
    
    /**
     * Reads all rows in the Parameter_Names table and returns the data as a Map
     * @return Map<String, Long> - A map containing key=parameter-name, value=parameter-name-id
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Map<String,Integer> readAllSearchParameterNames() throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;
    
    /**
     * Reads all rows in the Code_Systems table and returns the data as a Map
     * @return Map<String, Long> - A map containing key=system-name, value=system-id
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Map<String,Integer> readAllCodeSystems() throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;
    
    
    /**
     * Reads the id associated with the name of the passed Parameter from the Parameter_Names table. If the id for the passed name is not present
     * in the database, an id is generated, persisted, and returned.
     * @param String A valid FHIR search  parameter name.
     * @return Integer - the id associated with the name of the passed Parameter.
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    int readOrAddParameterNameId(String parameterName) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;

    /**
     * Read the id for the given parameter name, but do not create a new record if it doesn't exist.
     * @param parameterName
     * @return the id for the parameter name, or null not found
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Integer readParameterNameId(String parameterName) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;

    /**
     * Reads the id associated with the name of the passed code system name from the Code_Systems table. If the id for the passed system name is not present
     * in the database, an id is generated, persisted, and returned.
     * @param systemName - The name of a FHIR code system.
     * @return Integer - The id associated with the passed code system name.
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    int readOrAddCodeSystemId(String systemName) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;

    
    /**
     * Read the id for the given code system name, but do not create a new record if it doesn't exist.
     * @param systemName
     * @return Integer - The id associated with the passed code system name, or null if it doesn't exist
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Integer readCodeSystemId(String systemName) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;

    
    /**
     * Acquire and return the id associated with the passed parameter name.
     * @param parameterName The name of a valid FHIR search parameter.
     * @return Integer A parameter id.
     * @throws FHIRPersistenceException
     */
    int acquireParameterNameId(String parameterName) throws FHIRPersistenceException;

    /**
     * Acquire and return the id associated with the passed code-system name.
     * @param codeSystemName The name of a valid code-system.
     * @return Integer A code-system id.
     * @throws FHIRPersistenceException
     */
    int acquireCodeSystemId(String codeSystemName) throws FHIRPersistenceException;
    
    /**
     * Adds a code system name / code system id pair to a candidate collection for population into the CodeSystemsCache.
     * This pair must be present as a row in the FHIR DB CODE_SYSTEMS table.
     * @param codeSystemName A valid code system name.
     * @param codeSystemId The id corresponding to the code system name.
     * @throws FHIRPersistenceException
     */
    void addCodeSystemsCacheCandidate(String codeSystemName, Integer codeSystemId) throws FHIRPersistenceException;    
    
    /**
     * Adds a parameter name / parameter id pair to a candidate collection for population into the ParameterNamesCache.
     * This pair must be present as a row in the FHIR DB PARAMETER_NAMES table.
     * @param parameterName A valid search or sort parameter name.
     * @param parameterId The id corresponding to the parameter name.
     * @throws FHIRPersistenceException
     */
    void addParameterNamesCacheCandidate(String parameterName, Integer parameterId) throws FHIRPersistenceException;
    
    /**
     * Extracts String type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformStringParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    
    /**
     * Extracts Number type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformNumberParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    /**
     * Extracts Date type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformDateParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    /**
     * Extracts Latitude/Longitude type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformLatLongParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    /**
     * Extracts Token type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformTokenParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    /**
     * Extracts Quantity type FHIR search parameters from the passed collection and creates an SQL array of those parameters and their values.
     * @param connection A connection to the FHIR database.
     * @param schemaName The current schema name.
     * @param parameters A collection of FHIR search parameters.
     * @return An SQL Array containing rows of parameter names and values.
     * @throws FHIRPersistenceException
     */
    Array transformQuantityParameters(Connection connection, String schemaName, List<Parameter> parameters) throws FHIRPersistenceException;
    
    /**
     * Sets an externally managed DB connection, used by the DAO for all DB activity.
     * @param connection
     */
    void setExternalConnection(Connection connection);

}
