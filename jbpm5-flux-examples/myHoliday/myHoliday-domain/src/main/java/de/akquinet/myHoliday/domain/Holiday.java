package de.akquinet.myHoliday.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Entity
public class Holiday extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	private String substitution;

	@NotNull
	private String applicant;

	public Holiday() {
	}

	public Holiday(final String applicant, final String substitution,
			final Date startDate, final Date endDate) {
		this.applicant = applicant;
		this.substitution = substitution;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(final String applicant) {
		this.applicant = applicant;
	}

	public String getSubstitution() {
		return substitution;
	}

	public void setSubstitution(final String substitution) {
		this.substitution = substitution;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date start) {
		this.startDate = start;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date end) {
		this.endDate = end;
	}

	@Override
	public String toString() {
		return "Substitution [from=" + startDate + ", to=" + endDate
				+ ", substitution=" + substitution + ", userName=" + applicant + "]";
	}

}
