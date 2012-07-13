package de.akquinet.jbosscc.flux.bpmservice.deployment.data;

import org.jbpm.process.workitem.wsht.AbstractHTWorkItemHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class WSHumanTaskHandlerDelegateBean implements
		WSHumanTaskHandlerDelegate {

	private static final long serialVersionUID = 1L;

	private AbstractHTWorkItemHandler humanTaskWorkItemHandler;

	public WSHumanTaskHandlerDelegateBean() {
		//
	}

	public WSHumanTaskHandlerDelegateBean(
			final AbstractHTWorkItemHandler humanTaskWorkItemHandler) {
		this.humanTaskWorkItemHandler = humanTaskWorkItemHandler;
	}

	// -------------- getter / setter --------

	public AbstractHTWorkItemHandler getHumanTaskWorkItemHandler() {
		return humanTaskWorkItemHandler;
	}

	public void setHumanTaskWorkItemHandler(
			final AbstractHTWorkItemHandler humanTaskWorkItemHandler) {
		this.humanTaskWorkItemHandler = humanTaskWorkItemHandler;
	}
}
