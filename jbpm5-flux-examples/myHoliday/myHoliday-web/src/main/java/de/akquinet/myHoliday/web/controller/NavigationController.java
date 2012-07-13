package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named(value = NavigationController.BEAN_NAME)
@RequestScoped
public class NavigationController implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "navigationController";

	public static final String TASK_MANAGEMENT = "/pages/taskmanagement";
	public static final String PROCESS_MANAGEMENT = "/pages/processmanagement";
	public static final String USER_MANAGEMENT = "/pages/usermanagement";
	public static final String HOLIDAY_MANAGEMENT = "/pages/holidayview";

	private static final String STYLE_SELECTED = "selected";

	private String activePage = TASK_MANAGEMENT;

	public String goToTaskSite() {
		return TASK_MANAGEMENT;
	}

	public String goToProcessSite() {
		return PROCESS_MANAGEMENT;
	}

	public String goToUserSite() {
		return USER_MANAGEMENT;
	}

	public String goToHolidaySite() {
		return HOLIDAY_MANAGEMENT;
	}

	// ------------------- Getter für den Style der Options im menü -------------

	public String getActivePage() {
		if (this.activePage == null) {
			return "";
		}
		return this.activePage;
	}

	public void setActivePage(final String requestedView) {
		if (requestedView != null) {
			this.activePage = requestedView;
		}
	}

	public String getOptionStyleUrlaubsantrag() {
		if (getActivePage().startsWith(PROCESS_MANAGEMENT)) {
			return STYLE_SELECTED;
		}
		return "";
	}

	public String getOptionStyleAufgaben() {
		if (getActivePage().startsWith(TASK_MANAGEMENT)) {
			return STYLE_SELECTED;
		}
		return "";
	}

	public String getOptionStyleUsers() {
		if (getActivePage().startsWith(USER_MANAGEMENT)) {
			return STYLE_SELECTED;
		}
		return "";
	}

	public String getOptionStyleHolidays() {
		if (getActivePage().startsWith(HOLIDAY_MANAGEMENT)) {
			return STYLE_SELECTED;
		}
		return "";
	}

}
