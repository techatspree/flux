package de.akquinet.jbosscc.flux.bpmservice.exceptions;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class CanNotTransformContentToDataMapException extends Exception {

	private static final long serialVersionUID = 1L;

	public CanNotTransformContentToDataMapException() {
		super("Can not transform Content to dataMap");
	}

	public CanNotTransformContentToDataMapException(final String msg) {
		super("Can not transform Content to dataMap: " + msg);
	}
}
