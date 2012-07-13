package de.akquinet.jbosscc.flux.bpmservice;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.logging.Logger;

import de.akquinet.jbosscc.flux.bpmservice.deployment.producer.KnowledgeSessionProvider;

/**
 * Die Klasse stellt Methoden für die Interaktion mit dem Prozess zur Verfügung
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class ProcessManagementServiceBean implements Serializable,
		ProcessManagementService {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private KnowledgeSessionProvider ksessionProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.ProcessManagementService#startNewProcess(java.lang.String, java.util.Map)
	 */
	public void startNewProcess(final String processDefinitionId,
			final Map<String, Object> dataMap) {
		log.info("start Process");

		final StatefulKnowledgeSession ksession = ksessionProvider
				.getKSession();
		ksessionProvider.registerWorkitemHandler();
		ksession.startProcess(processDefinitionId, dataMap);
		ksession.fireAllRules();

		log.info("process started");
	}

}
