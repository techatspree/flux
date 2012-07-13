package de.akquinet.jbosscc.flux.taskitems.index;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.runtime.process.WorkItemHandler;
import org.jboss.logging.Logger;

import de.akquinet.jbosscc.flux.taskitems.index.qualifier.TaskItemIndex;
import de.akquinet.jbosscc.flux.taskitems.stereotypes.TaskItemHandler;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TaskItemIndexProducer {

	@Inject
	private Logger log;

	@Inject
	private Instance<WorkItemHandler> allWorkItems;

	@Produces
	@Named
	@TaskItemIndex
	public TaskItemIndexData createTaskItemIndex() {

		final Map<String, WorkItemHandler> taskItemIndex = new HashMap<String, WorkItemHandler>();

		for (final WorkItemHandler taskItem : allWorkItems) {
			if (taskItem.getClass().isAnnotationPresent(TaskItemHandler.class)) {
				final TaskItemHandler tdoAnnotation = taskItem.getClass()
						.getAnnotation(TaskItemHandler.class);

				final String taskName = tdoAnnotation.taskName();

				if (taskItemIndex.containsKey(taskName)) {
					log.errorv("duplicated tash item handler: #0", taskName);
				}

				taskItemIndex.put(taskName, taskItem);
			}
		}

		return new TaskItemIndexData(taskItemIndex);
	}
}
