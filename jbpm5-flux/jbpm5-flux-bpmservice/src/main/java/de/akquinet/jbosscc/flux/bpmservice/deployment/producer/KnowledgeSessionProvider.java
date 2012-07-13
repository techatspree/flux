package de.akquinet.jbosscc.flux.bpmservice.deployment.producer;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.WorkItemHandler;
import org.jboss.logging.Logger;

import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegate;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.KBase;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.WSHumanTaskHandler;
import de.akquinet.jbosscc.flux.taskitems.service.TaskItemHandlerService;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@ApplicationScoped
public class KnowledgeSessionProvider implements Serializable {

	private static final String HUMAN_TASK_TASK_HANDLER_NAME = "Human Task";

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private EnvironmentProvider environmentProvider;

	@Inject
	@KBase
	private KnowledgeBase knowledgeBase;

	@Inject
	private TaskItemHandlerService taskItemIndex;

	@Inject
	@WSHumanTaskHandler
	private WSHumanTaskHandlerDelegate humanTaskHandlerDelegate;

	private transient StatefulKnowledgeSession ksession;

	public StatefulKnowledgeSession createKSession() {
		final Environment environment = environmentProvider.getEnvironment();

		final Properties properties = new Properties();
		properties.put("drools.processInstanceManagerFactory", "org.jbpm.persistence.processinstance.JPAProcessInstanceManagerFactory");
		properties.put("drools.processSignalManagerFactory", "org.jbpm.persistence.processinstance.JPASignalManagerFactory");

		final KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);

		ksession = JPAKnowledgeService.newStatefulKnowledgeSession(knowledgeBase, config, environment);

		return ksession;
	}

	public void registerWorkitemHandler() {
		StatefulKnowledgeSession ksession = getKSession();	
		ksession.getWorkItemManager().registerWorkItemHandler(HUMAN_TASK_TASK_HANDLER_NAME, humanTaskHandlerDelegate.getHumanTaskWorkItemHandler());

		final Map<String, WorkItemHandler> taskItems = taskItemIndex.getTaskItems();
		for (final Entry<String, WorkItemHandler> e : taskItems.entrySet()) {
			log.info("add TaskItem: " + e.getKey());
			ksession.getWorkItemManager().registerWorkItemHandler(e.getKey(), e.getValue());
		}
	}

	public StatefulKnowledgeSession getKSession() {
		if (ksession == null) {
			ksession = createKSession();
		}
		return ksession;
	}

}
