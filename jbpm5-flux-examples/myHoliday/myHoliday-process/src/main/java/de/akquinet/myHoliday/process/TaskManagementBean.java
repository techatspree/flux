package de.akquinet.myHoliday.process;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jbpm.task.Status;

import de.akquinet.jbosscc.flux.bpmservice.WorkflowManagementService;
import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;
import de.akquinet.myHoliday.business.utils.UserAccountUtils;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class TaskManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private WorkflowManagementService workflowManagementService;

	@Inject
	private UserAccountUtils userAccountUtils;

	public List<TaskXContainer> getTasksForUserAndGroups(
			final UserAccount userAccount, final Status taskStateFilter) {
		final List<String> groupIdList = userAccountUtils
				.userAccountRolesToStringList(userAccount);
		return this.workflowManagementService.getTasksForUserAndGroups(
				userAccount.getUserName(), groupIdList, taskStateFilter);
	}

	/**
	 * Startet einen Task (taskinit) Es soll dann die Möglichkeit entstehen, Daten einzugeben, die für den Abschluss des Task benötigt werden
	 */
	public void startTask(final TaskXContainer actualTask,
			final UserAccount actualUser) {
		log.info("start Task");

		if (actualTask != null && actualUser != null) {
			final List<String> groupIdList = userAccountUtils
					.userAccountRolesToStringList(actualUser);
			this.workflowManagementService.startTask(actualTask,
					actualUser.getUserName(), groupIdList);
		}
	}

	/**
	 * Beendet einen Task Diese Funktion nimmt benutzereingaben entgegen und übergibt diese dem Task in einer Map und führt dann completeTask aus
	 */
	public void completeTask(final TaskXContainer actualTask,
			final UserAccount actualUser) {
		log.info("complete Task");

		if (actualTask != null) {
			this.workflowManagementService.completeTask(actualTask,
					actualUser.getUserName());
		}
	}

	public void revokeTask(final TaskXContainer actualTask,
			final UserAccount actualUser) {
		log.info("cancel Task");

		if (actualTask != null) {
			this.workflowManagementService.revokeTask(actualTask,
					actualUser.getUserName());
		}
	}

	/* --- TaskForm --- */

	public String getTaskFormName(final TaskXContainer actualTask) {
		return this.workflowManagementService.getTaskFormNameForTaskId(actualTask);
	}

	public TaskDataObject getTaskFormData(final TaskXContainer actualTask) {
		return this.workflowManagementService.getTaskFormData(actualTask);
	}
}
