package de.akquinet.jbosscc.flux.taskserver.local;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;

import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformContentToDataMapException;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.XHumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.HumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.utils.HumanTaskUtils;

/**
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@SessionScoped
@HumanTaskClient
public class LocalHumanTaskClient implements Serializable, XHumanTaskClient {

	private static final long serialVersionUID = 1L;

	public static final String RESULT_PARAM_NAME = "Result";
	public static final String LANG = "en-UK";

	@Inject
	private Logger log;

	@Inject
	private HumanTaskUtils humanTaskUtils;

	@Inject
	private LocalTaskServiceDelegator localTaskServiceDelegator;

	public List<TaskSummary> getAssignedTasks(final String userId) {
		return localTaskServiceDelegator.getTasksOwned(userId, LANG);
	}

	public List<TaskSummary> getAssignedTasks(final String userId,
			final List<String> groupIdList) {
		return localTaskServiceDelegator.getTasksAssignedAsPotentialOwner(userId,
				groupIdList, LANG);
	}

	public void claimTask(final long taskId, final String userId,
			final List<String> groupIdList) {
		localTaskServiceDelegator.claim(taskId, userId, groupIdList);
	}

	public void startTask(final long taskId, final String userId) {
		localTaskServiceDelegator.start(taskId, userId);
	}

	public void completeTask(final long taskId,
			final Map<String, Object> dataMap, final String userId) {
		final ContentData contentData = this.humanTaskUtils
				.dataMapToResultContentData(dataMap, RESULT_PARAM_NAME);
		localTaskServiceDelegator.complete(taskId, userId, contentData);
	}

	public void revokeTask(final long taskId, final String userId) {
		localTaskServiceDelegator.release(taskId, userId);
	}

	public void assignTask(final long taskId, final String userId,
			final String newUserId, final List<String> newUserGroupIdList) {
		if (newUserId == null) {
			localTaskServiceDelegator.release(taskId, userId);
		} else if (newUserId.equals(userId)) {
			if (newUserGroupIdList == null) {
				localTaskServiceDelegator.claim(taskId, newUserId);
			} else {
				localTaskServiceDelegator.claim(taskId, newUserId, newUserGroupIdList);
			}
		} else {
			localTaskServiceDelegator.delegate(taskId, userId, newUserId);
		}
	}

	public Task getTask(final long taskId) {
		return localTaskServiceDelegator.getTask(taskId);
	}

	public Content getTaskContent(final Task task) {
		if (task != null) {
			final long contentId = task.getTaskData().getDocumentContentId();

			if (contentId != -1) {
				return localTaskServiceDelegator.getContent(contentId);
			}
		}

		return null;
	}

	public Map<String, Object> getTaskDataMap(final Task task) {
		final Content content = getTaskContent(task);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			dataMap = humanTaskUtils.contentToDataMap(content, task);
		} catch (final CanNotTransformContentToDataMapException e) {
			log.error(e);
		}

		return dataMap;
	}
}
