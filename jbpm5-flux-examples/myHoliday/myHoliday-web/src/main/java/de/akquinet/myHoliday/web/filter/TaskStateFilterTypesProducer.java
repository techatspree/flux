package de.akquinet.myHoliday.web.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.jbpm.task.Status;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TaskStateFilterTypesProducer {

	@Produces
	@Named("taskStateFilterTypes")
	@ConversationScoped
	public List<Status> getRoleFilterTypes() {
		return new ArrayList<Status>(Arrays.asList(Status.values()));
	}
}
