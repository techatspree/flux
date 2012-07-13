package de.akquinet.jbosscc.flux.taskitems.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItemHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TaskItemIndexData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, WorkItemHandler> taskItemIndex;

	public TaskItemIndexData(final Map<String, WorkItemHandler> taskItemIndex) {
		this.taskItemIndex = taskItemIndex;
	}

	public Map<String, WorkItemHandler> getTaskItemIndex() {
		if (taskItemIndex == null) {
			taskItemIndex = new HashMap<String, WorkItemHandler>();
		}
		return taskItemIndex;
	}

	public WorkItemHandler getItemHandlerByName(final String workItemHandlerName) {
		return getTaskItemIndex().get(workItemHandlerName);
	}

}
