package de.akquinet.myHoliday.business;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import de.akquinet.myHoliday.dao.HolidayDao;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class HolidayServiceBean implements HolidayService {

	@Inject
	private Logger log;

	@EJB
	private HolidayDao holidayDao;

	@Override
	public Holiday findById(final Long id) {
		return holidayDao.load(id);
	}

	@Override
	public void saveHoliday(final Holiday substitution) {
		holidayDao.persist(substitution);
	}

	@Override
	public Holiday updateHoliday(final Holiday holiday) {
		Holiday loadedHoliday = findById(holiday.getId());
		loadedHoliday.setStartDate(holiday.getStartDate());
		loadedHoliday.setEndDate(holiday.getEndDate());
		loadedHoliday.setApplicant(holiday.getApplicant());
		loadedHoliday.setSubstitution(holiday.getSubstitution());
		holidayDao.persist(loadedHoliday);

		return loadedHoliday;
	}

	@Override
	public void deleteHoliday(final Holiday substitution) {
		log.debugv("delete substitution {0}", substitution.getId());
		holidayDao.delete(findById(substitution.getId()));
	}

	@Override
	public List<Holiday> findAllHolidays() {
		return holidayDao.loadAll();
	}

	@Override
	public Holiday createHoliday(final String userName,
			final String substitution, final Date start, final Date end) {
		final Holiday holiday = new Holiday(userName, substitution, start, end);
		log.info("create Holiday: " + holiday.toString());
		return holiday;
	}

	@Override
	public Holiday createAndSaveHoliday(String userName, String substitution,
			Date start, Date end) {
		final Holiday holiday = createHoliday(userName, substitution, start, end);
		saveHoliday(holiday);

		return holiday;
	}

	public int getHolidayNumber(Holiday holiday) {
		if (holiday != null) {
			return Days.daysBetween(new DateTime(holiday.getStartDate()),
					new DateTime(holiday.getEndDate())).getDays();
		} else {
			return 0;
		}
	}
}
