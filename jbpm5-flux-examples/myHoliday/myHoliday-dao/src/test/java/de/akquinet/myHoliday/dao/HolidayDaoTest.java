package de.akquinet.myHoliday.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;
import de.akquinet.myHoliday.dao.HolidayDao;
import de.akquinet.myHoliday.dao.HolidayDaoBean;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class HolidayDaoTest extends BaseNeedleTest {

	@ObjectUnderTest
	private final HolidayDao holidayDao = new HolidayDaoBean();

	@Test
	public void testSaveDataLogEntry() throws Exception {

		final Holiday s = new Holiday("userName", "who", new Date(), new Date());
		
		Assert.assertEquals(0, holidayDao.loadAll().size());

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				holidayDao.persist(s);
			}
		});

		Assert.assertEquals(1, holidayDao.loadAll().size());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFailSaveDataLogEntry() throws Exception {

		final Holiday s = new Holiday(null, "who", new Date(), new Date());
		
		Assert.assertEquals(0, holidayDao.loadAll().size());
		
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				holidayDao.persist(s);
			}
		});
	}
}
