package de.akquinet.myHoliday.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Local
public interface HolidayService {

	Holiday findById(final Long id);

	void saveHoliday(final Holiday dataLogEntry);

	Holiday updateHoliday(final Holiday dataLogEntry);

	void deleteHoliday(final Holiday dataLogEntry);

	List<Holiday> findAllHolidays();

	Holiday createHoliday(final String userName, final String substitution,
			final Date start, final Date end);

	Holiday createAndSaveHoliday(final String userName,
			final String substitution, final Date start, final Date end);

	int getHolidayNumber(Holiday holiday);
}
