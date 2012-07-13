package de.akquinet.jbosscc.flux.tdoservice.tdos.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;
import de.akquinet.jbosscc.flux.tdoservice.tdos.TaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TdoIndexData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, AbstractTaskDataObject> tdoIndex;

	public TdoIndexData(final Map<String, AbstractTaskDataObject> tdoIndex) {
		this.tdoIndex = tdoIndex;
	}

	public Map<String, AbstractTaskDataObject> getTdoIndex() {
		if (tdoIndex == null) {
			tdoIndex = new HashMap<String, AbstractTaskDataObject>();
		}
		return tdoIndex;
	}

	public TaskDataObject getTdoByTaskName(final String taskName) {
		return getTdoIndex().get(taskName);
	}
}
