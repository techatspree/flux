package de.akquinet.jbosscc.flux.bpmservice;

import java.util.List;

import org.jbpm.task.Status;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

public interface WorkflowManagementService {

	/**
	 * Liefert eine List mit allen für den User gefundenen Tasks zurück, gefiltert nach dem Status
	 */
	List<TaskXContainer> getTasksForUserAndGroups(final String userId, final List<String> groupIdList, final Status taskStateFilter);

	/**
	 * Startet einen Task und bereitet die FormularDaten vor
	 * 
	 * @param tc
	 *          Der Task der gestartet werden soll
	 */
	void startTask(final TaskXContainer tc, final String userId, final List<String> groupIdList);

	/**
	 * Beendet einen Task
	 * 
	 * @param tc Der Task der beendet werden soll
	 */
	void completeTask(final TaskXContainer tc, final String userId);

	/**
	 * Gibt den Task wieder frei
	 * 
	 * @param tc Der Task der beendet werden soll
	 */
	void revokeTask(final TaskXContainer tc, final String userId);

	void delegatTask(final TaskXContainer tc, final String actualUserId, final String newUserId, final List<String> newUserGroupIdList);

	TaskXContainer getTask(final long taskId, final String userId);

	/*
	 * Die Methode liefert den TaskNamen, um das entsprechende Formular laden zu können
	 */
	String getTaskFormNameForTaskId(final TaskXContainer taskContainer);

	/*
	 * Die Methode bekommt ein TaskContainer und stellt ein TaskDataObject bereit. Mit dem TaskDataObject wird es möglich, über ein Formular die DataMap auszugeben und die Daten zu
	 * bearbeiten. Mit zum Beispiel prepareForForm wird so ein TaskDataObject geladen, gefüllt und in den TaskContainer gepackt.
	 */
	TaskDataObject getTaskFormData(final TaskXContainer taskContainer);

	/**
	 * Prüft, ob der Task (TaskContainer) noch nicht in Bearbeitung ist oder schon beendet wurde.
	 * 
	 * @param tc TaskContainer der geprüft werden soll
	 * @return Liefert True, wenn der Task noch nicht in Bearbeitung ist oder beendet wurde.
	 */
	boolean isNotInProgressOrCompleted(final TaskXContainer tc);

	/**
	 * Prüft, ob der Task (TaskContainer) noch nicht beendet wurde.
	 * 
	 * @param tc TaskContainer der geprüft werden soll
	 * @return Liefert True, wenn der Task noch nicht beendet wurde.
	 */
	boolean isNotCompleted(final TaskXContainer tc);

}