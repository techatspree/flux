package de.akquinet.myHoliday.bootstrap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.akquinet.myHoliday.dao.UserAccountDao;
import de.akquinet.myHoliday.dao.UserAccountRoleDao;
import de.akquinet.myHoliday.dao.common.GenericDao;
import de.akquinet.myHoliday.domain.AbstractEntity;
import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Singleton
@Startup
public class BootstrapBean {

	@Inject
	private Logger log;

	@EJB
	private UserAccountDao userAccountDao;

	@EJB
	private UserAccountRoleDao userAccountRoleDao;

	@PostConstruct
	public void insert() {
		log.info("Setup data in data base...");
		insertUsers();
		log.info("Test data has been successfully set up");
	}

	private void insertUsers() {
		// Rollen Speichern
		final UserAccountRole userAccountRole1 = new UserAccountRole(
				AccountRole.TECHNICALADMIN);
		final UserAccountRole userAccountRole2 = new UserAccountRole(
				AccountRole.USER);
		final UserAccountRole userAccountRole3 = new UserAccountRole(
				AccountRole.ADMINISTRATION);

		save(userAccountRoleDao, userAccountRole1, userAccountRole2,
				userAccountRole3);

		final List<UserAccountRole> technicaladministratorRoles = new ArrayList<UserAccountRole>();
		final List<UserAccountRole> userRoles = new ArrayList<UserAccountRole>();
		final List<UserAccountRole> administrationRoles = new ArrayList<UserAccountRole>();

		technicaladministratorRoles.add(userAccountRoleDao
				.findByAccountRole(AccountRole.TECHNICALADMIN));
		userRoles.add(userAccountRoleDao.findByAccountRole(AccountRole.USER));
		administrationRoles.add(userAccountRoleDao
				.findByAccountRole(AccountRole.ADMINISTRATION));

		// Der Benutzer Administrator mit dem Benutzernamen "Administrator" wird benötigt, da er vom HumanTaskService benötigt wird!!!
		final UserAccount administrator = new UserAccount("Administrator",
				"Technischer", "Administrator", "secret", "user.admin@acme.com",
				"12345678", technicaladministratorRoles);

		final UserAccount userAccount1 = new UserAccount("john", "John", "McClane",
				"john", "jmcclane@acme.com", "12345678", userRoles);

		final UserAccount userAccount2 = new UserAccount("gordon", "Gordon",
				"Freeman", "gordon", "gfreeman@acme.com", "12345678",
				administrationRoles);

		final UserAccount userAccount3 = new UserAccount("robert", "Robert ",
				"Langdon", "robert", "rlangdon@acme.com", "12345678", userRoles);

		save(userAccountDao, administrator, userAccount1, userAccount2,
				userAccount3);
	}

	private <T extends AbstractEntity> void save(final GenericDao<T> dao,
			final T... entities) {
		for (final T entity : entities) {
			dao.persist(entity);
		}
	}
}
