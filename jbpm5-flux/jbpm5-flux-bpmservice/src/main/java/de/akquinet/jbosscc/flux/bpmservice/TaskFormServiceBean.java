package de.akquinet.jbosscc.flux.bpmservice;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.task.Task;

import de.akquinet.jbosscc.flux.bpmservice.container.TaskXContainer;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.XHumanTaskClient;
import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.HumanTaskClient;
import de.akquinet.jbosscc.flux.tdoservice.service.TaskDataObjectService;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class TaskFormServiceBean implements Serializable, TaskFormService {

	private static final long serialVersionUID = 1L;

	public static final String FORM_TYPE = "xhtml";
	public static final String DEFAULT_FORM_NAME = "default";

	@Inject
	@HumanTaskClient
	private XHumanTaskClient humanTaskClient;

	@Inject
	private TaskDataObjectService taskDataObjectServiceBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.TaskFormService#prepareForForm(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public void prepareForForm(final TaskXContainer taskContainer) {
		if (taskContainer != null) {
			final Task task = humanTaskClient.getTask(taskContainer.getId());

			final Map<String, Object> dataMap = humanTaskClient.getTaskDataMap(task);
			taskContainer.setDataMap(dataMap);

			final TaskDataObject tdo = taskDataObjectServiceBean.getTdoByTaskName(taskContainer.getName(), taskContainer.getDataMap());

			taskContainer.setTaskDataObject(tdo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.akquinet.flux.bpmservice.TaskFormService#getTaskFormName(de.akquinet.flux.bpmservice.container.TaskXContainer)
	 */
	public String getTaskFormName(final TaskXContainer taskContainer) {
		if (taskContainer != null) {
			return taskContainer.getTaskDataObject().getFormName() + "." + FORM_TYPE;
		}
		return DEFAULT_FORM_NAME + "." + FORM_TYPE;
	}
}
