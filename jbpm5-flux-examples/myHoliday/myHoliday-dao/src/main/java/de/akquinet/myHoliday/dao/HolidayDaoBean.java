package de.akquinet.myHoliday.dao;

import javax.ejb.Stateless;

import de.akquinet.myHoliday.dao.common.JpaGenericDao;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class HolidayDaoBean extends JpaGenericDao<Holiday> implements
		HolidayDao {

}
