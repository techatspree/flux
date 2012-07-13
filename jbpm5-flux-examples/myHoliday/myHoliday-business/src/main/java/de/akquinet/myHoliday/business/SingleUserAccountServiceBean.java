package de.akquinet.myHoliday.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Stateless
public class SingleUserAccountServiceBean implements Serializable,
		SingleUserAccountService {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserAccountService userAccountService;

	@Override
	public String getFirstName(final String userName) {
		UserAccount userAccount = null;
		if (userName != null) {
			userAccount = userAccountService.findByUserName(userName);
		}

		if (userAccount != null) {
			return userAccount.getFirstName();
		}

		return null;
	}

	@Override
	public String getLastName(final String userName) {
		UserAccount userAccount = null;
		if (userName != null) {
			userAccount = userAccountService.findByUserName(userName);
		}

		if (userAccount != null) {
			return userAccount.getLastName();
		}

		return null;
	}

	@Override
	public String getPhoneNumber(final String userName) {
		UserAccount userAccount = null;
		if (userName != null) {
			userAccount = userAccountService.findByUserName(userName);
		}

		if (userAccount != null) {
			return userAccount.getPhoneNumber();
		}

		return null;
	}

	@Override
	public List<UserAccount> getUserAccountList(final String userName) {
		final List<UserAccount> userAccountList = userAccountService
				.findAllUserAccounts();

		if (userName != null) {
			final UserAccount userAccount = userAccountService
					.findByUserName(userName);
			if (userAccount != null) {
				userAccountList.remove(userAccount);
			}
		}

		return userAccountList;
	}
}
