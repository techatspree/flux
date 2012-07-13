package de.akquinet.myHoliday.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.akquinet.myHoliday.dao.UserAccountDao;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class UserAccountServiceBean implements UserAccountService {

	@EJB
	private UserAccountDao userDao;

	@Override
	public UserAccount findById(final Long id) {
		return userDao.load(id);
	}

	@Override
	public UserAccount findByUserName(final String userName) {
		return userDao.findByUsername(userName);
	}

	@Override
	public void saveUserAccount(final UserAccount userAccount) {
		userDao.persist(userAccount);
	}

	@Override
	public void deleteUserAccount(final UserAccount userAccount) {
		userDao.delete(findById(userAccount.getId()));
	}

	@Override
	public List<UserAccount> findAllUserAccounts() {
		return userDao.loadAll();
	}
}
