package de.akquinet.jbosscc.flux.bpmservice.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskSummaryContainer;
import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class TaskSummaryUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<TaskXContainer> transformToTaskSummaryContainerList(
			final List<TaskSummary> taskSummaryList, final Status taskStateFilter) {
		final List<TaskXContainer> taskSummaryContainerList = new ArrayList<TaskXContainer>();

		if (taskStateFilter != null) {
			for (final TaskSummary taskSummary : taskSummaryList) {
				if (taskStateFilter.equals(taskSummary.getStatus())) {
					taskSummaryContainerList.add(new TaskSummaryContainer(taskSummary));
				}
			}
		} else {
			for (final TaskSummary taskSummary : taskSummaryList) {
				taskSummaryContainerList.add(new TaskSummaryContainer(taskSummary));
			}
		}

		return taskSummaryContainerList;
	}
}
