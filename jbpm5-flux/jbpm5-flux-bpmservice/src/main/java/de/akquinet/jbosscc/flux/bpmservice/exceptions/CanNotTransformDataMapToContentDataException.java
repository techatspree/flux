package de.akquinet.jbosscc.flux.bpmservice.exceptions;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public class CanNotTransformDataMapToContentDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public CanNotTransformDataMapToContentDataException() {
		super("Can not transform dataMap to contentData");
	}

	public CanNotTransformDataMapToContentDataException(final String msg) {
		super("Can not transform dataMap to contentData: " + msg);
	}
}
