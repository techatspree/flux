package de.akquinet.myHoliday.business.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.domain.UserAccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@Stateless
public class UserAccountUtils {

	/**
	 * Die Funktion gibt alle RollenIds des übergebenden UserAccounts als String-Liste aus.
	 * 
	 * @param userAccount der ausgelesen werden soll.
	 * @return List<String> in der die RollenIds des UserAccounts drinn stehen.
	 */
	public List<String> userAccountRolesToStringList(final UserAccount userAccount) {
		final List<UserAccountRole> userAccountRoles = userAccount
				.getUserAccountRoles();
		final List<String> groupNameList = new ArrayList<String>();

		for (final UserAccountRole userAccountRole : userAccountRoles) {
			groupNameList.add(userAccountRole.getAccountRole().getRoleId());
		}

		return groupNameList;
	}
}
