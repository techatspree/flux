package de.akquinet.jbosscc.flux.taskserver.local;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.drools.SystemEventListenerFactory;
import org.jboss.logging.Logger;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.identity.UserGroupCallback;
import org.jbpm.task.identity.UserGroupCallbackManager;
import org.jbpm.task.service.local.LocalTaskService;

import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.CustomUserGroupCallback;

@Named
@ApplicationScoped
public class LocalTaskServiceDelegatorBean implements Serializable,
		LocalTaskServiceDelegator {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
	private EntityManagerFactory emf;

	@Inject
	@CustomUserGroupCallback
	private UserGroupCallback customJbpmUsergroupCallback;

	private LocalTaskService localTaskService;

	// ---------------- postconstruct --------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#init()
	 */
	@PostConstruct
	public void init() {

		log.info("init LocalTaskServiceDelegatorBean");
		localTaskService = new LocalTaskService(new TaskService(emf,
				SystemEventListenerFactory.getSystemEventListener()));

		UserGroupCallbackManager.resetCallback();
		UserGroupCallbackManager.getInstance().setCallback(
				customJbpmUsergroupCallback);
		log.info("UserGroupCallbackName: "
				+ UserGroupCallbackManager.getInstance().getCallback().getClass()
						.getName());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#getTasksOwned(java.lang.String, java.lang.String)
	 */
	public List<TaskSummary> getTasksOwned(final String userId,
			final String language) {
		return localTaskService.getTasksOwned(userId, language);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#getTasksAssignedAsPotentialOwner(java.lang.String, java.util.List, java.lang.String)
	 */
	public List<TaskSummary> getTasksAssignedAsPotentialOwner(
			final String userId, final List<String> groupIds, final String language) {
		return localTaskService.getTasksAssignedAsPotentialOwner(userId, groupIds,
				language);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#claim(long, java.lang.String, java.util.List)
	 */
	public void claim(final long taskId, final String userId,
			final List<String> groupIds) {
		localTaskService.claim(taskId, userId, groupIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#start(long, java.lang.String)
	 */
	public void start(final long taskId, final String userId) {
		localTaskService.start(taskId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#complete(long, java.lang.String, org.jbpm.task.service.ContentData)
	 */
	public void complete(final long taskId, final String userId,
			final ContentData contentData) {
		localTaskService.complete(taskId, userId, contentData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#release(long, java.lang.String)
	 */
	public void release(final long taskId, final String userId) {
		localTaskService.release(taskId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#claim(long, java.lang.String)
	 */
	public void claim(final long taskId, final String userId) {
		localTaskService.claim(taskId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#delegate(long, java.lang.String, java.lang.String)
	 */
	public void delegate(final long taskId, final String userId,
			final String newUserId) {
		localTaskService.delegate(taskId, userId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#getTask(long)
	 */
	public Task getTask(final long taskId) {
		return localTaskService.getTask(taskId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#getContent(long)
	 */
	public Content getContent(final long contentId) {
		return localTaskService.getContent(contentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.taskserver.local.LocalTaskServiceDelegator#getLocalTaskService()
	 */
	public org.jbpm.task.TaskService getLocalTaskService() {
		return localTaskService;
	}

}
