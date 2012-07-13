package de.akquinet.myHoliday.web.viewconfig;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

import de.akquinet.myHoliday.web.qualifier.UserLoggedIn;

@ViewConfig
public interface MyWorkflowViewConfig {

	public static final String SERVLET_SUFFIX = ".seam";

	public static final String PAGE_PROCESSMANAGEMENT = "/pages/processmanagement";
	public static final String PAGE_SUBSTITUTIONVIEW = "/pages/substitutionview";
	public static final String PAGE_TASKMANAGEMENT = "/pages/taskmanagement";
	public static final String PAGE_USERMANAGEMENT = "/pages/usermanagement";

	public static final String PAGE_HOME = PAGE_PROCESSMANAGEMENT;

	static enum Pages {

		@ViewPattern("/pages/*")
		@UserLoggedIn
		SECURED,

		@FacesRedirect
		@ViewPattern("/*")
		@AccessDeniedView("/login.xhtml")
		@LoginView("/login")
		ALL

	}

}
