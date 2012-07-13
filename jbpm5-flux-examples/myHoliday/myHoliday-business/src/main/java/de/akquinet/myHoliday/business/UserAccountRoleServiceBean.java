package de.akquinet.myHoliday.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.akquinet.myHoliday.dao.UserAccountRoleDao;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class UserAccountRoleServiceBean implements UserAccountRoleService {

	@EJB
	private UserAccountRoleDao userAccountRoleDao;

	@Override
	public UserAccountRole findById(final Long id) {
		return userAccountRoleDao.load(id);
	}

	@Override
	public UserAccountRole findByAccountRole(final AccountRole accountRole) {
		return userAccountRoleDao.findByAccountRole(accountRole);
	}
}
