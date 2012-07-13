package de.akquinet.myHoliday.dao;

import java.util.Map;

import javax.ejb.Stateless;

import de.akquinet.myHoliday.dao.common.JpaGenericDao;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class UserAccountDaoBean extends JpaGenericDao<UserAccount> implements
		UserAccountDao {

	@Override
	public UserAccount findByUsername(final String userName) {
		final Map<String, Object> paramMap = createParameterMap(
				UserAccount.PARAM_USERNAME, userName);
		return findSingleByNamedQuery(UserAccount.QUERY_FIND_BY_USERNAME, paramMap);
	}

	@Override
	public UserAccount findByUsernameAndPassword(final String userName,
			final String password) {
		final Map<String, Object> paramMap = createParameterMap(
				UserAccount.PARAM_USERNAME, userName, UserAccount.PARAM_PASSWORD,
				password);
		return findSingleByNamedQuery(
				UserAccount.QUERY_FIND_BY_USERNAME_AND_PASSWORD, paramMap);
	}
}
