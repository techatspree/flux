package de.akquinet.jbosscc.flux.bpmservice.container;

import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.User;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TaskContainer extends AbstractTaskContainer<Task> {

	public TaskContainer(final Task task) {
		super(task);
	}

	public Task getTask() {
		return this.getObject();
	}

	@Override
	public long getId() {
		return this.getObject().getId();
	}

	@Override
	public String getName() {
		// TODO getName lokalisieren?
		return this.getObject().getNames().get(0).getText();
	}

	@Override
	public Status getStatus() {
		return this.getObject().getTaskData().getStatus();
	}

	@Override
	public User getActualOwner() {
		return this.getObject().getTaskData().getActualOwner();
	}

	@Override
	public String getInfo() {
		// TODO Lokalisierung
		return this.getObject().getDescriptions().get(0).getText();
	}
}
