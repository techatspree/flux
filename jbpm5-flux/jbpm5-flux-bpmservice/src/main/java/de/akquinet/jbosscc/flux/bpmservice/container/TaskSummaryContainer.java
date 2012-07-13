package de.akquinet.jbosscc.flux.bpmservice.container;

import org.jbpm.task.Status;
import org.jbpm.task.User;
import org.jbpm.task.query.TaskSummary;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TaskSummaryContainer extends AbstractTaskContainer<TaskSummary> {

	public TaskSummaryContainer(final TaskSummary taskSummary) {
		super(taskSummary);
	}

	public TaskSummary getTaskSummary() {
		return this.getObject();
	}

	@Override
	public long getId() {
		return this.getObject().getId();
	}

	@Override
	public String getName() {
		return this.getObject().getName();
	}

	@Override
	public Status getStatus() {
		return this.getObject().getStatus();
	}

	@Override
	public User getActualOwner() {
		return this.getObject().getActualOwner();
	}

	@Override
	public String getInfo() {
		return this.getObject().getDescription();
	}

}
