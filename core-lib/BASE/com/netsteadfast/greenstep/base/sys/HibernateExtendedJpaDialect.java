/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.base.sys;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

/**
 * 處理 exceptionStack 	org.springframework.transaction.InvalidIsolationLevelException: Standard JPA does not support custom isolation levels - use a special 
 * 問題
 */
public class HibernateExtendedJpaDialect extends HibernateJpaDialect {
	private static final long serialVersionUID = -5505680328318555204L;
	private Logger logger = Logger.getLogger(HibernateExtendedJpaDialect.class);
	
    @Override
    public Object beginTransaction(final EntityManager entityManager, 
    		final TransactionDefinition definition) throws PersistenceException, SQLException, TransactionException {
    	
    	Session session = (Session) entityManager.getDelegate();
    	if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
    		getSession(entityManager).getTransaction().setTimeout(definition.getTimeout());
    	}
    	entityManager.getTransaction().begin();
    	logger.debug("Transaction started");
    	session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				 logger.debug("The connection instance is " + connection.toString());
				 logger.debug("The isolation level of the connection is " + connection.getTransactionIsolation() 
						 + " and the isolation level set on the transaction is " + definition.getIsolationLevel() );
				 DataSourceUtils.prepareConnectionForTransaction(connection, definition);
			}
    	});
    	return prepareTransaction(entityManager, definition.isReadOnly(), definition.getName());
    }
    
}
