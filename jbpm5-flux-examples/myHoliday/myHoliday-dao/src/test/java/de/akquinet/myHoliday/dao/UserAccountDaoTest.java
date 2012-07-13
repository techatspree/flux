package de.akquinet.myHoliday.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;
import de.akquinet.myHoliday.dao.UserAccountDao;
import de.akquinet.myHoliday.dao.UserAccountDaoBean;
import de.akquinet.myHoliday.dao.UserAccountRoleDao;
import de.akquinet.myHoliday.dao.UserAccountRoleDaoBean;
import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class UserAccountDaoTest extends BaseNeedleTest {

	@ObjectUnderTest
	private final UserAccountDao userAccountDao = new UserAccountDaoBean();

	@ObjectUnderTest
	private final UserAccountRoleDao userAccountRoleDao = new UserAccountRoleDaoBean();	

	private UserAccountRole saveUserAccountRole(final AccountRole accountRole) throws Exception {
		final UserAccountRole uar = new UserAccountRole(accountRole);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountRoleDao.persist(uar);
			}
		});		
		
		Assert.assertEquals(1, userAccountRoleDao.loadAll().size());
		final UserAccountRole userAccountRole = userAccountRoleDao.findByAccountRole(accountRole);
		
		Assert.assertNotNull(userAccountRole);		
		return userAccountRole;
	}
	
	@Test
	public void testSaveUser() throws Exception {
		final UserAccountRole userAccountRole = saveUserAccountRole(AccountRole.USER);
		final List<UserAccountRole> accountRoles = new ArrayList<UserAccountRole>();
		accountRoles.add(userAccountRole);
		
		final UserAccount userAccount = new UserAccount("userName", "firstName", "lastName", "123",
				"firstName@lastName.com", "12345678", accountRoles);

		Assert.assertEquals(0, userAccountDao.loadAll().size());

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountDao.persist(userAccount);
			}
		});

		Assert.assertEquals(1, userAccountDao.loadAll().size());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFailSaveUser() throws Exception {
		final UserAccountRole userAccountRole = saveUserAccountRole(AccountRole.USER);
		final List<UserAccountRole> accountRoles = new ArrayList<UserAccountRole>();
		accountRoles.add(userAccountRole);
		
		final UserAccount userAccount = new UserAccount(null, "firstName", "lastName", "123", "firstName@lastName.com", "12345678", accountRoles);

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				userAccountDao.persist(userAccount);
			}
		});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFailOnWrongEMail() throws Exception {
		final UserAccountRole userAccountRole = saveUserAccountRole(AccountRole.USER);
		final List<UserAccountRole> accountRoles = new ArrayList<UserAccountRole>();
		accountRoles.add(userAccountRole);
		
		// first create and save a user
		final UserAccount userAccount = new UserAccount("userName", "firstName", "lastName", "123",
				"firstName@", "12345678", accountRoles);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager)
					throws Exception {
				transactionHelper.persist(userAccount);
			}
		});

		Assert.fail("sould not have been reached!");
	}
}
