package de.akquinet.myHoliday.process;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.jbosscc.flux.bpmservice.ProcessManagementServiceBean;
import de.akquinet.myHoliday.domain.UserAccount;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@SessionScoped
public class ProcessManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PROCESS_DEFINITION_ID = "de.akquinet.myholiday.requestForHoliday";

	public static final String PARAM_USERNAME = "applicant";

	@Inject
	private ProcessManagementServiceBean jbpmProcessManagmentServiceBean;

	public boolean startNewProcess(final UserAccount loggedInuserAccount) {

		if (loggedInuserAccount != null) {
			final Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put(PARAM_USERNAME, loggedInuserAccount.getUserName());

			this.jbpmProcessManagmentServiceBean.startNewProcess(
					PROCESS_DEFINITION_ID, dataMap);

			return true;
		}

		return false;
	}

}
