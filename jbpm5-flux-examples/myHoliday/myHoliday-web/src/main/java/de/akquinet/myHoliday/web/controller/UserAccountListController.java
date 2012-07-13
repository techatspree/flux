package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.business.UserAccountService;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
@Stateful
public class UserAccountListController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserAccountService userAccountService;

	private DataModel<UserAccount> userAccountDataModel;
	private UserAccount userAccount;

	public UserAccountListController() {
	}

	@PostConstruct
	public void init() {
		this.userAccountDataModel = new ListDataModel<UserAccount>();
		this.userAccount = null;
	}

	public DataModel<UserAccount> getDataModel() {
		rereadUserAccountList();
		return this.userAccountDataModel;
	}

	private void rereadUserAccountList() {
		this.userAccountDataModel.setWrappedData(this.userAccountService
				.findAllUserAccounts());
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}
}
