package de.akquinet.jbosscc.flux.tdoservice.tdos.index;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;
import de.akquinet.jbosscc.flux.tdoservice.tdos.index.qualifier.TdoIndex;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class TdoIndexProducer {

	@Inject
	private Instance<AbstractTaskDataObject> allTdos;

	@Produces
	@Named
	@TdoIndex
	public TdoIndexData createTdoIndex() {

		final Map<String, AbstractTaskDataObject> tdoIndex = new HashMap<String, AbstractTaskDataObject>();

		for (final AbstractTaskDataObject tdo : allTdos) {
			if (tdo.getClass().isAnnotationPresent(Tdo.class)) {
				final Tdo tdoAnnotation = tdo.getClass().getAnnotation(Tdo.class);

				tdo.setTaskName(tdoAnnotation.task());
				tdo.setFormName(tdoAnnotation.form());
				tdo.setIsClosable(tdoAnnotation.isClosable());
				tdo.setIsRevocable(tdoAnnotation.isRevocable());

				tdoIndex.put(tdoAnnotation.task(), tdo);
			}
		}

		return new TdoIndexData(tdoIndex);
	}
}
