package de.akquinet.jbosscc.flux.tdoservice.service;

import java.util.Map;

import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface TaskDataObjectService {

	TaskDataObject getTdoByTaskName(String taskName, Map<String, Object> dataMap);

}
