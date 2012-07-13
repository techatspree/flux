package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.business.SingleUserAccountService;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
@Stateful
public class UserAccountUtilController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SingleUserAccountService singleUserAccountService;

	public UserAccountUtilController() {
	}

	public String getFirstName(final String userName) {
		return singleUserAccountService.getFirstName(userName);
	}

	public String getLastName(final String userName) {
		return singleUserAccountService.getLastName(userName);
	}

	public String getPhoneNumber(final String userName) {
		return this.singleUserAccountService.getPhoneNumber(userName);
	}

	public List<UserAccount> getUserAccountList(final String userName) {
		return this.singleUserAccountService.getUserAccountList(userName);
	}
}
