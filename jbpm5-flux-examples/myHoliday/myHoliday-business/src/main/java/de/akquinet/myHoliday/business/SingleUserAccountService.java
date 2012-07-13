package de.akquinet.myHoliday.business;

import java.util.List;

import javax.ejb.Local;

import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Local
public interface SingleUserAccountService {

	public abstract String getFirstName(final String userName);

	public abstract String getLastName(final String userName);

	public abstract String getPhoneNumber(final String userName);

	public abstract List<UserAccount> getUserAccountList(final String userName);

}