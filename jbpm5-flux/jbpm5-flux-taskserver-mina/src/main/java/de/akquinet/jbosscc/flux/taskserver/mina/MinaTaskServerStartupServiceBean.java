package de.akquinet.jbosscc.flux.taskserver.mina;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.drools.SystemEventListenerFactory;
import org.jboss.logging.Logger;
import org.jbpm.task.identity.UserGroupCallback;
import org.jbpm.task.identity.UserGroupCallbackManager;
import org.jbpm.task.service.DefaultEscalatedDeadlineHandler;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;

import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.CustomFluxUserGroupCallback;

/**
 * Klasse für die Bereitstellung des Human-Task-Services
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Singleton
@Startup
public class MinaTaskServerStartupServiceBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int PORT = 9123;
	private static final String HOST = "localhost";

	@Inject
	private Logger log;

	@PersistenceUnit(unitName = "org.jbpm.task")
	private EntityManagerFactory emf;

	@Inject
	@CustomFluxUserGroupCallback
	private UserGroupCallback customJbpmUsergroupCallback;

	private MinaTaskServer minaTaskServer;

	/**
	 * An dieser Stelle darf keine Transaktion verwendet werden, da dier TaskService die Transaktion selber steuert. org.jbpm.task.service.TaskServiceSession holt sich selbst eine
	 * UserTransaction
	 * 
	 * Ausserdem muss darauf geachtet werden, dass die persistence-unit name="org.jbpm.task" den transaction-type="RESOURCE_LOCAL" hat.
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@PostConstruct
	public void init() {
		log.info("Init HumanTaskManager");
		try {

			// UserGroupCallback setzen, der von der Anwendung implementiert und mit @CustomUserGroupCallback annotiert werden muss.
			UserGroupCallbackManager.resetCallback();
			UserGroupCallbackManager.getInstance().setCallback(
					customJbpmUsergroupCallback);
			log.info("UserGroupCallbackName: "
					+ UserGroupCallbackManager.getInstance().getCallback().getClass()
							.getName());

			EscalatedDeadlineHandler handler = new DefaultEscalatedDeadlineHandler();

			final TaskService taskService = new TaskService(emf,
					SystemEventListenerFactory.getSystemEventListener(), handler);

			/*
			 * Wenn Administrator in Applikation vorhanden, wird hier keiner gebraucht. taskSession.addUser(new User("Administrator"));
			 */

			/* Starte Mina-Server for Human-Task */
			minaTaskServer = new MinaTaskServer(taskService, PORT, HOST);
			final Thread thread = new Thread(minaTaskServer);
			thread.start();

			// taskSession.dispose();
			log.info(" HumanTaskManager started ... :-)");

		} catch (final Throwable t) {
			log.error(t.getMessage(), t.getCause());
			throw new RuntimeException("can't start Mina server", t);
		}
	}

	@PreDestroy
	public void cleanup() {
		log.warn("shutting down Mina server");
		minaTaskServer.stop();
		log.warn("Mina server shut down");
	}
}
