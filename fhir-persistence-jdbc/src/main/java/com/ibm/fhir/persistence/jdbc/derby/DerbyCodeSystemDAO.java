/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.jdbc.derby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.fhir.persistence.jdbc.dao.api.FhirRefSequenceDAO;
import com.ibm.fhir.persistence.jdbc.dao.impl.CodeSystemDAOImpl;
import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException;

/**
 * Derby variant DAO used to manage code_systems records. Uses
 * plain old JDBC statements instead of a stored procedure.
 *
 */
public class DerbyCodeSystemDAO extends CodeSystemDAOImpl {
    private final FhirRefSequenceDAO fhirRefSequenceDAO;

    /**
     * Public constructor
     * @param c
     * @param fsd
     */
    public DerbyCodeSystemDAO(Connection c, FhirRefSequenceDAO fsd) {
        super(c);
        this.fhirRefSequenceDAO = fsd;
    }
    
    @Override
    public int readOrAddCodeSystem(String codeSystem) throws FHIRPersistenceDataAccessException   {
        // As the system is concurrent, we have to handle cases where another thread
        // might create the entry after we selected and found nothing
        Integer result = getCodeSystemId(codeSystem);
         
        // Create the resource if we don't have it already (set by the continue handler)
        if (result == null) {
            try {
                result = fhirRefSequenceDAO.nextValue();
             
                String INS = "INSERT INTO code_systems (code_system_id, code_system_name) VALUES (?, ?)";
                try (PreparedStatement stmt = getConnection().prepareStatement(INS)) {
                    // bind parameters
                    stmt.setInt(1, result);
                    stmt.setString(2, codeSystem);
                    stmt.executeUpdate();
                }
            }
            catch (SQLException e) {
                if ("23505".equals(e.getSQLState())) {
                    // another thread snuck in and created the record, so we need to fetch the correct id
                    result = getCodeSystemId(codeSystem);
                    
                    if (result == null) {
                        // This would be truly weird, but we protect against it anyway
                        throw new IllegalStateException("No code system returned after duplicate found!");
                    }
                }
                else {
                    throw new FHIRPersistenceDataAccessException("codeSystem=" + codeSystem, e);
                }
            }

        }
        
        // There's no way result can be null here, so we're OK returning an int
        return result;
    }

    /**
     * Read the id for the named type
     * @param codeSystem
     * @return the database id, or null if the named record is not found
     * @throws FHIRPersistenceDataAccessException
     */
    protected Integer getCodeSystemId(String codeSystem) throws FHIRPersistenceDataAccessException {
        Integer result;
        
        String sql1 = "SELECT code_system_id FROM code_systems WHERE code_system_name = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql1)) {
            stmt.setString(1, codeSystem);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            } 
            else {
                result = null;
            }
        }        
        catch (SQLException e) {
            throw new FHIRPersistenceDataAccessException("codeSystem=" + codeSystem, e);
        }
        
        return result;
    }

}
