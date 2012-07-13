package de.akquinet.myHoliday.taskitems;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import de.akquinet.jbosscc.flux.taskitems.stereotypes.TaskItemHandler;
import de.akquinet.myHoliday.business.HolidayService;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * SaveSubstitutionTaskItem, der die Vertretung in die Datenbank loggt
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@TaskItemHandler(taskName = "create holiday")
public class SaveHolidayTaskItem implements WorkItemHandler, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String INMAP_PARAM_APPLICANT = "applicant";
	public static final String INMAP_PARAM_START = "holiday_start";
	public static final String INMAP_PARAM_END = "holiday_end";

	public static final String OUTMAP_PARAM_HOLIDAY = "holiday";
	public static final String OUTMAP_PARAM_DAYS = "days";

	@Inject
	private HolidayService holidayService;

	@Override
	public void abortWorkItem(final WorkItem arg0, final WorkItemManager arg1) {
	}

	@Override
	public void executeWorkItem(final WorkItem workItem,
			final WorkItemManager workItemManager) {

		final Object applicant = workItem.getParameter(INMAP_PARAM_APPLICANT);
		final Object start = workItem.getParameter(INMAP_PARAM_START);
		final Object end = workItem.getParameter(INMAP_PARAM_END);

		final Map<String, Object> outDataMap = new HashMap<String, Object>();

		if (applicant != null && start != null && end != null) {
			Holiday holiday = holidayService.createAndSaveHoliday((String) applicant,
					null, (Date) start, (Date) end);
			int days = holidayService.getHolidayNumber(holiday);

			outDataMap.put(OUTMAP_PARAM_HOLIDAY, holiday);
			outDataMap.put(OUTMAP_PARAM_DAYS, days);
		}

		workItemManager.completeWorkItem(workItem.getId(), outDataMap);
	}
}
