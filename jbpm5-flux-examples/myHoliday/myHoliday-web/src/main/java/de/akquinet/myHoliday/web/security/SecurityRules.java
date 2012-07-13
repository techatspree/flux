package de.akquinet.myHoliday.web.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import de.akquinet.myHoliday.web.qualifier.UserLoggedIn;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class SecurityRules {

	@Secures
	@UserLoggedIn
	public boolean isUserLoggedIn(final Identity identity) {
		return identity.getUser() != null;
	}

}
