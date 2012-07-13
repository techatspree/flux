package de.akquinet.myHoliday.tdos;

import java.util.Date;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Tdo(task = "check request for leave", form = "check_request_for_leave")
public class CheckRequestForLeaveTDO extends AbstractTaskDataObject {

	private static final long serialVersionUID = 1L;

	public void setStartDate(Date date) {
	}

	public Date getStartDate() {
		return (Date) this.getValue("holiday_start");
	}

	public void setEndDate(Date date) {
	}

	public Date getEndDate() {
		return (Date) this.getValue("holiday_end");
	}

	public void setApplicant(final String applicant) {
	}

	public String getApplicant() {
		return (String) this.getValue("applicant");
	}

	public void setAccepted(String accepted) {
		this.setValue("accepted", Boolean.parseBoolean(accepted));
	}

	public String getAccepted() {
		Object o = this.getValue("accepted");
		if (o != null && o instanceof Boolean) {
			if ((Boolean) o) {
				return "true";
			}
		}
		return "false";
	}
}
