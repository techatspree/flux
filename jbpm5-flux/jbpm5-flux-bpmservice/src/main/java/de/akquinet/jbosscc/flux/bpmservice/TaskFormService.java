package de.akquinet.jbosscc.flux.bpmservice;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface TaskFormService {

	void prepareForForm(final TaskXContainer taskContainer);

	String getTaskFormName(final TaskXContainer taskContainer);

}