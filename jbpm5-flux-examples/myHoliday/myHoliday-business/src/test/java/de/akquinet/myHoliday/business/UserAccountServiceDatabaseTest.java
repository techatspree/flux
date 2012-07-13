package de.akquinet.myHoliday.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.InjectInto;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;
import de.akquinet.myHoliday.business.UserAccountService;
import de.akquinet.myHoliday.business.UserAccountServiceBean;
import de.akquinet.myHoliday.dao.BaseNeedleTest;
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
public class UserAccountServiceDatabaseTest extends BaseNeedleTest {

	@ObjectUnderTest
	@InjectInto(targetComponentId = "userAccountService")
	private final UserAccountDao userAccountDao = new UserAccountDaoBean();

	@ObjectUnderTest
	private final UserAccountService userAccountService = new UserAccountServiceBean();

	@ObjectUnderTest
	private final UserAccountRoleDao userAccountRoleDao = new UserAccountRoleDaoBean();

	private UserAccountRole saveUserAccountRole(final AccountRole accountRole)
			throws Exception {
		// Als erstes die Benutzerrolle speichern
		final UserAccountRole uar = new UserAccountRole(accountRole);
		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager) throws Exception {
				userAccountRoleDao.persist(uar);
			}
		});

		Assert.assertEquals(1, userAccountRoleDao.loadAll().size());
		final UserAccountRole userAccountRole = userAccountRoleDao
				.findByAccountRole(accountRole);

		Assert.assertNotNull(userAccountRole);
		return userAccountRole;
	}

	// -------------- test methods --------------------------------------------------------

	@Test
	public void testUserService() throws Exception {
		final UserAccountRole userAccountRole = saveUserAccountRole(AccountRole.USER);
		final List<UserAccountRole> accountRoles = new ArrayList<UserAccountRole>();
		accountRoles.add(userAccountRole);

		final UserAccount userAccount = new UserAccount("userName", "firstName",
				"lastName", "123", "firstName@lastName.com", "12345678", accountRoles);

		Assert.assertEquals(0, userAccountService.findAllUserAccounts().size());

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager) throws Exception {
				userAccountService.saveUserAccount(userAccount);
			}
		});

		Assert.assertEquals(1, userAccountService.findAllUserAccounts().size());
	}

	@Test
	public void testRemoveUser() throws Exception {

		Assert.assertEquals(0, userAccountService.findAllUserAccounts().size());

		final UserAccountRole userAccountRole = saveUserAccountRole(AccountRole.USER);
		final List<UserAccountRole> accountRoles = new ArrayList<UserAccountRole>();
		accountRoles.add(userAccountRole);

		// first create and save a user
		final UserAccount userAccount1 = new UserAccount("userName", "firstName",
				"lastName", "123", "firstName@lastName.com", "12345678", accountRoles);
		final UserAccount userAccount2 = new UserAccount("userName2", "firstName2",
				"lastName2", "123", "firstName2@lastName.com", "12345678", accountRoles);

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager) throws Exception {
				userAccountService.saveUserAccount(userAccount1);
				userAccountService.saveUserAccount(userAccount2);
			}
		});

		Assert.assertEquals(2, userAccountService.findAllUserAccounts().size());

		transactionHelper.executeInTransaction(new VoidRunnable() {
			@Override
			public void doRun(final EntityManager entityManager) throws Exception {
				userAccountService.deleteUserAccount(userAccount1);
			}
		});

		Assert.assertEquals(1, userAccountService.findAllUserAccounts().size());
	}
}
