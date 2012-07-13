package de.akquinet.myHoliday.web.viewconfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class ViewUtils {
	@Produces
	@Named
	public boolean isDevelopment(final FacesContext ctx) {
		return ctx.isProjectStage(ProjectStage.Development);
	}

	@Produces
	@Named("responseCommited")
	public boolean isResponseCommited(final FacesContext ctx) {
		return ((HttpServletResponse) ctx.getExternalContext().getResponse())
				.isCommitted();
	}
}