package de.akquinet.myHoliday.dao;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;
import de.akquinet.myHoliday.dao.UserAccountRoleDao;
import de.akquinet.myHoliday.dao.UserAccountRoleDaoBean;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class UserAccountRoleDaoTest extends BaseNeedleTest {

	@ObjectUnderTest
	private final UserAccountRoleDao userAccountRoleDao = new UserAccountRoleDaoBean();	

	@Test
	public void testSaveUserAccountRole() throws Exception {
		final AccountRole accountRole = AccountRole.TECHNICALADMIN;

		UserAccountRole userAccountRole = userAccountRoleDao.findByAccountRole(accountRole);
		Assert.assertNull(userAccountRole);			
		
		
		final UserAccountRole uar = new UserAccountRole(accountRole);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountRoleDao.persist(uar);
			}
		});		
		
		Assert.assertEquals(1, userAccountRoleDao.loadAll().size());
		userAccountRole = userAccountRoleDao.findByAccountRole(accountRole);
		
		Assert.assertNotNull(userAccountRole);		
	}
	
	@Test
	public void testLoadUserAccountRole() throws Exception {
		Assert.assertEquals(0, userAccountRoleDao.loadAll().size());		
		
		final UserAccountRole uar = new UserAccountRole(AccountRole.TECHNICALADMIN);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountRoleDao.persist(uar);
			}
		});		
		
		Assert.assertEquals(1, userAccountRoleDao.loadAll().size());
		final UserAccountRole userAccountRole = userAccountRoleDao.findByAccountRole(AccountRole.ADMINISTRATION);
		
		Assert.assertNull(userAccountRole);		
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFailSaveUser() throws Exception {
		final AccountRole accountRole = null;

		final UserAccountRole userAccountRole = userAccountRoleDao.findByAccountRole(accountRole);
		Assert.assertNull(userAccountRole);			
		
		final UserAccountRole uar = new UserAccountRole(accountRole);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountRoleDao.persist(uar);
			}
		});		
		Assert.fail("sould not have been reached!");
	}
}
