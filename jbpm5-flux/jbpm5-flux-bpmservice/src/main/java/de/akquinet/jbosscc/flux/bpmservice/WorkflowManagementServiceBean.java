package de.akquinet.jbosscc.flux.bpmservice;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskContainer;
import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.XHumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.HumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.utils.TaskSummaryUtils;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * Der WorkflowManagementService stellt Methoden für das Workflwo-Management bereit
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class WorkflowManagementServiceBean implements Serializable, WorkflowManagementService {

	private static final long serialVersionUID = 1L;

	@Inject
	@HumanTaskClient
	private XHumanTaskClient htcManagerService;

	@Inject
	private TaskSummaryUtils taskSummaryUtils;

	@Inject
	private TaskFormService taskFromService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#getTasksForUserAndGroups(java.lang.String, java.util.List, org.jbpm.task.Status)
	 */
	public List<TaskXContainer> getTasksForUserAndGroups(final String userId, final List<String> groupIdList, final Status taskStateFilter) {
		final List<TaskSummary> taskSummaryList = htcManagerService.getAssignedTasks(userId, groupIdList);
		
		return taskSummaryUtils.transformToTaskSummaryContainerList(taskSummaryList, taskStateFilter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#startTask(de.akquinet.flux.bpmservice.container.TaskXContainer, java.lang.String, java.util.List)
	 */
	public void startTask(final TaskXContainer tc, final String userId, final List<String> groupIdList) {
		if (isNotCompleted(tc)) {
			this.taskFromService.prepareForForm(tc);
		}

		if (isNotInProgressOrCompleted(tc)) {
			if (!tc.hasActualOwner()) {
				this.htcManagerService.claimTask(tc.getId(), userId, groupIdList);
			}
			this.htcManagerService.startTask(tc.getId(), userId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#completeTask(de.akquinet.flux.bpmservice.container.TaskXContainer, java.lang.String)
	 */
	public void completeTask(final TaskXContainer tc, final String userId) {
		if (isNotCompleted(tc)) {
			this.htcManagerService.completeTask(tc.getId(), tc.getDataMap(), userId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#revokeTask(de.akquinet.flux.bpmservice.container.TaskXContainer, java.lang.String)
	 */
	public void revokeTask(final TaskXContainer tc, final String userId) {
		if (isNotCompleted(tc)) {
			this.htcManagerService.revokeTask(tc.getId(), userId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#delegatTask(de.akquinet.flux.bpmservice.container.TaskXContainer, java.lang.String, java.lang.String,
	 * java.util.List)
	 */
	public void delegatTask(final TaskXContainer tc, final String actualUserId, final String newUserId, final List<String> newUserGroupIdList) {
		if (tc != null && newUserId != null) {
			this.htcManagerService.assignTask(tc.getId(), actualUserId, newUserId,
					newUserGroupIdList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#getTask(long, java.lang.String)
	 */
	public TaskXContainer getTask(final long taskId, final String userId) {
		return new TaskContainer(this.htcManagerService.getTask(taskId));
	}

	/*
	 * Die Methode liefert den TaskNamen, um das entsprechende Formular laden zu können
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#getTaskFormNameForTaskId(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public String getTaskFormNameForTaskId(final TaskXContainer taskContainer) {
		return this.taskFromService.getTaskFormName(taskContainer);
	}

	/*
	 * Die Methode bekommt ein TaskContainer und stellt ein TaskDataObject bereit. Mit dem TaskDataObject wird es möglich, über ein Formular die DataMap auszugeben und die Daten zu
	 * bearbeiten. Mit zum Beispiel prepareForForm wird so ein TaskDataObject geladen, gefüllt und in den TaskContainer gepackt.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#getTaskFormData(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public TaskDataObject getTaskFormData(final TaskXContainer taskContainer) {
		return taskContainer.getTaskDataObject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#isNotInProgressOrCompleted(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public boolean isNotInProgressOrCompleted(final TaskXContainer tc) {
		if (tc != null) {
			return tc.getStatus() != Status.InProgress && isNotCompleted(tc);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.WorkflowManagementService2#isNotCompleted(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public boolean isNotCompleted(final TaskXContainer tc) {
		if (tc != null) {
			return tc.getStatus() != Status.Completed;
		}

		return false;
	}
}
