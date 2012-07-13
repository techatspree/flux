package de.akquinet.jbosscc.flux.taskitems.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import de.akquinet.jbosscc.flux.taskitems.stereotypes.TaskItemHandler;

/**
 * TaskItem for tests
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@TaskItemHandler(taskName = "testTaskItem")
public class TestTaskItem implements WorkItemHandler, Serializable {

	private static final long serialVersionUID = 1L;

	public void abortWorkItem(final WorkItem arg0, final WorkItemManager arg1) {
	}

	public void executeWorkItem(final WorkItem workItem,
			final WorkItemManager workItemManager) {

		final Map<String, Object> outDataMap = new HashMap<String, Object>();
		workItemManager.completeWorkItem(workItem.getId(), outDataMap);
	}
}
