package de.akquinet.jbosscc.flux.taskserver.local;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;

public interface LocalTaskServiceDelegator {

	@PostConstruct
	void init();

	List<TaskSummary> getTasksOwned(final String userId, final String language);

	List<TaskSummary> getTasksAssignedAsPotentialOwner(final String userId, final List<String> groupIds, final String language);

	void claim(final long taskId, final String userId, final List<String> groupIds);

	void start(final long taskId, final String userId);

	void complete(final long taskId, final String userId, final ContentData contentData);

	void release(final long taskId, final String userId);

	void claim(final long taskId, final String userId);

	void delegate(final long taskId, final String userId, final String newUserId);

	Task getTask(final long taskId);

	Content getContent(final long contentId);

	org.jbpm.task.TaskService getLocalTaskService();

}