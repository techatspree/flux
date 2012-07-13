package de.akquinet.myHoliday.web.security;

import org.picketlink.idm.impl.api.model.SimpleUser;

import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class MyCurrentUser extends SimpleUser {

	private static final long serialVersionUID = 1L;

	private final UserAccount userAccount;

	public MyCurrentUser(final UserAccount userAccount) {
		super(userAccount.getUserName());
		this.userAccount = userAccount;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	@Override
	public String toString() {
		return userAccount.getUserName();
	}
}
