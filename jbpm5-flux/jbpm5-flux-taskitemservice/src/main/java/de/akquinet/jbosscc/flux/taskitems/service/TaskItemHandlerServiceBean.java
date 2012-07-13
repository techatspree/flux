package de.akquinet.jbosscc.flux.taskitems.service;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.runtime.process.WorkItemHandler;

import de.akquinet.jbosscc.flux.taskitems.index.TaskItemIndexData;
import de.akquinet.jbosscc.flux.taskitems.index.qualifier.TaskItemIndex;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class TaskItemHandlerServiceBean implements TaskItemHandlerService,
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@TaskItemIndex
	private TaskItemIndexData taskItemIndexData;

	public Map<String, WorkItemHandler> getTaskItems() {
		return taskItemIndexData.getTaskItemIndex();
	}
}
