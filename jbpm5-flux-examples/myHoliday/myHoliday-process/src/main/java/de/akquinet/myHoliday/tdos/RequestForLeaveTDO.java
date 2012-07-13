package de.akquinet.myHoliday.tdos;

import java.util.Date;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Tdo(task = "request for leave", form = "request_for_leave", isRevocable = false)
public class RequestForLeaveTDO extends AbstractTaskDataObject {

	private static final long serialVersionUID = 1L;

	public void setStartDate(final Date date) {
		this.setValue("holiday_start", date);
	}

	public Date getStartDate() {
		return new Date();
	}

	public void setEndDate(Date date) {
		this.setValue("holiday_end", date);
	}

	public Date getEndDate() {
		return new Date();
	}

	public void setApplicant(final String applicant) {
	}

	public String getApplicant() {
		return (String) this.getValue("applicant");
	}
}
