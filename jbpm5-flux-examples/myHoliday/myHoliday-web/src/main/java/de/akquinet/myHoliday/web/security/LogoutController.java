package de.akquinet.myHoliday.web.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import de.akquinet.myHoliday.web.qualifier.CurrentUser;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class LogoutController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	@CurrentUser
	private MyCurrentUser currentUser;

	@Inject
	private FacesContext facesContext;

	public void logout() {
		log.infov("Logout user {0}", currentUser.getUserAccount().getUserName());

		final HttpSession httpSession = getHttpSession(facesContext);

		if (httpSession != null) {
			httpSession.invalidate();
		}
	}

	private static HttpServletRequest getHttpServletRequest(
			final FacesContext facesContext) {
		final Object request = facesContext.getExternalContext().getRequest();

		if (request instanceof javax.servlet.http.HttpServletRequest) {
			return (HttpServletRequest) request;
		} else {
			return null;
		}
	}

	private static HttpSession getHttpSession(final FacesContext facesContext) {
		final HttpServletRequest httpServletRequest = getHttpServletRequest(facesContext);

		if (httpServletRequest != null) {
			return httpServletRequest.getSession();
		} else {
			return null;
		}
	}
}
