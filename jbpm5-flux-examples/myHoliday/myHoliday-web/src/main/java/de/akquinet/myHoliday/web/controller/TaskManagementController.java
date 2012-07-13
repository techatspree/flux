package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.task.Status;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;
import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.process.TaskManagementBean;
import de.akquinet.myHoliday.web.security.UserPermissionController;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
@Stateful
public class TaskManagementController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TaskManagementBean taskManagementServiceBean;

	@Inject
	private UserPermissionController security;

	private final DataModel<TaskXContainer> taskDataModel;
	private UserAccount actualUser;
	private TaskXContainer actualTask;

	private Status taskStateFilter;

	public TaskManagementController() {
		this.taskDataModel = new ListDataModel<TaskXContainer>();
		this.actualUser = null;
		taskStateFilter = null;
		this.actualTask = null;
	}

	public DataModel<TaskXContainer> getDataModel() {
		rereadTaskList();
		return this.taskDataModel;
	}

	private void rereadTaskList() {
		actualUser = security.getUserAccount();
		if (this.actualUser != null) {
			this.taskDataModel.setWrappedData(this.taskManagementServiceBean
					.getTasksForUserAndGroups(this.actualUser, this.taskStateFilter));
		}
	}

	/**
	 * Startet einen Task (taskinit) Es soll dann die Möglichkeit entstehen, Daten einzugeben, die für den Abschluss des Task benötigt werden
	 */
	public void startTask() {
		this.actualTask = taskDataModel.getRowData();
		this.taskManagementServiceBean.startTask(this.actualTask, this.actualUser);
	}

	/**
	 * Beendet einen Task Diese Funktion nimmt benutzereingaben entgegen und übergibt diese dem Task in einer Map und führt dann completeTask aus
	 */
	public void completeTask() {
		this.taskManagementServiceBean.completeTask(this.actualTask,
				this.actualUser);
		this.actualTask = null;
	}

	public void closeTaskForm() {
		this.actualTask = null;
	}

	public void revokeTask() {
		this.taskManagementServiceBean.revokeTask(this.actualTask, this.actualUser);
		this.actualTask = null;
	}

	/* --- TaskForm --- */

	public String getTaskFormName() {
		return this.taskManagementServiceBean.getTaskFormName(this.actualTask);
	}

	public TaskDataObject getTaskFormData() {
		return this.taskManagementServiceBean.getTaskFormData(this.actualTask);
	}

	public void setTaskStateFilter(final Status taskStateFilter) {
		this.taskStateFilter = taskStateFilter;
	}

	public Status getTaskStateFilter() {
		return this.taskStateFilter;
	}

	public void taskStateFilterChanged(final ValueChangeEvent event) {
		this.taskStateFilter = (Status) event.getNewValue();
	}

	public boolean isTaskSelected() {
		if (this.actualTask != null) {
			return true;
		} else {
			return false;
		}
	}
}
