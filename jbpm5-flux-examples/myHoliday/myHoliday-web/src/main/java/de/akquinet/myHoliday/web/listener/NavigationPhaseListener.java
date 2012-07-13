package de.akquinet.myHoliday.web.listener;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import de.akquinet.myHoliday.web.controller.NavigationController;

public class NavigationPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(final PhaseEvent event) {
		// NOP
	}

	@Override
	public void beforePhase(final PhaseEvent event) {

		final ELContext elContext = FacesContext.getCurrentInstance()
				.getELContext();
		final ELResolver elResolver = FacesContext.getCurrentInstance()
				.getELContext().getELResolver();

		final NavigationController naviController = (NavigationController) elResolver
				.getValue(elContext, null, NavigationController.BEAN_NAME);

		final String requestedView = event.getFacesContext().getExternalContext()
				.getRequestServletPath();

		naviController.setActivePage(requestedView);
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE; // RESTORE_VIEW;
	}

}
