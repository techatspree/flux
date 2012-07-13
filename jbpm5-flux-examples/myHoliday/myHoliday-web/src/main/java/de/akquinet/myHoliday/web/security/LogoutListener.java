package de.akquinet.myHoliday.web.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.events.PreLoggedOutEvent;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@ApplicationScoped
public class LogoutListener {
	@Inject
	private LogoutController logoutController;

	public void logoutListener(@Observes final PreLoggedOutEvent event) {
		logoutController.logout();
	}
}
