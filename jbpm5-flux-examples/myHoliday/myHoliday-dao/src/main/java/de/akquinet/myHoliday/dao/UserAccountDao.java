package de.akquinet.myHoliday.dao;

import javax.ejb.Local;

import de.akquinet.myHoliday.dao.common.GenericDao;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Local
public interface UserAccountDao extends GenericDao<UserAccount> {

	UserAccount findByUsername(final String userName);

	UserAccount findByUsernameAndPassword(final String userName,
			final String password);
}
