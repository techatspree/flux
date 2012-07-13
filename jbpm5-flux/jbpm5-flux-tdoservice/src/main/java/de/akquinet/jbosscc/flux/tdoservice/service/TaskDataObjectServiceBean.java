package de.akquinet.jbosscc.flux.tdoservice.service;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;
import de.akquinet.jbosscc.flux.tdoservice.tdos.index.TdoIndexData;
import de.akquinet.jbosscc.flux.tdoservice.tdos.index.qualifier.TdoIndex;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class TaskDataObjectServiceBean implements TaskDataObjectService,
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@TdoIndex
	private TdoIndexData tdoIndexData;

	public TaskDataObject getTdoByTaskName(final String taskName,
			final Map<String, Object> dataMap) {
		final TaskDataObject tdo = tdoIndexData.getTdoByTaskName(taskName);
		tdo.setDataMap(dataMap);
		return tdo;
	}

}
