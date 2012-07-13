package de.akquinet.jbosscc.flux.taskserver.mina;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jbpm.process.workitem.wsht.AbstractHTWorkItemHandler;
import org.jbpm.process.workitem.wsht.AsyncGenericHTWorkItemHandler;
import org.jbpm.task.AsyncTaskService;
import org.jbpm.task.Content;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.responsehandlers.BlockingGetContentResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingGetTaskResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;

import de.akquinet.jbosscc.flux.bpmservice.deployment.data.WSHumanTaskHandlerDelegate;
import de.akquinet.jbosscc.flux.bpmservice.deployment.qualifier.WSHumanTaskHandler;
import de.akquinet.jbosscc.flux.bpmservice.exceptions.CanNotTransformContentToDataMapException;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.XHumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.HumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.utils.HumanTaskUtils;

/**
 * Die Klasse verbindet die Process-Engiene mit dem HumanTask Service/Server und führt die Aktionen durch.
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@RequestScoped
@HumanTaskClient
public class MinaHumanTaskClient implements Serializable, XHumanTaskClient {

	private static final long serialVersionUID = 1L;

	public static final String RESULT_PARAM_NAME = "Result";
	//TODO
	public static final String LANG = "en-UK";
	public static final long WAIT_TILL_DONE = 5000;

	@Inject
	private Logger log;

	@Inject
	private HumanTaskUtils humanTaskUtils;

	@Inject
	@WSHumanTaskHandler
	private WSHumanTaskHandlerDelegate humanTaskHandlerDelegate;

	private AsyncTaskService client;
	

	/**
	 * Verbindet sich zum MinaTaskServer
	 */
	public void initClient() {
		if (this.client == null) {
			AbstractHTWorkItemHandler abstractHTWorkItemHandler = humanTaskHandlerDelegate
					.getHumanTaskWorkItemHandler();
			if (abstractHTWorkItemHandler instanceof AsyncGenericHTWorkItemHandler) {
				AsyncGenericHTWorkItemHandler asyncGenericHTWorkItemHandler = (AsyncGenericHTWorkItemHandler) abstractHTWorkItemHandler;
				this.client = asyncGenericHTWorkItemHandler.getClient();
				log.info("Client initialised");
			}
		}
	}

	/**
	 * Diese Methode liefert eine Liste aller zu dem Benutzer gehörenden Tasks
	 * 
	 * @param userId Benutzer dem die Task gehören.
	 * @return
	 */
	public List<TaskSummary> getAssignedTasks(final String userId) {
		log.info("getAssignedTasks");

		initClient();
		List<TaskSummary> tasks = null;
		try {
			final BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
			this.client.getTasksOwned(userId, LANG, responseHandler);
			tasks = responseHandler.getResults();
		} catch (final Throwable t) {
			t.printStackTrace();
		}
		return tasks;
	}

	/**
	 * Die Methode liefert alle Task zu den angegebenen Gruppen und des Benutzers.
	 * 
	 * @param userId
	 * @param groupIdList
	 * @return
	 */
	public List<TaskSummary> getAssignedTasks(final String userId,
			final List<String> groupIdList) {
		log.info("getAssignedTasks");

		initClient();
		List<TaskSummary> tasks = null;
		try {
			final BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
			this.client.getTasksAssignedAsPotentialOwner(userId, groupIdList, LANG,
					responseHandler);
			tasks = responseHandler.getResults();
		} catch (final Throwable t) {
			t.printStackTrace();
		}
		return tasks;
	}

	/**
	 * Diese Methode Bindet den Task an den angegebenen Benutzer. Notwendig, wenn ein Task nur eine Gruppenzuweisung besitzt.
	 * 
	 * @param taskId TaskId des zu bindenden Tasks
	 * @param userId UserId des Benutzer, der diesen Task haben möchte
	 * @param groupIdList GruppenId der der Task zugeordnet ist.
	 */
	public void claimTask(final long taskId, final String userId,
			final List<String> groupIdList) {
		log.info("claimTask");
		initClient();

		final BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		this.client.claim(taskId, userId, groupIdList, responseHandler);
		responseHandler.waitTillDone(WAIT_TILL_DONE);
	}

	/**
	 * Diese Methode startet einen Task
	 * 
	 * @param taskId
	 * @param userId
	 * @throws InterruptedException
	 */
	public void startTask(final long taskId, final String userId) {
		log.info("startTask");
		initClient();

		final BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		this.client.start(taskId, userId, responseHandler);
		responseHandler.waitTillDone(WAIT_TILL_DONE);
	}

	/**
	 * Diese Methode beendet den Task und übergibt eine DataMap an den Prozess. Vorher muss der Task gestartet werden und muss an dem Benutzer gebunden sein.
	 * 
	 * @param taskId
	 * @param dataMap DatenMap, die Inormationen z.B. aus dem TaskFormular enthalten kann.
	 * @param userId Benutzer der an den Task gebunden ist und diesen ausführen möchte
	 * @throws InterruptedException
	 */
	public void completeTask(final long taskId,
			final Map<String, Object> dataMap, final String userId) {
		log.info("completeTask");
		initClient();

		final ContentData contentData = this.humanTaskUtils
				.dataMapToResultContentData(dataMap, RESULT_PARAM_NAME);

		final BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		this.client.complete(taskId, userId, contentData, responseHandler);
		responseHandler.waitTillDone(WAIT_TILL_DONE);
	}

	/**
	 * Die Methode gibt einen Task wieder frei, wenn dieser durch claim an einen Benutzer gebunden wurde.
	 * 
	 * @param taskId TaskId des freizugebenden Tasks
	 * @param userId UserId des Benutzer, an dem der Task gebunden ist.
	 */
	public void revokeTask(final long taskId, final String userId) {
		log.info("revokeTask");
		initClient();
		final BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		this.client.release(taskId, userId, responseHandler);
		responseHandler.waitTillDone(WAIT_TILL_DONE);
	}

	/**
	 * Diese Methode überträgt den Task von einem Benutzer zu einem anderen Benutzer.
	 * 
	 * @param taskId TaskId des Tasks, der übergeben werden soll
	 * @param newUserId UserId des neuen Benutzers
	 * @param userId UserId des alten Benutzers
	 */
	public void assignTask(final long taskId, final String userId,
			final String newUserId, final List<String> newUserGroupIdList) {
		initClient();
		final BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		if (newUserId == null) {
			this.client.release(taskId, userId, responseHandler);
		} else if (newUserId.equals(userId)) {
			if (newUserGroupIdList == null) {
				this.client.claim(taskId, newUserId, responseHandler);
			} else {
				this.client.claim(taskId, newUserId, newUserGroupIdList,
						responseHandler);
			}
		} else {
			this.client.delegate(taskId, userId, newUserId, responseHandler);
		}
		responseHandler.waitTillDone(WAIT_TILL_DONE);
	}

	public Task getTask(final long taskId) {
		initClient();
		/*
		 * Es gibt zwei Implementierungen in org.jbpm.process.workitem.wsht und org.jbpm.task.service.responsehandlers Diese scheinen sich nur zwischen der Möglichkeit, eine
		 * taskWaitTime anzugeben.
		 */
		final BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();
		this.client.getTask(taskId, responseHandler);

		return responseHandler.getTask();
	}

	/*
	 * Holt den Content zu einem Task Das ist noch die Vorstufe der ParamMap, die z.B. beim ProzessStart mit übergeben werden kann.
	 */
	public Content getTaskContent(final Task task) {
		log.info("getTaskContent");
		if (task != null) {
			final long contentId = task.getTaskData().getDocumentContentId();
			if (contentId != -1) {
				initClient();

				final BlockingGetContentResponseHandler getContentResponseHandler = new BlockingGetContentResponseHandler();
				this.client.getContent(contentId, getContentResponseHandler);
				return getContentResponseHandler.getContent();
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
