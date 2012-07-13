package de.akquinet.myHoliday.web.security;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.api.Credential;
import org.picketlink.idm.impl.api.PasswordCredential;

import de.akquinet.myHoliday.business.UserAccountService;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@SessionScoped
@Named("myHolidayAuthenticator")
public class MyHolidayAuthenticator extends BaseAuthenticator implements
		Serializable, Authenticator {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private Credentials credentials;

	@EJB
	private UserAccountService userAccountService;

	@Override
	public void authenticate() {

		final String username = credentials.getUsername();
		log.debugv("Authenticating user {0}", username);

		final Credential credential = credentials.getCredential();
		final String password = ((PasswordCredential) credential).getValue();

		final UserAccount userAccount = username != null ? userAccountService
				.findByUserName(username) : null;

		if (userAccount == null || !userAccount.getPassword().equals(password)) {
			log.debugv("Authentication for user {0} failed", username);
			setStatus(AuthenticationStatus.FAILURE);
			return;
		}

		setUser(new MyCurrentUser(userAccount));
		setStatus(AuthenticationStatus.SUCCESS);

		log.debugv("Authentication for user {0} succeeded", username);
	}

}
