package de.akquinet.myHoliday.business;

import java.util.List;

import javax.ejb.Local;

import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Local
public interface UserAccountService {

	UserAccount findById(final Long id);

	UserAccount findByUserName(final String userName);

	void saveUserAccount(final UserAccount userAccount);

	void deleteUserAccount(final UserAccount userAccount);

	List<UserAccount> findAllUserAccounts();
}
