/*
 * (C) Copyright IBM Corp. 2017,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.jdbc.test;

import java.util.Properties;

import com.ibm.fhir.model.test.TestUtil;
import com.ibm.fhir.persistence.FHIRPersistence;
import com.ibm.fhir.persistence.jdbc.impl.FHIRPersistenceJDBCImpl;
import com.ibm.fhir.persistence.jdbc.test.util.DerbyInitializer;
import com.ibm.fhir.persistence.test.common.AbstractDeleteTest;

/**
 * Concrete subclass for delete tests run against the JDBC schema.
 * @author markd
 *
 */
public class JDBCDeleteTest extends AbstractDeleteTest {
    
    private Properties testProps;
    
    public JDBCDeleteTest() throws Exception {
        this.testProps = TestUtil.readTestProperties("test.jdbc.properties");
    }

    @Override
    public void bootstrapDatabase() throws Exception {
        DerbyInitializer derbyInit;
        String dbDriverName = this.testProps.getProperty("dbDriverName");
        if (dbDriverName != null && dbDriverName.contains("derby")) {
            derbyInit = new DerbyInitializer(this.testProps);
            derbyInit.bootstrapDb(false);
        }
    }
    
    @Override
    public FHIRPersistence getPersistenceImpl() throws Exception {
        return new FHIRPersistenceJDBCImpl(this.testProps);
    }
}
