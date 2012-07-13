package de.akquinet.jbosscc.flux.taskserver.local;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.process.workitem.wsht.GenericHTWorkItemHandler;

import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegateBean;
import de.akquinet.jbosscc.flux.bpmservice.deployment.producer.KnowledgeSessionProvider;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.WSHumanTaskHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class LocalWSHumanTaskHandlerProducer implements Serializable {

	private static final int PORT = 9123;
	private static final String HOST = "localhost";

	private static final long serialVersionUID = 1L;

	@Inject
	private KnowledgeSessionProvider ksessionProvider;

	@Inject
	private LocalTaskServiceDelegator localTaskServiceDelegator;

	@Produces
	@Named
	@WSHumanTaskHandler
	public de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegate produceWSHumanTaskHandlerDelegate() {
		final GenericHTWorkItemHandler humanTaskHandler = new GenericHTWorkItemHandler(
				localTaskServiceDelegator.getLocalTaskService(),
				ksessionProvider.getKSession());

		humanTaskHandler.setLocal(true);

		humanTaskHandler.setIpAddress(HOST);
		humanTaskHandler.setPort(PORT);
		humanTaskHandler.connect();

		return new WSHumanTaskHandlerDelegateBean(humanTaskHandler);
	}

}
