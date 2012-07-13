package de.akquinet.jbosscc.flux.taskitems.service;

import java.util.Map;

import org.drools.runtime.process.WorkItemHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface TaskItemHandlerService {

	Map<String, WorkItemHandler> getTaskItems();

}
