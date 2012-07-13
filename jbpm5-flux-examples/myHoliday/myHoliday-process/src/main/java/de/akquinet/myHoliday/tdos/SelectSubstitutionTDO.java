package de.akquinet.myHoliday.tdos;

import javax.inject.Inject;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;
import de.akquinet.myHoliday.business.HolidayService;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Tdo(task = "select substitution", form = "select_substitution")
public class SelectSubstitutionTDO extends AbstractTaskDataObject {

	private static final long serialVersionUID = 1L;

	@Inject
	private HolidayService holidayService;

	public void setHoliday(final Holiday holiday) {
		this.setValue("holiday", holiday);
	}

	public Holiday getHoliday() {
		return (Holiday) this.getValue("holiday");
	}

	public void setTime(final int time) {
	}

	public int getTime() {
		return holidayService.getHolidayNumber(getHoliday());
	}

	public void setApplicant(final String applicant) {
	}

	public String getApplicant() {
		return (String) this.getValue("applicant");
	}
}
