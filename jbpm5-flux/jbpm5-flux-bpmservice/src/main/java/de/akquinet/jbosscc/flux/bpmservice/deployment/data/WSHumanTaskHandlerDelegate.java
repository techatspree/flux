package de.akquinet.jbosscc.flux.bpmservice.deployment.data;

import java.io.Serializable;

import org.jbpm.process.workitem.wsht.AbstractHTWorkItemHandler;

public interface WSHumanTaskHandlerDelegate extends Serializable {

	AbstractHTWorkItemHandler getHumanTaskWorkItemHandler();

	void setHumanTaskWorkItemHandler(final AbstractHTWorkItemHandler humanTaskWorkItemHandler);
}