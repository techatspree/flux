package de.akquinet.myHoliday.dao;

import javax.ejb.Local;

import de.akquinet.myHoliday.dao.common.GenericDao;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Local
public interface UserAccountRoleDao extends GenericDao<UserAccountRole> {

	UserAccountRole findByAccountRole(final AccountRole accountRole);
}
