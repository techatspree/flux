package de.akquinet.myHoliday.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.myHoliday.business.HolidayService;
import de.akquinet.myHoliday.domain.Holiday;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
@Stateful
public class HolidayListController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HolidayService substitutionService;

	private DataModel<Holiday> dataLogEntryDataModel;

	public HolidayListController() {
	}

	@PostConstruct
	public void init() {
		this.dataLogEntryDataModel = new ListDataModel<Holiday>();
	}

	public DataModel<Holiday> getDataModel() {
		rereadSubstitutionList();
		return this.dataLogEntryDataModel;
	}

	private void rereadSubstitutionList() {
		this.dataLogEntryDataModel.setWrappedData(this.substitutionService
				.findAllHolidays());
	}
}
