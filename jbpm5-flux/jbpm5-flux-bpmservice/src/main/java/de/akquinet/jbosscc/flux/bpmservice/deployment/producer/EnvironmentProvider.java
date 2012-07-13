package de.akquinet.jbosscc.flux.bpmservice.deployment.producer;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.TransactionManager;

import org.drools.KnowledgeBaseFactory;
import org.drools.persistence.jta.JtaTransactionManager;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.jbpm.persistence.JpaProcessPersistenceContextManager;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@ApplicationScoped
public class EnvironmentProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
	private EntityManagerFactory emf;

	@Resource(mappedName = "java:jboss/TransactionManager")
	private TransactionManager tm;

	private transient Environment environment = null;

	public Environment getEnvironment() {

		if (environment == null) {

			environment = KnowledgeBaseFactory.newEnvironment();

			environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);

			final JpaProcessPersistenceContextManager manager = new JpaProcessPersistenceContextManager(environment);
			environment.set(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, manager);

			final JtaTransactionManager transactionManager = new JtaTransactionManager(null, null, tm);
			environment.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
		}

		return environment;
	}
}
