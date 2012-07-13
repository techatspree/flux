package de.akquinet.jbosscc.flux.bpmservice;

import java.util.Map;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface ProcessManagementService {
	
	void startNewProcess(final String processDefinitionId,
			final Map<String, Object> dataMap);
	
}