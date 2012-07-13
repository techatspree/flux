package de.akquinet.myHoliday.web.security;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.domain.UserAccountRole;
import de.akquinet.myHoliday.domain.enums.AccountRole;
import de.akquinet.myHoliday.web.qualifier.CurrentUser;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class UserPermissionController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@CurrentUser
	private MyCurrentUser currentUser;

	public boolean isLoggedIn() {
		if (currentUser != null) {
			return true;
		}
		return false;
	}

	public boolean isRoleAdministration() {
		if (currentUser != null) {
			for (final UserAccountRole userAccounRole : currentUser.getUserAccount()
					.getUserAccountRoles()) {
				if (userAccounRole.getAccountRole().equals(AccountRole.ADMINISTRATION)) {
					return true;
				}
			}
		}

		return false;
	}

	public UserAccount getUserAccount() {
		return currentUser != null ? currentUser.getUserAccount() : null;
	}

	public String getUserName() {
		return getUserAccount().getUserName();
	}

	public String getUserRoles() {
		String userRolesString = "";

		List<UserAccountRole> userRoles = null;
		if (this.currentUser != null) {
			userRoles = this.currentUser.getUserAccount().getUserAccountRoles();
		}

		for (final UserAccountRole uar : userRoles) {
			userRolesString = uar.getAccountRole().getRoleId() + " "
					+ userRolesString;
		}

		return userRolesString.trim();
	}
}
