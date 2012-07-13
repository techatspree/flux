package de.akquinet.jbosscc.flux.tdoservice.service;

import de.akquinet.jbosscc.flux.tdoservice.stereotypes.Tdo;
import de.akquinet.jbosscc.flux.tdoservice.tdos.AbstractTaskDataObject;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Tdo(task = "Test Task", form = "testForm", isRevocable = false)
public class TestTaskDataObject extends AbstractTaskDataObject {
	private static final long serialVersionUID = 1L;

}
