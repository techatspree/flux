package de.akquinet.myHoliday.dao;

import java.util.Map;

import javax.ejb.Stateless;

import de.akquinet.myHoliday.dao.common.JpaGenericDao;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class UserAccountRoleDaoBean extends JpaGenericDao<UserAccountRole>
		implements UserAccountRoleDao {

	@Override
	public UserAccountRole findByAccountRole(final AccountRole accountRole) {

		final Map<String, Object> paramMap = createParameterMap(
				UserAccountRole.PARAM_ACCOUNT_ROLE, accountRole);
		return findSingleByNamedQuery(UserAccountRole.QUERY_FIND_BY_ACCOUNT_ROLE,
				paramMap);
	}
}
