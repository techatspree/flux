package de.akquinet.jbosscc.flux.bpmservice.container;

import java.util.Map;

import org.jbpm.task.Status;
import org.jbpm.task.User;

import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface TaskXContainer {

	long getId();

	String getName();

	Status getStatus();

	User getActualOwner();

	boolean hasActualOwner();

	String getInfo();

	Map<String, Object> getDataMap();

	void setDataMap(Map<String, Object> dataMap);

	TaskDataObject getTaskDataObject();

	void setTaskDataObject(TaskDataObject taskDataObject);
}
