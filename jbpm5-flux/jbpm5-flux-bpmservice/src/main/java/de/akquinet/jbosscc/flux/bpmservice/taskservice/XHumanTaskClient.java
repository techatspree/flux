package de.akquinet.jbosscc.flux.bpmservice.taskservice;

import java.util.List;
import java.util.Map;

import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public interface XHumanTaskClient {

	List<TaskSummary> getAssignedTasks(final String userId);

	List<TaskSummary> getAssignedTasks(final String userId, final List<String> groupIdList);

	void claimTask(final long taskId, final String userId, final List<String> groupIdList);

	void startTask(final long taskId, final String userId);

	void completeTask(final long taskId, final Map<String, Object> dataMap, final String userId);

	void revokeTask(final long taskId, final String userId);

	void assignTask(final long taskId, final String userId, final String newUserId, final List<String> newUserGroupIdList);

	Task getTask(final long taskId);

	Content getTaskContent(final Task task);

	Map<String, Object> getTaskDataMap(final Task task);
}
