package de.akquinet.myHoliday.web.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.web.qualifier.CurrentUser;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@SessionScoped
public class CurrentUserProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MyHolidayAuthenticator authenticator;

	@Produces
	@Named
	@CurrentUser
	public MyCurrentUser getCurrentUser() {
		return (MyCurrentUser) authenticator.getUser();
	}
}
