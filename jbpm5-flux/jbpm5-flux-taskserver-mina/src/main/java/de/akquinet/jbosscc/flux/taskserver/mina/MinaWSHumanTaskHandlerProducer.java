package de.akquinet.jbosscc.flux.taskserver.mina;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.process.workitem.wsht.AsyncMinaHTWorkItemHandler;

import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegate;
import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegateBean;
import de.akquinet.jbosscc.flux.bpmservice.deployment.producer.KnowledgeSessionProvider;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.WSHumanTaskHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class MinaWSHumanTaskHandlerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private KnowledgeSessionProvider ksessionProvider;

	@Produces
	@Named
	@WSHumanTaskHandler
	@ApplicationScoped
	public WSHumanTaskHandlerDelegate produceWSHumanTaskHandlerDelegate() {
		final AsyncMinaHTWorkItemHandler humanTaskHandler = new AsyncMinaHTWorkItemHandler(
				ksessionProvider.getKSession());
		humanTaskHandler.connect();
		return new WSHumanTaskHandlerDelegateBean(humanTaskHandler);
	}
}
