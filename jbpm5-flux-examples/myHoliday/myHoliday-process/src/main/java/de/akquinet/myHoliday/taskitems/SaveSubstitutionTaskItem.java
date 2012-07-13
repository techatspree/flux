package de.akquinet.myHoliday.taskitems;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.jboss.logging.Logger;

import de.akquinet.jbosscc.flux.taskitems.stereotypes.TaskItemHandler;
import de.akquinet.myHoliday.business.HolidayService;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * SaveSubstitutionTaskItem, der die Vertretung in die Datenbank loggt
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@TaskItemHandler(taskName = "update holiday")
public class SaveSubstitutionTaskItem implements WorkItemHandler, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String INMAP_PARAM_HOLIDAY = "holiday";

	public static final String OUTMAP_PARAM_HOLIDAY = "holiday";

	@Inject
	private Logger log;

	@Inject
	private HolidayService holidayService;

	@Override
	public void abortWorkItem(final WorkItem arg0, final WorkItemManager arg1) {
	}

	@Override
	public void executeWorkItem(final WorkItem workItem,
			final WorkItemManager workItemManager) {

		final Object holidayObject = workItem.getParameter(INMAP_PARAM_HOLIDAY);
		final Map<String, Object> outDataMap = new HashMap<String, Object>();

		if (holidayObject != null) {
			Holiday holiday = holidayService.updateHoliday((Holiday) holidayObject);
			outDataMap.put(OUTMAP_PARAM_HOLIDAY, holiday);
		}

		workItemManager.completeWorkItem(workItem.getId(), outDataMap);
	}
}
