package de.akquinet.myHoliday.tdos;

import java.util.Date;

import javax.inject.Inject;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;
import de.akquinet.myHoliday.business.HolidayService;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Tdo(task = "confirm answer", form = "confirm_answer", isRevocable = false)
public class ConfirmAnswerTDO extends AbstractTaskDataObject {

	private static final long serialVersionUID = 1L;

	@Inject
	private HolidayService holidayService;

	public void setHoliday(final Holiday holiday) {
		this.setValue("holiday", holiday);
	}

	public Holiday getHoliday() {
		return (Holiday) this.getValue("holiday");
	}

	public void setStartDate(final Date date) {
	}

	public Date getStartDate() {
		if (getHoliday() != null) {
			return getHoliday().getStartDate();
		}
		return new Date();
	}

	public void setEndDate(Date date) {
	}

	public Date getEndDate() {
		if (getHoliday() != null) {
			return getHoliday().getEndDate();
		}
		return new Date();
	}

	public boolean isAccpepted() {
		return (Boolean) this.getValue("accepted");
	}

	public void setAccepted(final boolean accepted) {
		this.setValue("accepted", accepted);
	}

	public boolean getAccepted() {
		return (Boolean) this.getValue("accepted");
	}

	public void setTime(final int time) {
	}

	public int getTime() {
		return holidayService.getHolidayNumber(getHoliday());
	}
}
