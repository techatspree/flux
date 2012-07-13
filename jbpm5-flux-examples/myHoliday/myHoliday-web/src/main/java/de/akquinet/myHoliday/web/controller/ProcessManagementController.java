package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.process.ProcessManagementBean;
import de.akquinet.myHoliday.web.qualifier.CurrentUser;
import de.akquinet.myHoliday.web.security.MyCurrentUser;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class ProcessManagementController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcessManagementBean processManagementServiceBean;

	@Inject
	private NavigationController navigationController;

	@Inject
	@CurrentUser
	private MyCurrentUser currentUser;

	public String createNewProcessForLoggedInUser() {
		if (processManagementServiceBean.startNewProcess(currentUser
				.getUserAccount())) {
			return navigationController.goToTaskSite();
		}
		return null;
	}
}
