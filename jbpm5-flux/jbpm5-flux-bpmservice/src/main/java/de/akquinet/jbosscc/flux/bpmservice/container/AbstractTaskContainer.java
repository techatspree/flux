package de.akquinet.jbosscc.flux.bpmservice.container;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.task.Status;
import org.jbpm.task.User;

import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public abstract class AbstractTaskContainer<E> implements TaskXContainer {

	private final E taskObject;
	private Map<String, Object> dataMap;
	private TaskDataObject taskDataObject;

	public AbstractTaskContainer(final E taskObject) {
		this.taskObject = taskObject;
		this.dataMap = new HashMap<String, Object>();
	}

	public E getObject() {
		return this.taskObject;
	}

	public Map<String, Object> getDataMap() {
		return this.dataMap;
	}

	public void setDataMap(final Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public TaskDataObject getTaskDataObject() {
		return this.taskDataObject;
	}

	public void setTaskDataObject(final TaskDataObject taskDataObject) {
		this.taskDataObject = taskDataObject;
	}

	public boolean hasActualOwner() {
		if (getActualOwner() != null) {
			return true;
		}
		return false;
	}

	public abstract long getId();

	public abstract String getName();

	public abstract User getActualOwner();

	public abstract Status getStatus();

	public abstract String getInfo();
}
